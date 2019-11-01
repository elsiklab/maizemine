<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="im"%>


<!-- dataCategories -->
<html:xhtml/>

<div class="body">
<im:boxarea title="Data" stylename="plainbox"><p><fmt:message key="dataCategories.intro"/></p></im:boxarea>

<table cellpadding="0" cellspacing="0" border="0" class="dbsources">
  <tr>
    <th>Data Category</th>
    <th>Description</th>
    <th>Link</th>
    <th>Source</th>
    <th>PubMed</th>
    <th>Link</th>
  </tr>
  <tr><td rowspan="3" class="leftcol">
    <h2><p>Genes</p></h2></td>
    <td>Maize Community Gene Set</td>
    <td>
        <p><i>Z. mays</i></p>
    </td>
    <td>AGPv4</td>
    <td>
       <p>Lawrence et al - PubMed <a target="_blank" href=https://www.ncbi.nlm.nih.gov/pubmed/14681441>14681441 </a></p>
       <p>Jiao et al. - PubMed <a target="_blank" href=https://www.ncbi.nlm.nih.gov/pubmed/28605751>28605751</a></p>
   </td>
    <td> <a target="_blank" href="http://www.maizegdb.org/assembly#downloads">maizeGDB Genomes</a></td>
  </tr>

  <tr>
    <td>Maize Community Gene Set</td>
    <td> <i>Z. mays</i> </td>
    <td>AGPv3</td>
    <td>
       <p>Law et al  - PubMed <a target="_blank" href=https://www.ncbi.nlm.nih.gov/pubmed/25384563>25384563</a></p> 
       <p>Lawrence et al - PubMed <a target="_blank" href=https://www.ncbi.nlm.nih.gov/pubmed/14681441>14681441 </a></p>
    </td>
  
     <td><a target="_blank" href="http://www.maizegdb.org/assembly#downloads">maizeGDB Genomes</a></td>
  </tr>
    <tr>
     <td>NCBI Annotation (RefSeq and Gene)</td>
    <td> <i>Z. mays</i> </td>
    <td>RefSeq</td>
    <td><p>O'Leary et al - PubMed <a target="_blank" href=https://www.ncbi.nlm.nih.gov/pubmed/26553804>26553804</a></p></td>
    <td><a target="_blank" href="ftp://ftp.ncbi.nlm.nih.gov/genomes/all/GCF/000/005/005/GCF_000005005.2_B73_RefGen_v4">NCBI ftp</a></td>
  </tr>


  <tr>
    <td rowspan="1"  class="leftcol"><p><h2>Homology</h2></p></td>
     <td>Orthologue and paralogue relationships</td>
    <td>
        <p><i>A. tauschii</i></p>
	<p><i>A. thaliana</i></p>
        <p><i>B. distachyon</i></p>
        <p><i>H. vulgare</i></p>
	<p><i>O. barthii</i></p>
	<p><i>O. brachyantha</i></p>
	<p><i>O. glaberrima</i></p>
	<p><i>O. glumaepatula</i></p>
	<p><i>O. indica</i></p>
	<p><i>O. longistaminata</i></p>
	<p><i>O. meridionalis</i></p>
	<p><i>O. nivara</i></p>
	<p><i>O. punctata</i></p>
	<p><i>O. rufipogon</i></p>
	<p><i>O. sativa</i></p>
	<p><i>S. italica</i></p>
	<p><i>T. aestivum</i></p>
	<p><i>T. urartu</i></p>
	<p><i>S. bicolor</i></p>
        <p><i>Z. mays</i></p>
    </td>
    <td>EnsemblCompara Plant - Version 36</td>
    <td>Monaco et al - PubMed <a target="_blank" href=https://www.ncbi.nlm.nih.gov/pubmed/24217918>24217918</a></td>
    <td><a target="_blank" href="http://plants.ensembl.org/info/website/ftp/index.html">EnsemblCompara Plant</a></td>
  </tr>

  <tr>
    <td rowspan="2"  class="leftcol"><p><h2>Proteins</h2></p></td>
    <td> Protein Annotations from UniProt</td>
    <td>
        <p><i>Z. mays</i></p>
    </td>
    <td> UniProt - Release 2017-02</td>
    <td> UniProt Consortium - PubMed <a target="_blank" href=http://www.ncbi.nlm.nih.gov/pubmed/?term=25348405>25348405</a></td>
    <td> <a target="_blank" href="ftp://ftp.uniprot.org/pub/databases/uniprot/current_release/knowledgebase/complete/">UniProt FTP</a></td>
  </tr>

  <tr>
    <td> Protein family and domain assignments to proteins from InterPro</td>
    <td>
        <p><i>Z. mays</i></p>
    </td>
    <td> InterPro Version 62</td>
    <td> Mitchell et al - PubMed <a target="_blank" href=http://www.ncbi.nlm.nih.gov/pubmed/?term=25428371>25428371</a></td>
    <td> <a target="_blank" href="ftp://ftp.ebi.ac.uk/pub/databases/interpro/61.0/">InterPro FTP</a></td>
  </tr>

  <tr>
    <td class="leftcol"><p> <h2>Gene Ontology</h2></p></td>
    <td> GO annotations </td>
    <td>
        <p><i>Z. mays</i></p>
    </td>
    <td> GOA at UniProt (GOC Validation Date: 12/16/2016)</td>
    <td> <p>Huntley et al - PubMed <a target="_blank" href=http://www.ncbi.nlm.nih.gov/pubmed/?term=25378336>25378336</a></p><p>Gene Ontology Consortium - PubMed <a target="_blank" href=http://www.ncbi.nlm.nih.gov/pubmed/?term=25428369>25428369</a></p></td>
    <td> <a target="_blank" href="http://geneontology.org/page/download-ontology">GO Consortium Annotation FTP</a></td>
  </tr>

<tr><td rowspan="3" class="leftcol">
    <h2><p>Pathways</p></h2></td>
    <td>Pathway information</td>
    <td>
        <p><i>A. thaliana</i></p>
        <p><i>Z. mays</i></p>
    </td>
    <td>Plant Reactome - release 52</td>
    <td> Tello-Ruiz MK et al - PubMed <a target="_blank" href=https://www.ncbi.nlm.nih.gov/pubmed/26553803>26553803</a></td>
    <td> <a target="_blank" href="http://plantreactome.gramene.org/download/">Reactome Gramene FTP</a></td> 
 </tr>
  <tr>
    <td> Pathway information</td>
     <td> <i>Z. mays</i> </td>
    <td> KEGG data set</td>
    <td><p>Kanehisa M et al - PubMed<a target="_blank" href=https://www.ncbi.nlm.nih.gov/pubmed/22080510>22080510</a></p></td>
    <td><a target="_blank" href="http://www.genome.jp/kegg/">KEGG</a></td>
 </tr>
   <tr>
    <td> Pathway information</td>
    <td> <i>Z. mays</i> </td>
    <td> CornCyc 8.0</td>
    <td><p>Walsh JR et al - PubMed <a target="_blank" href=https://www.ncbi.nlm.nih.gov/pubmed/27899149>27899149</a></p></td>
    <td><a target="_blank" href="https://corncyc-b73-v4.maizegdb.org/">CornCyc FTP</a></td>
 </tr>

 <tr>
    <td class="leftcol"><p><h2>Publications</h2></p></td>
    <td> A mapping from genes to publications</td>
    <td> <i>Z. mays</i> </td>
    <td> UniProt publications</td>
    <td>UniProt Knowledgebase <a target="_blank" href=https://www.ncbi.nlm.nih.gov/pubmed/28150232>28150232</a></td>
    <td></td>
  </tr>
 <tr>
  <tr>
    <td class="leftcol"><p><h2>Gene Expression</h2></p></td>
    <td> Gene expression computed on reference gene set RefSeq, AGPv3 and AGPv4</td>
    <td> <i>Z. mays</i> </td>
    <td>NCBI SRA</td>
    <td>
         <p>Stelpflug et al - PubMed <a target="_blank" href=https://www.ncbi.nlm.nih.gov/pubmed/27898762>27898762</a></p>
         <p>Sekhon et al - PubMed <a target="_blank" href=https://www.ncbi.nlm.nih.gov/pubmed/23637782>23637782</a></p>
    
    </td>
    <td><a target="_blank" href=https://www.ncbi.nlm.nih.gov/sra?linkname=bioproject_sra_all&from_uid=171684>NCBI BioProject 171684</a></td>
  </tr>
  <tr>
    <td class="leftcol"><p><h2>Variation</h2></p></td>
    <td>dbSNP data for Zea mays</td>
    <td><i>Z. mays</i></td>
    <td> dbSNP build 151</td>
    <td> Sherry et al - PubMed <a target="_blank" href="http://www.ncbi.nlm.nih.gov/pubmed/?term=11125122">11125122</a></td>
    <td><p><a target="_blank" href="ftp://ftp.ncbi.nih.gov/snp/organisms/archive/corn_4577/VCF/">NCBI FTP</a></td>

  </tr>
  <tr>
    <td rowspan="2"  class="leftcol"><p><h2>Genome Assembly</h2></p></td>
    <td> Chromosome Assembly</td>
    <td>
        <p><i>Z. mays</i></p>
    </td>
    <td> B73_RefGen_v4 (GCF_000005005.2)</td>
    <td><p>Jiao et al - PubMed <a target="_blank" href=https://www.ncbi.nlm.nih.gov/pubmed/28605751>28605751</a></p></td>
    <td> <a target="_blank" href="ftp://ftp.ncbi.nlm.nih.gov/genomes/all/GCF/000/005/005/GCF_000005005.2_B73_RefGen_v4">NCBI FTP</a></td>
  </tr>

  <tr>
    <td>Chromosome Assembly</td>
    <td>
        <p><i>Z. mays</i></p>
    </td>
    <td>B73_RefGen_v3</td>
    <td><p>Schnable et al  - PubMed <a target="_blank" href=https://www.ncbi.nlm.nih.gov/pubmed/19965430>19965430</a></p></td>
    <td> <a target="_blank" href="ftp://ftp.ensemblgenomes.org/pub/plants/release-31/fasta/zea_mays/dna">Ensembl Genomes FTP</a></td>
  </tr>
    <tr>
</table>

<div class="body">
</div>

</div>
<!-- /dataCategories -->
