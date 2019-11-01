package org.intermine.bio.dataconversion;

/*
 * Copyright (C) 2002-2016 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.io.File;
import java.io.Reader;
import java.lang.Exception;
import java.lang.String;
import java.lang.System;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.intermine.dataconversion.ItemWriter;
import org.intermine.metadata.Model;
import org.intermine.xml.full.Item;
import org.apache.log4j.Logger;
import org.intermine.util.FormattedTextParser;
/**
 *
 * @author
 */
public class SymbolsConverter extends BioFileConverter
{
    //
    protected static final Logger LOG = Logger.getLogger(SymbolsConverter.class);
  //  private String orgRefId;
    private static final String DATASET_TITLE = "Symbols";
    private static final String DATA_SOURCE_NAME = "Symbols";
//    String taxonId;
//    String organismReferenceId;

    /**
     * Constructor
     * @param writer the ItemWriter used to handle the resultant items
     * @param model the Model
     */
    public SymbolsConverter(ItemWriter writer, Model model) {
        super(writer, model, DATA_SOURCE_NAME, DATASET_TITLE);
    }

 
    /**
     *
     *
     * {@inheritDoc}
     */
    public void process(Reader reader) throws Exception {
        // assumes that the metadata file has unique entries
        Iterator<String[]> lineIter = FormattedTextParser.parseTabDelimitedReader(reader);
//        organismReferenceId = getOrganism(taxonId);

        while (lineIter.hasNext()) {
            String[] line = lineIter.next();
            if (Pattern.matches("ID", line[0])) {
                // skipping header
                continue;
            }


            System.out.println(line.toString());
            String geneId = line[0];
            String symbol = line[1];
           // String description = line[2];

            Item item = createItem("Gene");
         //   item.setReference("organism", organismReferenceId);
         //   if (!description.isEmpty()) { item.setAttribute("description", description); }
            if (!symbol.isEmpty()) { item.setAttribute("symbol", symbol); }
            if (!geneId.isEmpty()) { item.setAttribute("primaryIdentifier", geneId); }



            try {
                store(item);
            } catch(Exception e) {
                System.out.println("Error while storing Symboldata item: " + item + "\nStacktrace: " + e);
            }



        }
    }
}
