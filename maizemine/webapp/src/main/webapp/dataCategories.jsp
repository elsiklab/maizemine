<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="im" %>

<!-- dataCategories -->
<div class="body">

<!-- INSERT TABLE HERE -->

<table cellpadding="0" cellspacing="0" border="0" class="dbsources"><tr><th width="15%">Data Category</th><th width="25%">Description</th><th width="10%">Organism</th><th width="15%">Source</th><th width="20%">PubMed</th><th width="15%">Link
</th></tr><tr>
<td class="leftcol"><h2><p>Genome Assembly</p></h2></td>
<td>Chromosome Assembly</td>
<td><em>Zea mays</em> B73</td>
<td>Zm-B73-REFERENCE-NAM-5.0 (GCF_902167145.1); Nov 2020</td>
<td>Hufford et al. - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/34353948" target="_blank">34353948</a></td>
<td><a href="https://ftp.ncbi.nlm.nih.gov/genomes/all/GCF/902/167/145/GCF_902167145.1_Zm-B73-REFERENCE-NAM-5.0/" target="_blank">NCBI FTP</a></td>
</tr>
<tr class="new-category-row">
<td class="leftcol" rowspan="2"><h2><p>Genes</p></h2></td>
<td>Maize Community Gene Set</td>
<td rowspan="2"><em>Zea mays</em> B73</td>
<td>MaizeGDB/Gramene Zm00001e.1; Jan 2021</td>
<td></td>
<td><a href="https://download.maizegdb.org/Zm-B73-REFERENCE-NAM-5.0/" target="_blank">MaizeGDB download</a></td>
</tr>
<tr>
<!-- part of rowspan -->
<td>NCBI Annotation</td>
<!-- part of rowspan -->
<td>RefSeq Annotation Release 103; 1 Sep 2020</td>
<td>O'Leary et al. - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/26553804" target="_blank">26553804</a></td>
<td><a href="https://ftp.ncbi.nlm.nih.gov/genomes/all/GCF/902/167/145/GCF_902167145.1_Zm-B73-REFERENCE-NAM-5.0/" target="_blank">NCBI FTP</a></td>
</tr>
<tr class="new-category-row">
<td class="leftcol"><h2><p>Gene Expression</p></h2></td>
<td>Gene expression computed on official gene models for Zm-B73-REFERENCE-NAM 5.0</td>
<td><em>Zea mays</em> B73</td>
<td>24 Aug 2021</td>
<td>Sekkon et al. - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/23637782" target="_blank">23637782</a></td>
<td><a href="https://datacommons.cyverse.org/browse/iplant/home/maizegdb/maizegdb/MaizeGDB_qTeller_FPKM/B73v5_qTeller_FPKM" target="_blank">MaizeGDB Expression download</a></td>
</tr>
<tr class="new-category-row">
<td class="leftcol"><h2><p>Gene Ontology</p></h2></td>
<td>GO annotations</td>
<td><em>Zea mays</em></td>
<td>GOA at UniProt - release 2021_03; 16 Jun 2021</td>
<td>Huntley et al. - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/25378336" target="_blank">25378336</a></td>
<td><a href="http://ftp.ebi.ac.uk/pub/databases/GO/goa/UNIPROT/goa_uniprot_all.gaf.gz" target="_blank">GOA UniProt FTP</a></td>
</tr>
<tr class="new-category-row">
<td class="leftcol" rowspan="2"><h2><p>Proteins</p></h2></td>
<td>Protein Annotations from UniProt</td>
<td rowspan="2"><em>Zea mays</em></td>
<td>UniProt release 2021_03; 16 Jun 2021</td>
<td>UniProt Consortium - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/33237286" target="_blank">33237286</a></td>
<td><a href="https://ftp.uniprot.org/pub/databases/uniprot/current_release/knowledgebase/complete/" target="_blank">UniProt FTP</a></td>
</tr>
<tr>
<!-- part of rowspan -->
<td>Protein family and domain assignments to proteins from InterPro</td>
<!-- part of rowspan -->
<td>InterPro v.86.0; 24 Jun 2021</td>
<td>Blum et al. - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/33156333" target="_blank">33156333</a></td>
<td><a href="ftp://ftp.ebi.ac.uk/pub/databases/interpro/86.0/" target="_blank">InterPro FTP</a></td>
</tr>
<tr class="new-category-row">
<td class="leftcol" rowspan="2"><h2><p>Pathways</p></h2></td>
<td>KEGG</td>
<td rowspan="2"><em>Zea mays</em></td>
<td>KEGG release 100.0; 1 Oct 2021</td>
<td>Kanehisa et al.  - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/34423492" target="_blank">34423492</a></td>
<td><a href="https://www.genome.jp/kegg/" target="_blank">KEGG</a></td>
</tr>
<tr>
<!-- part of rowspan -->
<td>Plant Reactome</td>
<!-- part of rowspan -->
<td>Plant Reactome, version 77; 9 Jun 2021</td>
<td>Naithani et al. - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/31680153" target="_blank">31680153</a></td>
<td><a href="https://plantreactome.gramene.org/download/current/Ensembl2PlantReactome_All_Levels.txt" target="_blank">Plant Reactome Gramene download</a></td>
</tr>
<tr class="new-category-row">
<td class="leftcol"><h2><p>Homology</p></h2></td>
<td>Orthologue and paralogue relationships</td>
<td><em>Ananas comosus</em><br><em>Asparagus officinalis</em><br><em>Aegilops tauschii</em><br><em>Arabidopsis thaliana</em><br><em>Brachypodium distachyon</em><br><em>Dioscorea cayenensis</em><br><em>Eragrostis curvula</em><br><em>Eragrostis tef</em><br><em>Hordeum vulgare</em><br><em>Leersia perrieri</em><br><em>Musa acuminata</em><br><em>Oryza barthii</em><br><em>Oryza brachyantha</em><br><em>Oryza glaberrima</em><br><em>Oryza glumipatula</em><br><em>Oryza longistaminata</em><br><em>Oryza meridionalis</em><br><em>Oryza nivara</em><br><em>Oryza punctata</em><br><em>Oryza rufipogon</em><br><em>Oryza sativa Indica Group</em><br><em>Oryza sativa Japonica Group</em><br><em>Panicum hallii</em><br><em>Panicum hallii var. hallii</em><br><em>Sorghum bicolor</em><br><em>Setaria italica</em><br><em>Saccharum spontaneum</em><br><em>Setaria viridis</em><br><em>Triticum aestivum</em><br><em>Triticum dicoccoides</em><br><em>Triticum turgidum</em><br><em>Triticum urartu</em><br><em>Zea mays</em></td>
<td>Ensembl Plant Biomart release 51; Apr 2021</td>
<td>Howe et al. - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/33137190" target="_blank">33137190</a></td>
<td><a href="http://plants.ensembl.org/index.html" target="_blank">Ensembl Plant Biomart download</a></td>
</tr>
<tr class="new-category-row">
<td class="leftcol"><h2><p>Variation</p></h2></td>
<td>Single Nucleotide Polymorphisms</td>
<td><em>Zea mays</em> B73</td>
<td>Ensembl Plants release 51; May 2021</td>
<td>Yates et al. 2022 - Pubmed 34791415</td>
<td><a href="http://ftp.ensemblgenomes.org/pub/release-51/plants/variation/vcf/zea_mays/" target="_blank">Ensembl Genomes</a></td>
</tr>
<tr class="new-category-row">
<td class="leftcol" rowspan="8"><h2><p>Maize Community Datasets</p></h2></td>
<td>Grotewold CAGE Tag Count Root</td>
<td rowspan="8"><em>Zea mays</em></td>
<td>3 May 2021</td>
<td>Mej&iacute;a-Guerra et al. - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/26628745" target="_blank">26628745</a></td>
<td><a href="https://datacommons.cyverse.org/browse/iplant/home/maizegdb/maizegdb/B73v5_JBROWSE_AND_ANALYSES/B73v5_TSS" target="_blank">Grotewold CAGE Tag Count Root download</a></td>
</tr>
<tr>
<!-- part of rowspan -->
<td>Grotewold CAGE Tag Count Shoot</td>
<!-- part of rowspan -->
<td>3 May 2021</td>
<td>Mej&iacute;a-Guerra et al. - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/26628745" target="_blank">26628745</a></td>
<td><a href="https://datacommons.cyverse.org/browse/iplant/home/maizegdb/maizegdb/B73v5_JBROWSE_AND_ANALYSES/B73v5_TSS" target="_blank">Grotewold CAGE Tag Count Shoot download</a></td>
</tr>
<tr>
<!-- part of rowspan -->
<td>GWAS Atlas</td>
<!-- part of rowspan -->
<td>31 Mar 2021</td>
<td>Tian et al. - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/31566222" target="_blank">31566222</a></td>
<td><a href="https://datacommons.cyverse.org/browse/iplant/home/maizegdb/maizegdb/B73v5_JBROWSE_AND_ANALYSES/B73v5_diversity_markers_and_GWAS/GWAS/SNPs_from_GWAS_Atlas_database" target="_blank">GWAS Atlas download</a></td>
</tr>
<tr>
<!-- part of rowspan -->
<td>MaizeGDB_UniformMu</td>
<!-- part of rowspan -->
<td>18 Mar 2021</td>
<td>McCarty et al. - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/24194867" target="_blank">24194867</a></td>
<td><a href="https://download.maizegdb.org/Insertions/UniformMu/" target="_blank">MaizeGDB_UniformMu download</a></td>
</tr>
<tr>
<!-- part of rowspan -->
<td>Stam 2017 Husk H3K9ac Enhancer</td>
<!-- part of rowspan -->
<td>3 Apr 2020</td>
<td>Oka et al. - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/28732548" target="_blank">28732548</a></td>
<td><a href="https://datacommons.cyverse.org/browse/iplant/home/maizegdb/maizegdb/B73v5_JBROWSE_AND_ANALYSES/B73v5_epigenetics_and_DNA_binding/Oka_2017_enhancer_binding/Oka_Enhancer_Husk_v5.gff" target="_blank">Stam 2017 Husk H3K9ac Enhancer download</a></td>
</tr>
<tr>
<!-- part of rowspan -->
<td>Stam 2017 Seedling H3K9ac Enhancer</td>
<!-- part of rowspan -->
<td>3 Apr 2020</td>
<td>Oka et al. - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/28732548" target="_blank">28732548</a></td>
<td><a href="https://datacommons.cyverse.org/browse/iplant/home/maizegdb/maizegdb/B73v5_JBROWSE_AND_ANALYSES/B73v5_epigenetics_and_DNA_binding/Oka_2017_enhancer_binding/Oka_Enhancer_Seedling_v5.gff" target="_blank">Stam 2017 Seedling H3K9ac Enhancer download</a></td>
</tr>
<tr>
<!-- part of rowspan -->
<td>Vollbrecht 2010 Ac/Ds Insertions</td>
<!-- part of rowspan -->
<td>3 Apr 2020</td>
<td>Vollbrecht et al. - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/20581308" target="_blank">20581308</a></td>
<td><a href="https://download.maizegdb.org/Insertions/AcDs_Vollbrecht/" target="_blank">Vollbrecht 2010 Ac/Ds Insertions download</a></td>
</tr>
<tr>
<!-- part of rowspan -->
<td>Wallace 2014 GWAS</td>
<!-- part of rowspan -->
<td>18 Mar 2021</td>
<td>Wallace et al. - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/25474422" target="_blank">25474422</a></td>
<td><a href="https://datacommons.cyverse.org/browse/iplant/home/maizegdb/maizegdb/B73v5_JBROWSE_AND_ANALYSES/B73v5_diversity_markers_and_GWAS/GWAS/GWAS_SNPs_from_Wallace_2014/B73v5_Wallace_etal_2014_PLoSGenet_GWAS_hits-150112_blastn.gff.gz" target="_blank">Wallace 2014 GWAS download</a></td>
</tr>
<tr class="new-category-row">
<td class="leftcol"><h2><p>Publications</p></h2></td>
<td>A mapping from genes to publications</td>
<td><em>Zea mays</em></td>
<td>Uniprot Publications, release 2021_03; 16 Jun 2021</td>
<td>UniProt Consortium - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/33237286" target="_blank">33237286</a> </td>
<td><a href="https://ftp.uniprot.org/pub/databases/uniprot/current_release/knowledgebase/complete/" target="_blank">UniProt FTP</a></td>
</tr>
<tr class="new-category-row">
<td class="leftcol" rowspan="4"><h2><p>Ontologies</p></h2></td>
<td>Evidence Ontology</td>
<td></td>
<td>4 Jun 2021</td>
<td>Chibucos et al. - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/25052702" target="_blank">25052702</a></td>
<td><a href="https://bioportal.bioontology.org/ontologies/ECO" target="_blank">ECO</a></td>
</tr>
<tr>
<!-- part of rowspan -->
<td>Gene Ontology</td>
<td></td>
<td>1 May 2021</td>
<td>Gene Ontology Consortium - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/27899567" target="_blank">27899567</a></td>
<td><a href="https://bioportal.bioontology.org/ontologies/GO" target="_blank">GO</a></td>
</tr>
<tr>
<!-- part of rowspan -->
<td>Plant Ontology</td>
<td></td>
<td>20 Aug 2020</td>
<td>Walls et al. - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/31214208" target="_blank">31214208</a></td>
<td><a href="https://github.com/Planteome/plant-ontology" target="_blank">PO</a></td>
</tr>
<tr>
<!-- part of rowspan -->
<td>Sequence Ontology</td>
<td></td>
<td>10 Jun 2021</td>
<td>Eilbeck et al. - PubMed <a href="https://www.ncbi.nlm.nih.gov/pubmed/15892872" target="_blank">15892872</a></td>
<td><a href="http://intermine.org/im-docs/docs/database/data-sources/library/so" target="_blank">SO</a></td>
</tr>
</table>

</div>
<!-- /dataCategories -->
