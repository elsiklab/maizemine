package org.intermine.bio.dataconversion;

/*
 * Copyright (C) 2002-2019 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.io.Reader;

import org.apache.log4j.Logger;
import org.intermine.dataconversion.ItemWriter;
import org.intermine.metadata.Model;
import org.intermine.xml.full.Item;

/**
 * 
 * @author
 */
public class MaizeKeggPathwayConverter extends MaizePathwaysConverter
{
    private static final String DATASET_TITLE = "KEGG pathways data set";
    private static final String DATA_SOURCE_NAME = "GenomeNet";
    protected static final Logger LOG = Logger.getLogger(MaizeKeggPathwayConverter.class);
    private static final String PROP_FILE = "maize-kegg-pathway_config.properties";

    /**
     * Constructor
     * @param writer the ItemWriter used to handle the resultant items
     * @param model the Model
     */
    public MaizeKeggPathwayConverter(ItemWriter writer, Model model) {
        super(writer, model, DATA_SOURCE_NAME, DATASET_TITLE);
        readConfig(PROP_FILE);
    }
}
