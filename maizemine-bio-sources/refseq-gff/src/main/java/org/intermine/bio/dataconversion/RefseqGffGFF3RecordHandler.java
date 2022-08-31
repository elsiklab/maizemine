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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.intermine.bio.io.gff3.GFF3Record;
import org.intermine.metadata.Model;
import org.intermine.metadata.StringUtil;
import org.intermine.xml.full.Item;

/**
 * A converter/retriever for RefSeqGFF files.
 */

public class RefseqGffGFF3RecordHandler extends GFF3RecordHandler
{
    protected Map<String,String> aliasToRefId = new HashMap<String,String>();
    protected Map<String,String> crossRefToRefId = new HashMap<String,String>();
    protected Map<String,String> geneToRefId = new HashMap<String,String>();
    protected Map<String,String> crossRefSourceIdentifierToDatabaseIdentifierMap = new HashMap<String,String>();

    private static final String ENC = "UTF-8";

    /**
     * Create a new RefseqGffGFF3RecordHandler for the given data model.
     * @param model the model for which items will be created
     */
    public RefseqGffGFF3RecordHandler (Model model) {
        super(model);

        refsAndCollections.put("CDS", "transcript");
        refsAndCollections.put("Exon", "transcripts");
        refsAndCollections.put("MiRNA", "gene");
        refsAndCollections.put("MRNA", "gene");
        refsAndCollections.put("NcRNA", "gene");
        refsAndCollections.put("PrimaryTranscript", "gene");
        refsAndCollections.put("RRNA", "gene");
        refsAndCollections.put("Transcript", "gene");
        refsAndCollections.put("TRNA", "gene");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void process(GFF3Record record) {
        Item feature = getFeature();
        String clsName = feature.getClassName();

        // Set source, symbol, description (if present) for all features
        // (CDS, Exon won't have symbol or description)
        feature.setAttribute("source", record.getSource());
        setFeatureSymbol(record); 
        setFeatureDescription(record);
        // Set transcript biotype, if applicable
        setFeatureBiotype(record, "transcript_biotype");

        // Extra processing according to class (gene, transcript, etc.)
        if (clsName.equals("Gene"))  {
            setFeatureBiotype(record, "gene_biotype");
            setFeatureStatus(record);
            createDuplicateEntities(record);
            handleDbxrefs(record, "primaryIdentifier", "NCBI_Gene");

            // disabling parsing of alias and xRef from GFF3
            /*
            if (record.getAliases() != null) {
                List<String> aliases = record.getAliases();
                Iterator<String> aliasesIterator = aliases.iterator();

                while (aliasesIterator.hasNext()) {
                    setAliasName(aliasesIterator.next());
                }
            }

            if (record.getAttributes().get("xRef") != null) {
                List<String> xRefList = record.getAttributes().get("xRef");
                Iterator<String> xRefIterator = xRefList.iterator();

                while (xRefIterator.hasNext()) {
                    setCrossReference(xRefIterator.next());
                }
            }
            */
        } else if (clsName.equals("Pseudogene")) {
            setFeatureBiotype(record, "gene_biotype");
            handleDbxrefs(record, "primaryIdentifier", "NCBI_Gene");

        } else if (clsName.equals("PrimaryTranscript")) {
            createMatureTranscripts(record);
            handleDbxrefs(record, "primaryIdentifier", "RefSeq_NA");
            handleDbxrefs(record, "mirbaseIdentifier", "miRBase");

        } else if (clsName.equals("Transcript")) {
            setFeatureStatus(record);
            handleDbxrefs(record, "primaryIdentifier", "RefSeq_NA");
            handleDbxrefs(record, "proteinIdentifier", "RefSeq_Prot");
        
        } else if (clsName.equals("MiRNA")) {
            handleDbxrefs(record, "primaryIdentifier", "RefSeq_NA");
            handleDbxrefs(record, "mirbaseIdentifier", "miRBase");

        } else if (clsName.equals("MRNA")) {
            setFeatureStatus(record);
            handleDbxrefs(record, "primaryIdentifier", "RefSeq_NA");
            handleDbxrefs(record, "proteinIdentifier", "RefSeq_Prot");

        } else if (clsName.equals("AntisenseRNA") || clsName.equals("LncRNA")
                || clsName.equals("RRNA") || clsName.equals("SnoRNA") 
                || clsName.equals("SnRNA") || clsName.equals("TRNA")) {
            handleDbxrefs(record, "primaryIdentifier", "RefSeq_NA");
        }
    }

    /**
     * Set feature symbol attribute, if present.
     * @param record
     */
    protected void setFeatureSymbol(GFF3Record record) {
        Item feature = getFeature();
        if (record.getAttributes().get("symbol_ncbi") != null) {
            String symbol = record.getAttributes().get("symbol_ncbi").iterator().next();
            feature.setAttribute("symbol", symbol);
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
     * Set feature description attribute, if present.
     * @param record
     */
    protected void setFeatureDescription(GFF3Record record) {
        Item feature = getFeature();
        if (record.getAttributes().get("description") != null) {
            String description = record.getAttributes().get("description").iterator().next();
            setItemDescription(feature, description);
        }
    }

    /**
     * Set item description attribute with correct encoding.
     * @param item
     * @param description
     */
    protected void setItemDescription(Item item, String description) {
        try {
            item.setAttribute("description", URLDecoder.decode(URLDecoder.decode(description, ENC), ENC));
        } catch (UnsupportedEncodingException e) {
            System.out.println("WARNING: unsupported encoding " + ENC);
        }
    }

    /**
     * Set feature status attribute, if present.
     * @param record
     */
    protected void setFeatureStatus(GFF3Record record) {
        Item feature = getFeature();
        if (record.getAttributes().get("feature_type") != null) {
            String ft = record.getAttributes().get("feature_type").iterator().next();
            if (ft.equals("Ambiguous") || ft.equals("Frameshift")) {
                // limiting status to only show if the current gene is of type 'Ambiguous' or type 'Frameshift'
                feature.setAttribute("status", ft);
            }
        }
    }

    /**
     * Create duplicate entities, if applicable.
     * @param record
     */
    protected void createDuplicateEntities(GFF3Record record) {
        Item feature = getFeature();
        if (record.getAttributes().get("duplicate_entity") != null) {
            String duplicates = record.getAttributes().get("duplicate_entity").iterator().next();
            List<String> entities = new ArrayList<String>(Arrays.asList(StringUtil.split(duplicates, "|")));
            for (String entity : entities) {
                Item duplicateEntityItem = converter.createItem("DuplicateEntity");
                String duplicateEntityItemRefId = duplicateEntityItem.getIdentifier();
                List<String> entityAttributes = new ArrayList<String>(Arrays.asList(StringUtil.split(entity, ",")));
                List<String> locationInformation = new ArrayList<String>(Arrays.asList(StringUtil.split(entityAttributes.get(0), ":")));
                String chromosome = locationInformation.get(0);
                List<String> positionInfo = new ArrayList<String>(Arrays.asList(StringUtil.split(locationInformation.get(1), "..")));
                String start = positionInfo.get(0);
                String end = positionInfo.get(1);
                int strand = locationInformation.get(2).equals("+") ? 1 : -1;

                duplicateEntityItem.setAttribute("chromosome", chromosome);
                duplicateEntityItem.setAttribute("start", start);
                duplicateEntityItem.setAttribute("end", end);
                duplicateEntityItem.setAttribute("strand", Integer.toString(strand));

                if (entityAttributes.size() > 1) {
                    String geneIdentifier = entityAttributes.get(1).replace("NCBI_Gene:", "");
                    duplicateEntityItem.setAttribute("geneIdentifier", geneIdentifier);
                }

                if (entityAttributes.size() > 2) {
                    ArrayList<String> product = new ArrayList<String>(Arrays.asList(StringUtil.split(entityAttributes.get(2), ":")));
                    if (product.size() > 1) {
                        String transcriptId = product.get(1);
                        duplicateEntityItem.setAttribute("transcriptIdentifier", transcriptId);
                    }

                    if (product.size() > 2) {
                        String proteinIdentifier = product.get(2);
                        duplicateEntityItem.setAttribute("proteinIdentifier", proteinIdentifier);
                    }
                }

                try {
                    converter.store(duplicateEntityItem);
                } catch (Exception e) {
                    System.out.println("Exception while storing duplicateEntityItem:" + duplicateEntityItem + "\n" + e);
                }

                feature.addToCollection("duplicateEntities", duplicateEntityItemRefId);
            }
        }
    }

    /**
     * Create mature transcripts, if applicable.
     * @param record
     */
    protected void createMatureTranscripts(GFF3Record record) {
        Item feature = getFeature();
        if (record.getAttributes().get("mature_form") != null) {
            // mature_form for miRNA
            String matureFormString = record.getAttributes().get("mature_form").iterator().next();
            List<String> entities = new ArrayList<String>(Arrays.asList(StringUtil.split(matureFormString, "|")));

            for (String entity : entities) {
                Item matureTranscriptItem = converter.createItem("MatureTranscript");
                String matureTranscriptItemRefId = matureTranscriptItem.getIdentifier();
                List<String> entityAttributes = new ArrayList<String>(Arrays.asList(StringUtil.split(entity, ",")));
                List<String> locationInformation = new ArrayList<String>(Arrays.asList(StringUtil.split(entityAttributes.get(0), ":")));
                String chromosome = locationInformation.get(0);
                List<String> positionInfo = new ArrayList<String>(Arrays.asList(StringUtil.split(locationInformation.get(1), "..")));
                String start = positionInfo.get(0);
                String end = positionInfo.get(1);
                int strand = locationInformation.get(2).equals("+") ? 1 : -1;
                matureTranscriptItem.setAttribute("chromosome", chromosome);
                matureTranscriptItem.setAttribute("start", start);
                matureTranscriptItem.setAttribute("end", end);
                matureTranscriptItem.setAttribute("strand", Integer.toString(strand));
                String transcriptId = entityAttributes.get(1).split(":")[1];
                String mirbaseId = entityAttributes.get(2).split(":")[1];
                String description = entityAttributes.get(3).split(":")[1];
                matureTranscriptItem.setAttribute("transcriptIdentifier", transcriptId);
                matureTranscriptItem.setAttribute("mirbaseIdentifier", mirbaseId);
                setItemDescription(matureTranscriptItem, description);

                try {
                    converter.store(matureTranscriptItem);
                } catch (Exception e) {
                    System.out.println("Exception while storing matureTranscriptItem:" + matureTranscriptItem + "\n" + e);
                }

                feature.addToCollection("matureTranscripts", matureTranscriptItemRefId);
            }
        }
    }

    /**
     * Handle database cross-references, if present.
     * @param record
     * @param featureIdentifierName
     * @param refPrefix
     */
    protected void handleDbxrefs(GFF3Record record, String featureIdentifierName, String refPrefix) {
        Item feature = getFeature();
        List<String> dbxrefs = record.getDbxrefs();

        if (dbxrefs != null) {
            Iterator<String> dbxrefsIter = dbxrefs.iterator();

            while (dbxrefsIter.hasNext()) {
                String dbxref = dbxrefsIter.next();
                List<String> refList = new ArrayList<String>( Arrays.asList(StringUtil.split(dbxref, ",")));

                for (String ref : refList) {
                    ref = ref.trim();
                    int colonIndex = ref.indexOf(":");

                    if (colonIndex == -1) {
                        throw new RuntimeException("Error in Dbxref attribute " + ref );
                    }

                    if (ref.startsWith(refPrefix)) {
                        feature.setAttribute(featureIdentifierName, ref.replace(refPrefix + ":", ""));
                    }
                }
            }
        }
    }

    /**
     * Method parses the alias string, creates an AliasName item and sets the necessary references and collections
     * @param alias
     */
    protected void setAliasName(String alias) {
        Item feature = getFeature();
        List<String> splitVal = new ArrayList<String>(Arrays.asList(StringUtil.split(alias, ":")));

        if (splitVal.size() != 2) {
            String errorMsg = "size: " + splitVal.size();
            errorMsg += "Ambiguous aliasName: " + splitVal;
            errorMsg += "Expected aliasName format is '<ALIAS_ID>:<ALIAS_SOURCE>'";
            errorMsg += "Note: ALIAS_ID must be associated with its source";
            throw new RuntimeException(errorMsg);
        }

        String aliasPrimaryIdentifier = splitVal.get(0);
        String aliasSource = splitVal.get(1);

        if (aliasToRefId.containsKey(aliasPrimaryIdentifier)) {
            feature.addToCollection("aliases", aliasToRefId.get(aliasPrimaryIdentifier));
        } else { 
            Item aliasItem = converter.createItem("AliasName");
            aliasItem.setAttribute("identifier", aliasPrimaryIdentifier);
            aliasItem.setAttribute("source", aliasSource);
            aliasItem.setReference("organism", getOrganism());
            String aliasRefId = aliasItem.getIdentifier();
            feature.addToCollection("aliases", aliasRefId);
            aliasItem.addToCollection("features", feature.getIdentifier());
            aliasToRefId.put(aliasPrimaryIdentifier, aliasRefId);
            addItem(aliasItem);
        }
    }

    /**
     * Parses the xRef string, creates a Cross Reference item, creates a Gene item 
     * and sets the necessary references and collections.
     * @param xRef
     */
    protected void setCrossReference(String xRef) {
        Item feature = getFeature();
        List<String> crossRefPair = new ArrayList<String>(Arrays.asList(StringUtil.split(xRef, ":")));

        if (crossRefPair.size() == 0) { return; }

        if (crossRefPair.size() != 2) {
            String errorMsg = "Ambiguous xRef: " + crossRefPair;
            errorMsg += "Expected xRef format is '<XREF_ID>:<XREF_SOURCE>'";
            errorMsg += "Note: XREF_SOURCE should match column 2 of the alternate GFF3 (if any)";
            throw new RuntimeException(errorMsg);
        }

        String crossReferenceIdentifier = crossRefPair.get(0);
        String crossReferenceSource = crossRefPair.get(1);

        if (crossRefToRefId.containsKey(crossReferenceIdentifier)) {
            if (! geneToRefId.containsKey(crossReferenceIdentifier)) {
                throw new RuntimeException("xRef exists but its corresponding gene instance does not exist");
            }
        } else {
            Item crossRefItem = converter.createItem("CrossReference");
            String crossRefSourceId;
            if (crossRefSourceIdentifierToDatabaseIdentifierMap.containsKey(crossReferenceSource)) {
                crossRefSourceId = crossRefSourceIdentifierToDatabaseIdentifierMap.get(crossReferenceSource);
            } else {
                Item crossRefSourceItem = converter.createItem("DataSource");
                crossRefSourceId = crossRefSourceItem.getIdentifier();
                crossRefSourceIdentifierToDatabaseIdentifierMap.put(crossReferenceSource, crossRefSourceId);
            }
            crossRefItem.setReference("source", crossRefSourceId);
            crossRefItem.setAttribute("identifier", crossReferenceIdentifier);
            String crossRefRefId = crossRefItem.getIdentifier();
            crossRefToRefId.put(crossReferenceIdentifier, crossRefRefId);

            if (!geneToRefId.containsKey(crossReferenceIdentifier)) {
                // storing the Gene instance of xRef
                Item geneItem = converter.createItem("Gene");
                geneItem.setAttribute("primaryIdentifier", crossReferenceIdentifier);
                geneItem.setAttribute("source", crossReferenceSource);
                geneItem.setReference("organism", getOrganism());
                geneToRefId.put(crossReferenceIdentifier, geneItem.getIdentifier());
                crossRefItem.setReference("source", feature.getIdentifier());
                crossRefItem.setReference("target", geneItem.getIdentifier());
                addItem(geneItem);
            }

            addItem(crossRefItem);
        }
    }

    // No longer used, but leaving here for future reference:
    /**
     * Parse biotype from gene_biotype attribute for aesthetics.
     * @param biotype
     * @return
     */
    protected String parseGeneBiotype(String biotype) {
        String returnType = "";

        if (biotype.equals("lnc_RNA")){
            biotype = biotype.replace("_", "");
        }

        if (biotype.equals("Mt_tRNA") || biotype.equals("Mt_rRNA") || biotype.equals("RNase_MRP_RNA")
            || biotype.equals("SRP_RNA") || biotype.equals("misc_RNA") || biotype.equals("C_region")
            || biotype.equals("V_segment") || biotype.equals("telomerase_RNA") || biotype.equals("antisense_RNA")) {
            returnType = biotype.replace("_", " ");
        } else if (biotype.equals("miRNA") || biotype.equals("ncRNA") || biotype.equals("tRNA")
            || biotype.equals("rRNA") || biotype.equals("snRNA") || biotype.equals("snoRNA")
            || biotype.equals("lncRNA")) {
            returnType = biotype;
        } else if (biotype.equals("protein_coding") || biotype.equals("processed_pseudogene")) {
            String[] splitList = biotype.split("_");
            returnType = StringUtils.capitalize(splitList[0]) + " " + StringUtils.capitalize(splitList[1]);
        } else if (biotype.equals("pseudogene") || biotype.equals("transcribed_pseudogene") 
            || biotype.equals("ncRNA_pseudogene") || biotype.equals("tRNA_pseudogene") || biotype.equals("other")) {
            returnType = StringUtils.capitalize(biotype);
        } else {
            throw new RuntimeException("Unexpected gene_biotype: " + biotype);
        }
        return returnType;
    }
}
