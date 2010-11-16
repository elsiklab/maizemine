package samples;

/*
 * Copyright (C) 2002-2010 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.io.IOException;
import java.util.List;

import org.intermine.metadata.Model;
import org.intermine.pathquery.Constraints;
import org.intermine.pathquery.OrderDirection;
import org.intermine.pathquery.PathQuery;
import org.intermine.webservice.client.core.ServiceFactory;
import org.intermine.webservice.client.services.ModelService;
import org.intermine.webservice.client.services.QueryService;

/**
 * The QueryAPIClient is an example of query client fetching results of query from InterMine web
 * service.  It demonstrates using InterMine query API.
 **/
public class QueryAPIClient
{
    private static String serviceRootUrl = "http://localhost:8080/query/service";

    private static final int MAX_ROWS = 10000;

    /**
     * @param args command line arguments
     * @throws IOException
     */
    public static void main(String[] args) {
        QueryService service =
            new ServiceFactory(serviceRootUrl, "QueryAPIClient").getQueryService();
        Model model = getModel();
        
        // Create a query
        PathQuery query = new PathQuery(model);
        query.addViews("Organism.name", "Organism.taxonId");
        query.addOrderBy("Organism.name", OrderDirection.ASC);

        // Run the query
        List<List<String>> result = service.getResult(query, MAX_ROWS);
        
        // Output results
        int count = 0;
        for (List<String> row : result) {
            for (String cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
            count++;
        }
        System.out.println(System.getProperty("line.separator") + count + " results");
    }

    private static Model getModel() {
        ModelService service = new ServiceFactory(serviceRootUrl, "ClientAPI").getModelService();
        return service.getModel();
    }
}
