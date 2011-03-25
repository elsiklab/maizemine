package org.intermine.api.tracker;

/*
 * Copyright (C) 2002-2011 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.log4j.Logger;
import org.intermine.api.profile.Profile;
import org.intermine.api.template.TemplateManager;
import org.intermine.api.tracker.factory.TrackerFactory;
import org.intermine.api.tracker.track.ListTrack;
import org.intermine.api.tracker.track.Track;
import org.intermine.api.tracker.util.ListBuildMode;
import org.intermine.api.tracker.util.ListTrackerEvent;
import org.intermine.api.tracker.util.TrackerUtil;
import org.intermine.objectstore.ObjectStoreWriter;
import org.intermine.objectstore.intermine.ObjectStoreWriterInterMineImpl;

/**
 * Intermediate class which decouples the tracker components from the code that uses them.
 * @author dbutano
 *
 */
public class TrackerDelegate
{
    private static final Logger LOG = Logger.getLogger(TrackerDelegate.class);
    protected Map<String, Tracker> trackers = new HashMap<String, Tracker>();
    ObjectStoreWriter osw;

    /**
     * Create the tracker manager managing the trackers specified in input
     * @param trackerClassNames the tracker names
     * @param osw the object store witer used to retrieve the connections
     */
    public TrackerDelegate(String[] trackerClassNames, ObjectStoreWriter osw) {
        Queue<Track> trackQueue = new LinkedList<Track>();
        this.osw = osw;
        Connection connection = null;
        try {
            connection = getConnection();
            Tracker tracker;
            for (String trackerClassName : trackerClassNames) {
                try {
                    tracker = TrackerFactory.getTracker(trackerClassName, connection, trackQueue);
                    String key = tracker.getName();
                    trackers.put(key, tracker);
                } catch (Exception e) {
                    LOG.error("Problems instantiating the tracker " + trackerClassName, e);
                }
            }
        } catch (SQLException sqle) {
            LOG.error("Problems retrieving connection. The tracker "
                      + " hasn't been instatiated", sqle);
        } finally {
            releaseConnection(connection);
        }

        try {
            connection = getConnection();
            TrackerLogger trackerLogger = new TrackerLogger(connection, trackQueue);
            new Thread(trackerLogger).start();
        } catch (SQLException sqle) {
            LOG.error("Problems retrieving conn for TrackerDelegate or TrackerLogger", sqle);
            releaseConnection(connection);
        }
    }

    /**
     * Return the trackers saved in the TrackerManager
     * @return map containing names and trackers
     */
    public Map<String, Tracker> getTrackers() {
        return trackers;
    }

    /**
     * Return the tracker template
     * @return map containing names and trackers
     */
    public TemplateTracker getTemplateTracker() {
        if (!trackers.isEmpty()) {
            return (TemplateTracker ) trackers.get(TrackerUtil.TEMPLATE_TRACKER);
        }
        return null;
    }

    /**
     * Return the tracker specified in input
     * @param trackerName the name of the tracker
     * @return Tracker the tracker
     */
    public Tracker getTracker(String trackerName) {
        if (!trackers.isEmpty()) {
            return trackers.get(trackerName);
        }
        return null;
    }

    /**
     * Store into the database the template execution by the user specified in input
     * @param templateName the template name
     * @param profile the user profile
     * @param sessionIdentifier the session id
     */
    public void trackTemplate(String templateName, Profile profile,
                             String sessionIdentifier) {
        TemplateTracker tt = getTemplateTracker();
        if (tt != null) {
            tt.trackTemplate(templateName, profile, sessionIdentifier);
        }
    }

    /**
     * Return the rank associated to the templates
     * @return map with key the template name and value the rank associated
     */
    public Map<String, Integer> getAccessCounter() {
        TemplateTracker tt = getTemplateTracker();
        if (tt != null) {
            Connection connection = null;
            try {
                connection = getConnection();
                return tt.getAccessCounter(connection);
            } catch (SQLException sqle) {
                LOG.error("Problems retrieving conn for getAccessCounter ", sqle);
            } finally {
                releaseConnection(connection);
            }
        }
        return null;
    }

    /**
     * Return the rank associated to the templates
     * @param templateManager the template manager
     * @return map with key the template name and value the rank associated
     */
    public Map<String, Integer> getRank(TemplateManager templateManager) {
        TemplateTracker tt = getTemplateTracker();
        if (tt != null) {
            return tt.getRank(templateManager);
        }
        return null;
    }

    /**
     * Update the template name value into the database
     * @param oldTemplateName the old name
     * @param newTemplateName the new name
     */
    public void updateTemplateName(String oldTemplateName, String newTemplateName) {
        TemplateTracker tt = getTemplateTracker();
        if (tt != null) {
            Connection connection = null;
            try {
                connection = getConnection();
                tt.updateTemplateName(oldTemplateName, newTemplateName, connection);
            } catch (SQLException sqle) {
                LOG.error("Problems retrieving conn for getAccessCounter ", sqle);
            } finally {
                releaseConnection(connection);
            }
        }
    }

    /**
     * Store into the database the list creation
     * @param type the type of the list
     * @param count the number of items contained
     * @param buildMode (from identifiers or from the querybuilder page)
     */
    public void trackListCreation(String type, int count, ListBuildMode buildMode) {
        Tracker tracker = getTracker(TrackerUtil.LIST_TRACKER);
        if (tracker != null) {
            ((ListTracker) tracker).trackList(type, count, buildMode, ListTrackerEvent.CREATION);
        }
    }

    /**
     * Store into the database the list execution
     * @param type the type of the list
     * @param count the number of items contained
     */
    public void trackListExecution(String type, int count) {
        Tracker tracker = getTracker(TrackerUtil.LIST_TRACKER);
        if (tracker != null) {
            ((ListTracker) tracker).trackList(type, count, null, ListTrackerEvent.EXECUTION);
        }
    }

    /**
     * Return the operations done for each list (execution or creation)
     * @return the list of ListTrack
     */
    public List<ListTrack> getListOperations() {
        Tracker lt = getTracker(TrackerUtil.LIST_TRACKER);
        if (lt != null) {
            Connection connection = null;
            try {
                connection = getConnection();
                return ((ListTracker) lt).getListOperations(connection);
            } catch (SQLException sqle) {
                LOG.error("Problems retrieving conn for getListOperations ", sqle);
            } finally {
                releaseConnection(connection);
            }
        }
        return null;
    }

    /**
     * Store into the database the login event
     * @param username the name of the user logged
     */
    public void trackLogin(String username) {
        Tracker tracker = getTracker(TrackerUtil.LOGIN_TRACKER);
        if (tracker != null) {
            ((LoginTracker) tracker).trackLogin(username);
        }
    }

    /**
     * Return the number of access for each user
     * @return map with key the user name and access number
     */
    public Map<String, Integer> getUserLogin() {
        Tracker lt = getTracker(TrackerUtil.LOGIN_TRACKER);
        if (lt != null) {
            Connection connection = null;
            try {
                connection = getConnection();
                return ((LoginTracker) lt).getUserLogin(connection);
            } catch (SQLException sqle) {
                LOG.error("Problems retrieving conn for getUserLogin ", sqle);
            } finally {
                releaseConnection(connection);
            }
        }
        return null;
    }

    /**
     * Store into the database the login event
     * @param keyword the keywords used for the search
     */
    public void trackKeywordSearch(String keyword) {
        Tracker tracker = getTracker(TrackerUtil.SEARCH_TRACKER);
        if (tracker != null) {
            ((KeySearchTracker) tracker).trackSearch(keyword);
        }
    }


    /**
     * Return the number of search for each keyword
     * @return map with key the keyword and the number of searches for that keyword
     */
    public Map<String, Integer> getKeywordSearches() {
        Tracker st = getTracker(TrackerUtil.SEARCH_TRACKER);
        if (st != null) {
            Connection connection = null;
            try {
                connection = getConnection();
                return ((KeySearchTracker) st).getKeywordSearches(connection);
            } catch (SQLException sqle) {
                LOG.error("Problems retrieving conn for getKeywordSearches ", sqle);
            } finally {
                releaseConnection(connection);
            }
        }
        return null;
    }

    /**
     * Store into the database the execution of a query (saved or temporary)
     * @param type the root type
     */
    public void trackQuery(String type) {
        Tracker tracker = getTracker(TrackerUtil.QUERY_TRACKER);
        if (tracker != null) {
            ((QueryTracker) tracker).trackQuery(type);
        }
    }
    /**
     * Release the database connection
     * @param conn the connection to release
     */
    private void releaseConnection(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.getAutoCommit()) {
                    Exception e = new Exception();
                    e.fillInStackTrace();
                    LOG.warn("releaseConnection called while in transaction - rolling back."
                              + System.getProperty("line.separator"), e);
                    conn.rollback();
                    conn.setAutoCommit(true);
                }
                conn.close();
            } catch (SQLException e) {
                LOG.error("Could not release SQL connection " + conn, e);
            }
        }
    }

    private Connection getConnection() throws SQLException {
        ObjectStoreWriterInterMineImpl uosw = (ObjectStoreWriterInterMineImpl) osw;
        return uosw.getDatabase().getConnection();
    }
}
