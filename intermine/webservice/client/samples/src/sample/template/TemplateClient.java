package sample.template;

import java.util.ArrayList;
import java.util.List;

import org.intermine.webservice.client.core.ServiceFactory;
import org.intermine.webservice.client.services.TemplateService;
import org.intermine.webservice.client.template.TemplateParameter;

/*
 * Copyright (C) 2002-2008 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

/**
 * The TemplateClient demonstrates using of InterMine template web service. This example returns 
 * first 100 predicted orthologues between two organisms sorted by FlyBase gene identifier.  
 * 
 * NOTE: The template name or template parameters can change at the server in next versions of 
 * FlyMine. In this case please download newer version of samples or modify sample properly.
 * 
 * @author Jakub Kulaviak
 **/
public class TemplateClient
{
    
    private static String serviceRootUrl = "http://pony:8080/query/service";
    
    public static void main(String[] args) {
        
        TemplateService service = new ServiceFactory(serviceRootUrl, "TemplateClient").getTemplateService();
        List<TemplateParameter> parameters = new ArrayList<TemplateParameter>();
        // setting first template parameter
        // first organism should be equal to Drosophila melanogaster
        parameters.add(new TemplateParameter("eq", "Drosophila melanogaster"));
        // setting second template parameter
        // second organism should be equal to Caenorhabditis elegans
        parameters.add(new TemplateParameter("eq", "Caenorhabditis elegans"));
        // first 100 results are fetched
        List<List<String>> result = service.getResult("GeneOrganism1_OrthologueOrganism2", parameters, 1, 100);
        System.out.println("First 100 predicted orthologues between two organisms sorted by FlyBase gene identifier:");
        for (List<String> row : result) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

}
