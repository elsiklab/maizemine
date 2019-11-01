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
    <th>Organism</th>
    <th>Data</th>
    <th>Source</th>
    <th>PubMed</th>
    <th>Note</th>
  </tr>
  <tr><td rowspan="2" class="leftcol">
    <h2><p>Genes</p></h2></td>
    <td>
        <p><i>Z. mays</i></p>
    </td>
    <td>Zm-B73-REFERENCE-GRAMENE-4.0</td>
    <td>MaizeGDB</td>
      <td><p>Lawrence et al - PubMed <a href=https://www.ncbi.nlm.nih.gov/pubmed/14681441>14681441 </a></p></td>
    <td> <a href="http://www.maizegdb.org/assembly#downloads">MaizeGDB Download</a></td>
  </tr>
  <tr>
    <td> <i>Z. mays</i> </td>
    <td> B73 RefGen_v3</td>
    <td> MaizeGDB</td>
    <td><p>Lawrence et al - PubMed <a href=https://www.ncbi.nlm.nih.gov/pubmed/14681441>14681441 </a></p></td>
    <td><a href="http://www.maizegdb.org/assembly#downloads">MaizeGDB Download</a></td>
  </tr>


  <tr>
    <td rowspan="1"  class="leftcol"><p><h2>Homology</h2></p></td>
    <td>
        <p><i>Z. mays</i></p>
	<p><i>A. thaliana</i></p>
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
	<p><i>A. tauschii</i></p>
	<p><i>B. distachyon</i></p>
	<p><i>H. vulgare</i></p>
	<p><i>S. bicolor</i></p>
    </td>
     <td>Orthologue and paralogue relationships</td>
    <td>EnsemblCompara Plant - Version 34</td>
    <td>Monaco et al - PubMed <a href=https://www.ncbi.nlm.nih.gov/pubmed/24217918>24217918</a></td>
    <td><a href="http://plants.ensembl.org/info/website/ftp/index.html">EnsemblCompara Plant Download</a></td>
  </tr>

  <tr>
    <td rowspan="2"  class="leftcol"><p><h2>Proteins</h2></p></td>
    <td>
        <p><i>Z. mays</i></p>
    </td>
    <td> Protein annotation</td>
    <td> UniProt - Release 2016-02</td>
    <td> UniProt Consortium - PubMed <a href=http://www.ncbi.nlm.nih.gov/pubmed/?term=25348405>25348405</a></td>
    <td> <a href="ftp://ftp.uniprot.org/pub/databases/uniprot/current_release/knowledgebase/complete/">UniProt FTP</a></td>
  </tr>

  <tr>
    <td>
        <p><i>Z. mays</i></p>
    </td>
    <td> Protein domains</td>
    <td> InterPro Version 61 </td>
    <td> Mitchell et al - PubMed <a href=http://www.ncbi.nlm.nih.gov/pubmed/?term=25428371>25428371</a></td>
    <td> <a href="ftp://ftp.ebi.ac.uk/pub/databases/interpro/61.0/">InterPro FTP</a></td>
  </tr>

  <tr>
    <td class="leftcol"><p> <h2>Gene Ontology</h2></p></td>
    <td>
        <p><i>Z. mays</i></p>
    </td>
    <td> GO annotations </td>
    <td> GOA at UniProt (GOC Validation Date: 12/16/2016)</td>
    <td> <p>Huntley et al - PubMed <a href=http://www.ncbi.nlm.nih.gov/pubmed/?term=25378336>25378336</a></p><p>Gene Ontology Consortium - PubMed <a href=http://www.ncbi.nlm.nih.gov/pubmed/?term=25428369>25428369</a></p></td>
    <td> <a href="http://geneontology.org/page/download-ontology">GO Consortium Annotation Download</a></td>
  </tr>

  <tr>
    <td class="leftcol" rowspan="1"><p><h2>Pathways</h2></p></td>
    <td>
      <p><i>Z. mays</i></p>
      <p><i>A. thaliana</i></p>
    </td>
    <td> Pathway information</td>
    <td> Reactome Gramene pathways - release 52</td>
    <td> Tello-Ruiz MK et al - PubMed <a href=https://www.ncbi.nlm.nih.gov/pubmed/26553803>26553803</a></td>
    <td> <a href="http://plantreactome.gramene.org/download/">Reactome Gramene Download</a></td>
  </tr>
 <tr>
    <td class="leftcol"><p><h2>Publications</h2></p></td>
    <td> <i>Z. mays</i> </td>
    <td> A mapping from genes to publications</td>
    <td> Uniprot publications</td>
    <td>Uniprot Knowledgebase <a href=https://www.ncbi.nlm.nih.gov/pubmed/28150232>28150232</a></td>
    <td></td>
  </tr>
    <tr>
</table>

<div class="body">
</div>

</div>
<!-- /dataCategories -->
