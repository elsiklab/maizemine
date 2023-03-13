<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="im"%>
<%@ taglib uri="/WEB-INF/functions.tld" prefix="imf" %>

<!DOCTYPE html>
<html>
  <head>
    <style>
    #release-updates {
      margin: 10px 20px 10px 20px;
    }
    #new-release-note {
      font-size:15px;
    }
    .note_header {
      line-height: 1.8;
      margin-bottom: 10px;
    }
    .note_header h5 {
      font-style: italic;
    }
    .note_desc {
      font-size: 13px;
      margin-left: 20px;
    }
    </style>
  </head>
  <body>
    <div id="content-wrap">
      <div id="release-updates">
        <div id="new-release-note">
          <p>MaizeMine has been updated to the latest version 1.5. Please see the data sources page for a full list of data and their versions.</p>
          <p>If you have any questions, please see our docs and youtube videos. Please do not hesitate to contact us should you require any further assistance. For all types of help and feedback email <c:out value="${WEB_PROPERTIES['feedback.destination']}"/> .</p>
        </div>
        <br/>
        <div class="note_header">
          <h3>MaizeMine v1.5 release</h3>
          <h5>November 2022</h5>
        </div>
        <div class="note_desc">
          <h4>Updates</h4>
          <ul>
            <li>Genome assemblies and gene sets of 25 <em>Z. mays</em> NAM founder lines have been added.</li>
            <li>Pangenes encompassing the NAM founder lines, computed at MaizeGDB, have been added, and template queries for these are found under a new template category tab called "Pangene".</li>
            <li>All MaizeGDB gene sources are now named with both the name of the <em>Z. mays</em> line and the gene set name at MaizeGDB (e.g. B73 Zm00001eb.1).</li>
            <li>GO annotations for all <em>Z. mays</em> lines computed using Pannzer at MaizeGDB have been added.</li>
            <li>Gene symbols and descriptions for all <em>Z. mays</em> lines computed using Pannzer at MaizeGDB have been added.</li>
            <li>Pathways for all lines computed using E2P2 at MaizeGDB have been added.</li>
            <li>The following other datasets have been updated for B73: UniProt, UniProt GO annotations, KEGG, Reactome, InterPro, Publications, GWAS Atlas.</li>
            <li>The InterMine application has been updated.</li>
          </ul>
          <br/>
        </div>
        <br/>
        <div class="note_header">
          <h3>MaizeMine v1.4 release</h3>
          <h5>December 2021</h5>
        </div>
        <div class="note_desc">
          <h4>Updates</h4>
          <ul>
            <li>The AGPv3.21 and AGPv4 genome assemblies have been replaced by the Zm-B73-REFERENCE-NAM-5.0  assembly.</li>
            <li>The MaizeGDB/Gramene gene sets associated with the older assemblies have been replaced by Zm00001eb.1.</li>
            <li>The RefSeq gene set has been updated with the latest release computed for Zm-B73-REFERENCE-NAM-5.0.</li>
            <li>New gene database crossreferences have been computed for Zm00001eb.1 <=> RefSeq.</li>
            <li>A new alias identifier dataset that maps Zm00001eb.1 gene ids to ids of older MaizeGDB/Gramene gene sets has been added.</li>
            <li>The source of SNP data is now Ensembl Plants rather than NCBI dbSNP, and SNP ids are now Panzea ids rather than rsIDs.</li>
            <li>The source of Gene Expression data is now MaizeGDB qTeller.</li>
            <li>CornCyc has been removed from this release, and will be added back once it has been transferred to the new gene set.</li>
            <li>The following other datasets have been updated: GO annotations, KEGG, Reactome, UniProt, InterPro, Ensembl Compara, Publications.</li>
            <li>KEGG and Reactome pathways are provided only for Zea mays.</li>
            <li>The following community datasets have been added or mapped to the new assembly: GWAS Atlas, Grotewold CAGE Tag Counts, Stam H3K9ac Enhancers, Wallace 2014 GWAS, Vollbrecht 2010 Ac/Ds Insertions (previously called Brutnell lab Ds Insertions), MaizeGDB_UniformMU (previously called McCarty_UniformMU)</li>
            <li>The following community datasets have been removed and may be added back once they are mapped to the new gene set or assembly: Maize Gamer, Illumina 50K SNP Chip, Barkan Lab Mu Insertions, Zhang Lab EMS mutations</li>
            <li>Template queries have been modified for the new data</li>
            <li>The InterMine application has been updated.</li>
          </ul>
          <br/>
        </div>
        <br/>
        <div class="note_header">
          <h3>MaizeMine v1.3 release</h3>
          <h5>March 2019</h5>
        </div>
        <div class="note_desc">
          <h4>New data</h4>
          <ul>
            <li>Barkan_Mu_Illumina_V3 data set</li>
            <li>Barkan_Mu_Illumina_V4 data set</li>
            <li>Brutnell_AcDs_V3 data set</li>
            <li>Chinese_EMS_V3 data set</li>
            <li>Chinese_EMS_V4 data set</li>
            <li>Grotewold_TSS_Root_V3 data set</li>
            <li>Grotewold_TSS_Shoot_V3 data set</li>
            <li>McCarty_UniformMU_V3 data set</li>
            <li>Maize-gamer data set</li>
          </ul>
        </div>
        <br/>
        <div class="note_header">
          <h3>MaizeMine v1.2 release</h3>
          <h5>December 2018</h5>
        </div>
        <div class="note_desc">
          <h4>New data</h4>
          <ul>
            <li>dbSNP(build 151) data set</li>
          </ul>
        </div>
        <br/>
        <div class="note_header">
          <h3>MaizeMine v1.1 release</h3>
          <h5>October 2017</h5>
        </div>
        <div class="note_desc">
          <h4>Features</h4>
          <ul>
            <li>Added support for performing genomic region search on both B73_RefGen_v3 and B73_RefGen_v4 assemblies</li>
          </ul>
          <br/>
          <h4>New data</h4>
          <ul>
            <li>KEGG data set-July 2017 release</li>
            <li>CornCyc pathways v4 based -8.0 May 2017</li>
            <li>RefSeq gene set for Zea mays</li>
            <li>Addition of UniProt release 08/2017 for NCBI RefSeq RefGen_v4 annotations</li>
            <li>UniProt and InterPro with Zea mays RefSeq B73_RefGen_v4 gene set Identifiers</li>
            <li>Cross References between RefSeq v4 and Gramene gene annotation v4</li>
          </ul>
        </div>
        <br/>
        <div class="note_header">
          <h3>MaizeMine v1.0 release</h3>
          <h5>December 2016</h5>
        </div>
        <div class="note_desc">
          <h4>New data</h4>
          <ul>
            <li>B73 RefGen_v3 Assembly, Zea mays B73_RefGen_v3 gene set</li>
            <li>B73 RefGen_v4 Assembly, Zea mays B73_RefGen_v4 gene set</li>
            <li>Ensembl Compara data set</li>
            <li>Reactome Gramene data set</li>
            <li>UniProt and InterPro with Zea mays Ensembl(Gramene) B73_RefGen_v4 gene set Identifiers</li>
          </ul>
        </div>
        <br/>
      </div>
    </div>
  </body>
</html>

