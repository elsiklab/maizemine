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
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;

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

    /**
     * Create a new MaizeGffGFF3RecordHandler for the given data model.
     * @param model the model for which items will be created
     */

    Map<String,String> geneToRefId = new HashMap<String,String>();
    Map<String,String> xRefToRefId = new HashMap<String,String>();

    public MaizeGffGFF3RecordHandler (Model model) {
        super(model);
        // refsAndCollections controls references and collections that are set from the
        //         Parent= attributes in the GFF3 file.
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
        String primaryIdentifier = feature.getAttribute("primaryIdentifier").getValue();
        feature.setAttribute("primaryIdentifier", primaryIdentifier.substring(primaryIdentifier.indexOf(':') + 1));
        feature.setAttribute("source", record.getSource());
        if("gene".equalsIgnoreCase(record.getType())){
       //     if(record.getAttributes().get("Alias") != null) {
        //        List<String> aliasIdentifiers = record.getAttributes().get("Alias");
       //         for (String aliasIdentifier : aliasIdentifiers){
           //         setAliasName(aliasIdentifier);
       //         }
       //     }
        //    if(record.getAttributes().get("xRef") != null){
       //         List<String> xRefList = record.getAttributes().get("xRef");
       //         Iterator<String> xRefIterator = xRefList.iterator();
       //         while (xRefIterator.hasNext()) {
            //        setCrossReference(xRefIterator.next());
      //          }
      //      }
            if (record.getAttributes().get("description") != null) {
                String description = record.getAttributes().get("description").iterator().next();
                feature.setAttribute("description", description);
            }
           if (record.getAttributes().get("status") != null) {
                String status = record.getAttributes().get("status").iterator().next();
                feature.setAttribute("status", status);
            }
           if (record.getAttributes().get("biotype") != null) {
                String biotype = record.getAttributes().get("biotype").iterator().next();
                feature.setAttribute("biotype", biotype);
            }


        }
    }
/*
    public void setCrossReference(String xRef) {
        Item feature = getFeature();
        List<String> xRefPair = new ArrayList<String>(Arrays.asList(StringUtil.split(xRef, ":")));
        if (xRefPair.size() == 0) { return; }
        if (xRefPair.size() != 2) {
            System.out.println("Ambiguous xRef: " + xRefPair);
            System.out.println("Expected xRef format is '<XREF_ID>:<XREF_SOURCE>'");
            System.out.println("Note: XREF_SOURCE should match column 2 of the alternate GFF3 (if any)");
            System.exit(1);
        }
        String identifier = xRefPair.get(0);
        String xRefSource = xRefPair.get(1);
        if (xRefToRefId.containsKey(identifier)) {
            feature.addToCollection("dbCrossReferences", xRefToRefId.get(identifier));
            if (! geneToRefId.containsKey(identifier)) {
                System.out.println("xRef exists but its corresponding gene instance does not exist");
                System.exit(1);
            }
        } else {
            Item xRefItem = converter.createItem("xRef");
            xRefItem.setAttribute("refereeSource", xRefSource);
            xRefItem.setReference("organism", getOrganism());
            String xRefRefId = xRefItem.getIdentifier();
            feature.addToCollection("dbCrossReferences", xRefRefId);
            xRefToRefId.put(identifier, xRefRefId);
            if (!geneToRefId.containsKey(identifier)) {
                // storing the Gene instance of xRef
                Item geneItem = converter.createItem("Gene");
                geneItem.setAttribute("primaryIdentifier", identifier);
                geneItem.setAttribute("source", xRefSource);
                geneItem.setReference("organism", getOrganism());
                geneToRefId.put(identifier, geneItem.getIdentifier());
                xRefItem.setReference("referrer", feature.getIdentifier());
                xRefItem.setReference("referee", geneItem.getIdentifier());
                addItem(geneItem);
            }
            addItem(xRefItem);
        }
    }

    public void setAliasName(String aliasIdentifier) {
        Item feature = getFeature();
        Item aliasItem = converter.createItem("AliasName");
        List<String> aliasIdArray = new ArrayList<String>(Arrays.asList(StringUtil.split(aliasIdentifier, ":")));
        if (aliasIdArray.size() == 2) {
            aliasItem.setAttribute("identifier", aliasIdArray.get(0));
            aliasItem.setAttribute("source", aliasIdArray.get(1));
        }
        else {
            aliasItem.setAttribute("source", aliasIdentifier);
        }
        aliasItem.setAttribute("identifier", aliasIdentifier);
        aliasItem.setReference("features", feature.getIdentifier());
        feature.addToCollection("aliases", aliasItem);
        addItem(aliasItem);
    }
*/
}
