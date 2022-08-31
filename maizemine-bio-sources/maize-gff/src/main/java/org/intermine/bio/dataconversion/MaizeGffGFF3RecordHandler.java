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

import java.util.Iterator;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.intermine.bio.io.gff3.GFF3Record;
import org.intermine.metadata.Model;
import org.intermine.xml.full.Item;
import org.intermine.xml.full.Attribute;
import org.intermine.metadata.StringUtil;

/**
 * A converter/retriever for the MaizeGff dataset via GFF files.
 */

public class MaizeGffGFF3RecordHandler extends GFF3RecordHandler
{
    private static final String ENC = "UTF-8";

    /**
     * Create a new MaizeGffGFF3RecordHandler for the given data model.
     * @param model the model for which items will be created
     */
    public MaizeGffGFF3RecordHandler (Model model) {
        super(model);
        refsAndCollections.put("Exon", "transcripts");
        refsAndCollections.put("ThreePrimeUTR", "transcripts");
        refsAndCollections.put("FivePrimeUTR", "transcripts");
        refsAndCollections.put("CDS", "transcript");
        refsAndCollections.put("Transcript", "gene");
        refsAndCollections.put("MRNA", "gene");
        refsAndCollections.put("MiRNA", "gene");
        refsAndCollections.put("LincRNA", "gene");
        refsAndCollections.put("lincRNA", "gene");
        refsAndCollections.put("TRNA", "gene");
   }

    /**
     * {@inheritDoc}
     */
    @Override
    public void process(GFF3Record record) {
        Item feature = getFeature();
        String clsName = feature.getClassName();
        feature.setAttribute("source", record.getSource());
        String primaryIdentifier = feature.getAttribute("primaryIdentifier").getValue();
        feature.setAttribute("primaryIdentifier", primaryIdentifier.substring(primaryIdentifier.indexOf(':') + 1));

        if (clsName.equals("Gene")) {
            setFeatureDescription(record);
            setFeatureStatus(record);
            setFeatureBiotype(record, "gene_biotype");
        } else {
            // Set transcript biotype, if applicable
            setFeatureBiotype(record, "transcript_biotype");
        }
    }

    /**
     * Set feature description attribute with correct encoding, if present.
     * @param record
     */
    protected void setFeatureDescription(GFF3Record record) {
        Item feature = getFeature();
        if (record.getAttributes().get("description") != null) {
            String description = record.getAttributes().get("description").iterator().next();
            try {
                feature.setAttribute("description", URLDecoder.decode(URLDecoder.decode(description, ENC), ENC));
            } catch (UnsupportedEncodingException e) {
                System.out.println("WARNING: unsupported encoding " + ENC);
            }
        }
    }

    /**
     * Set feature status attribute, if present.
     * @param record
     */
    protected void setFeatureStatus(GFF3Record record) {
        Item feature = getFeature();
        if (record.getAttributes().get("status") != null) {
            String status = record.getAttributes().get("status").iterator().next();
            feature.setAttribute("status", status);
        }
    }

    /**
     * Set feature biotype attribute, if present.
     * @param record
     * @param biotypeName
     */
    protected void setFeatureBiotype(GFF3Record record, String biotypeName) {
        Item feature = getFeature();
        if (record.getAttributes().get(biotypeName) != null) {
            String biotype = record.getAttributes().get(biotypeName).iterator().next();
            feature.setAttribute("biotype", biotype);
        }
    }


    /**
     * Set item description attribute with correct encoding
     * @param item
     * @param description
     */
    private void setItemDescription(Item item, String description) {
        try {
            item.setAttribute("description", URLDecoder.decode(URLDecoder.decode(description, ENC), ENC));
        } catch (UnsupportedEncodingException e) {
            System.out.println("WARNING: unsupported encoding " + ENC);
        }
    }

    // Below function not used, but leaving in case needed in the future:
    /**
     * Parse biotype from gene_biotype attribute for aesthetics.
     * @param biotype
     * @return
     */
    protected String parseGeneBiotype(String biotype) {
        String returnType = "";

        if (biotype.equals("protein_coding") || biotype.equals("non_coding")) {
            String[] splitList = biotype.split("_");
            returnType = StringUtils.capitalize(splitList[0]) + " " + StringUtils.capitalize(splitList[1]);
        } else {
            throw new RuntimeException("Unexpected gene_biotype: " + biotype);
        }
        return returnType;
    }
}
