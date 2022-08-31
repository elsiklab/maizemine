package org.intermine.bio.dataconversion;

/*
 * Copyright (C) 2002-2021 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.util.HashMap;

import org.intermine.bio.io.gff3.GFF3Record;
import org.intermine.metadata.Model;
import org.intermine.xml.full.Item;

/**
 * A converter/retriever for the QtlGff dataset via GFF files.
 */

public class QtlGffGFF3RecordHandler extends GFF3RecordHandler
{
    HashMap<String, Item> publicationItems = new HashMap<String, Item>();
    HashMap<String, Item> sequenceAlterationItems = new HashMap<String, Item>();

    /**
     * Create a new QtlGffGFF3RecordHandler for the given data model.
     * @param model the model for which items will be created
     */
    public QtlGffGFF3RecordHandler (Model model) {
        super(model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void process(GFF3Record record) {
        Item feature = getFeature();
        String clsName = feature.getClassName();
        if (clsName.equals("QTL")) {
            if (record.getAttributes().get("ID") != null) {
                String primaryIdentifier = record.getAttributes().get("ID").iterator().next();
                feature.setAttribute("primaryIdentifier", primaryIdentifier);
            }
            if (record.getAttributes().get("Name") != null) {
                String name = record.getAttributes().get("Name").iterator().next();
                feature.setAttribute("name", name);
            }
            if (record.getAttributes().get("P-value") != null) {
                String pValue = record.getAttributes().get("P-value").iterator().next();
                feature.setAttribute("pValue", pValue);
            }
            if (record.getAttributes().get("PUBMED_ID") != null) {
                String pubMedId = record.getAttributes().get("PUBMED_ID").iterator().next();
                Item publication = getPublication(pubMedId);
                feature.addToCollection("publications", publication.getIdentifier());
            }
            if (record.getAttributes().get("FlankMarker") != null) {
                String flankMarker = record.getAttributes().get("FlankMarker").iterator().next();
                feature.setAttribute("flankMarker", flankMarker);
                //if (flankMarker.startsWith("rs")) {
                //    Item sequenceAlteration = getSequenceAlteration(flankMarker);
                //    feature.addToCollection("snpsAsFlankMarkers", sequenceAlteration.getIdentifier());
                //}
            }
        }
    }

    /**
     * Get an Item representation of a Publication
     * @param pubMedId
     * @return
     */
    private Item getPublication(String pubMedId) {
        Item publication = null;
        if (publicationItems.containsKey(pubMedId)) {
            publication = publicationItems.get(pubMedId);
        } else {
            publication = converter.createItem("Publication");
            publication.setAttribute("pubMedId", pubMedId);
            addItem(publication);
            publicationItems.put(pubMedId, publication);
        }
        return publication;
    }

    /**
     * Get an Item representation of a SequenceAlteration
     * @param identifier
     * @return
     */
    private Item getSequenceAlteration(String identifier) {
        Item sequenceAlteration = null;
        if (sequenceAlterationItems.containsKey(identifier)) {
            sequenceAlteration = sequenceAlterationItems.get(identifier);
        }
        else {
            sequenceAlteration = converter.createItem("SequenceAlteration");
            sequenceAlteration.setAttribute("primaryIdentifier", identifier);
            addItem(sequenceAlteration);
            sequenceAlterationItems.put(identifier, sequenceAlteration);
        }
        return sequenceAlteration;
    }
}
