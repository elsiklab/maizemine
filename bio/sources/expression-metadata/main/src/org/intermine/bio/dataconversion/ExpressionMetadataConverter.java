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
import org.intermine.objectstore.ObjectStoreException;
import org.intermine.dataconversion.ItemWriter;
import org.intermine.metadata.Model;
import org.intermine.xml.full.Item;
import org.apache.log4j.Logger;
import org.intermine.util.FormattedTextParser;

/**
 *
 * @author
 */
public class ExpressionMetadataConverter extends BioFileConverter
{
    //
    private static final String PLANT_ONTOLOGY = "Plant Ontology";
    private HashMap<String,Item> sampleMap = new HashMap<String, Item>();
    private HashMap<String,Item> ontologyTermMap = new HashMap<String, Item>();
    private HashMap<String,Item> delayedItems = new HashMap<String, Item>();
    private static final String DATASET_TITLE = "Maize Expression Metadata";
    private static final String DATA_SOURCE_NAME = "Maize Expression Metadata from SRA";

    /**
     * Constructor
     * @param writer the ItemWriter used to handle the resultant items
     * @param model the Model
     */
    public ExpressionMetadataConverter(ItemWriter writer, Model model) {
        super(writer, model, DATA_SOURCE_NAME, DATASET_TITLE);
    }

    /**
     *
     * {@inheritDoc}
     */
    public void process(Reader reader) throws Exception {
        Iterator<String[]> lineIter = FormattedTextParser.parseTabDelimitedReader(reader);
        while(lineIter.hasNext()) {
            String[] line = lineIter.next();
            String sampleReplicateName = line[0];
            String sampleName = line[1];
            String sraBioSampleId = line[2];
            String sraRunId = line[3];
            String[] sraRunIdArray = sraRunId.split(";");
            sraRunId = String.join(", ", sraRunIdArray);
            String sraExperimentId = line[4];
            String[] sraExperimentIdArray = sraExperimentId.split(";");
            sraExperimentId = String.join(", ", sraExperimentIdArray);
            String sraStudyId = line[5];
            String sraBioProjectId = line[6];
            String libraryLayout = line[7];
            String sraSampleId = line[8];
            String bioSampleDescription = line[9];
            String organGroup = line[10];
            String tissueDescription = line[11];
            String poTerm = line[12].replaceAll(" ","");
            String poTermName = line[13];
            String growthStage = line[14];
            String replicateNumber = line[15];

            if (sampleName.isEmpty()) {
                System.out.println("sampleName cannot be empty");
                System.exit(1);
            }
            if (sampleReplicateName.isEmpty()) {
                System.out.println("sampleReplicateName cannot be empty");
                System.exit(1);
            }

            // create or get Sample item
            Item sampleItem;
            if (sampleMap.containsKey(sampleName)) {
                sampleItem = sampleMap.get(sampleName);
            }
            else {
                sampleItem = createItem("Sample");
                sampleItem.setAttribute("name", sampleName);
                sampleItem.setAttribute("sampleName", sampleName);
                if (!sraStudyId.isEmpty()) sampleItem.setAttribute("sraStudyId", sraStudyId);
                if (!sraBioProjectId.isEmpty()) sampleItem.setAttribute("sraBioProjectId", sraBioProjectId);
                if (!libraryLayout.isEmpty()) sampleItem.setAttribute("libraryLayout", libraryLayout);
                if (!growthStage.isEmpty()) sampleItem.setAttribute("growthStage", growthStage);
                Item poTermItem = getPlantOntologyTerm(poTerm, poTermName);
                sampleItem.setReference("poName", poTermItem.getIdentifier());
                if (!organGroup.isEmpty()) sampleItem.setAttribute("organGroup", organGroup);
                if (!tissueDescription.isEmpty()) sampleItem.setAttribute("tissueDescription", tissueDescription);
                sampleMap.put(sampleName, sampleItem);
            }

            // create Replicate item
            Item item = createItem("Replicate");
            item.setAttribute("name", sampleReplicateName);
            item.setAttribute("sampleReplicateName", sampleReplicateName);

            if (!sraBioSampleId.isEmpty()) item.setAttribute("sraBioSampleId", sraBioSampleId);
            if (!sraRunId.isEmpty()) item.setAttribute("sraRunId", sraRunId);
            if (!sraExperimentId.isEmpty()) item.setAttribute("sraExperimentId", sraExperimentId);
            if (!sraSampleId.isEmpty()) item.setAttribute("sraSampleId", sraSampleId);
            if (!bioSampleDescription.isEmpty()) item.setAttribute("bioSampleDescription", bioSampleDescription);
            if (!replicateNumber.isEmpty()) item.setAttribute("replicateNumber", replicateNumber);

            // set Sample as sample reference for Replicate
            item.setReference("sample", sampleItem.getIdentifier());
            // add to Sample's replicates collection
            sampleItem.addToCollection("replicates", item);

            try {
                // store Replicate item
                store(item);
            } catch (Exception e) {
                System.out.println("Error while storing item: " + item + "\n" + e);
            }
        }
    }

    /**
     * Get an Item representation of Plant Ontology term
     * @param termName
     * @param ontologyName
     * @return
     */
    private Item getPlantOntologyTerm(String identifier, String name) {
        Item ontologyTerm = null;
        if (ontologyTermMap.containsKey(identifier)) {
            ontologyTerm = ontologyTermMap.get(identifier);
        }
        else {
            ontologyTerm = createItem("POTerm");
            ontologyTerm.setAttribute("identifier", identifier);
            ontologyTerm.setAttribute("name", name);
            Item ontology = getOntology(PLANT_ONTOLOGY);
            ontologyTerm.setReference("ontology", ontology);
            ontologyTermMap.put(identifier, ontologyTerm);
        }
        return ontologyTerm;
    }

    /**
     * Get an Item representation of an Ontology
     * @param ontologyName
     * @return
     */
    private Item getOntology(String ontologyName) {
        Item ontology = null;
        if (delayedItems.containsKey(ontologyName)) {
            ontology = delayedItems.get(ontologyName);
        }
        else {
            ontology = createItem("Ontology");
            ontology.setAttribute("name", ontologyName);
            ontology.setAttribute("url", "https://bioportal.bioontology.org/ontologies/PO");
            delayedItems.put(ontologyName, ontology);
        }
        return ontology;
    }

    /**
     *
     */
    public void storeItems() {
        for (String key : sampleMap.keySet()) {
            try {
                // store Sample item
                store(sampleMap.get(key));
            } catch (Exception e) {
                System.out.println("Error while storing sample item:\n" + sampleMap.get(key) + "\nStackTrace:\n" + e);
            }
        }
    }

    /**
     *
     */
    public void storeOntologyTermItems() {
        for (String key : ontologyTermMap.keySet()) {
            try {
                // store Ontology term item
                store(ontologyTermMap.get(key));
            } catch (Exception e) {
                System.out.println("Error while storing ontology term item:\n" + ontologyTermMap.get(key) + "\nStackTrace:\n" + e);
            }
        }
    }

    /**
     *
     */
    public void storeDelayedItems() {
        for (String key : delayedItems.keySet()) {
            try {
                // store Sample item
                store(delayedItems.get(key));
            } catch (Exception e) {
                System.out.println("Error while storing delayed item:\n" + delayedItems.get(key) + "\nStackTrace:\n" + e);
            }
        }
    }

    /**
     *
     */
    public void close() {
        storeItems();
        storeOntologyTermItems();
        storeDelayedItems();
    }
}
