package org.intermine.webservice.server.query;

/*
 * Copyright (C) 2002-2022 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;

/**
 * Runs the query-to-list service to run queries and save them as lists.
 * @author Alex Kalderimis
 *
 */
public class QueryListAppendServlet extends HttpServlet
{

    /**
     * Eclipse made me do it!!
     */
    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        runService(request, response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        runService(request, response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) {
        runService(request, response);
    }

    private void runService(HttpServletRequest request, HttpServletResponse response) {
        final InterMineAPI im = InterMineContext.getInterMineAPI();
        new QueryListAppendService(im).service(request, response);
    }

}
