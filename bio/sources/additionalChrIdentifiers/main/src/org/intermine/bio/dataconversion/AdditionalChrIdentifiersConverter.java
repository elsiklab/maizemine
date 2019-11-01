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
public class AdditionalChrIdentifiersConverter extends BioFileConverter
{
    //
    protected static final Logger LOG = Logger.getLogger(AdditionalChrIdentifiersConverter.class);
    private static final String DATASET_TITLE = "Additional Chromosome Identifiers";
    private static final String DATA_SOURCE_NAME = "Additional Chromosome Identifiers";


    /**
     * Constructor
     * @param writer the ItemWriter used to handle the resultant items
     * @param model the Model
     */
    public AdditionalChrIdentifiersConverter(ItemWriter writer, Model model) {
        super(writer, model, DATA_SOURCE_NAME, DATASET_TITLE);
    }

    /**
     * 
     *
     * {@inheritDoc}
     */
    public void process(Reader reader) throws Exception {
      Iterator<String[]> lineIter = FormattedTextParser.parseTabDelimitedReader(reader);


      while (lineIter.hasNext()) {
            String[] line = lineIter.next();
            if (Pattern.matches("ID", line[0])) {
              continue;
            }


            System.out.println(line.toString());
            String chromId = line[0];
            String assembly = line[1];
            String secIdentifier = line[2];



            Item item = createItem("Chromosome");

            if (!chromId.isEmpty()) { item.setAttribute("primaryIdentifier", chromId); }
            if (!assembly.isEmpty()) { item.setAttribute("assembly", assembly); }
            if (!secIdentifier.isEmpty()) { item.setAttribute("secondaryIdentifier", secIdentifier); }



            try {
                store(item);
            } catch(Exception e) {
                System.out.println("Error while storing Symboldata item: " + item + "\nStacktrace: " + e);
            }



      }













    }
}
