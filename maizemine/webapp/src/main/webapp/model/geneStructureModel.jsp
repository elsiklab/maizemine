
<!-- geneStructureModel.jsp -->

<div class="collection-of-collections" id="gene-structure-model" style="height:80px">

    <link rel="stylesheet" type="text/css" href="jbrowse_renderer/genome.css">

<style>
/********************************************************
*   invisible containers,
*   for features that also have a renderClass that gets centered,
*      and for subfeatures that have rendered children
*      (currently only subfeatures like this are exons, which have CDS and UTR child divs)
********************************************************/
.feature-name {
    z-index:0;
}
.RefSeq,
.plus-RefSeq,
.minus-RefSeq  {
    background-color: lightgray;
    height: 7px;
    z-index:0;
}
.RefSeq-CDS,
.plus-RefSeq-CDS,
.minus-RefSeq-CDS  {
    background-color: rgb(79,48,255);
    height: 11px;
    border: 1px solid gray;
    z-index:0;
}

.RefSeq-UTR,
.plus-RefSeq-UTR,
.minus-RefSeq-UTR {
    background-color: rgb(79,48,255);
    height: 9px;
    z-index:0;
}
</style>

<script>
var pid = '<c:out value="${gene.primaryIdentifier}"/>';

var assemblyMap = {
    "Zm-B73-REFERENCE-NAM-5.0" : "862",
    "B73_RefGen_v3" : "20",
    "B73_RefGen_v4" : "545"
};

// Require bare bones jbrowse components without using the main browser object
require({
   packages: [
       'dbind',
       'dgrid',
       'dojo',
       'dijit',
       'dojox',
       'json-schema',
       'jszlib',
       { name: 'lazyload', main: 'lazyload' },
       'xstyle',
       'put-selector',
       { name: 'jDataView', location: 'jDataView/src', main: 'jdataview' },
       'FileSaver',
       'JBrowse'
   ]
},
[
   'dojo/cookie',
   'dojo/dom',
   'dojo/dom-construct',
   'dojo/dom-style',
   'dojo/dom-class',
   'JBrowse/Browser',
   'JBrowse/View/Track/HTMLFeatures',
   'JBrowse/Store/SeqFeature/NCList',
   'JBrowse/Model/SimpleFeature',
   'JBrowse/View/GranularRectLayout',
   'JBrowse/Store/Sequence/StaticChunked'
],
function (cookie,dom,domConstruct,domStyle,domClass,Browser,HTMLFeatures,NCList,SimpleFeature,Layout,StaticChunkedSequence) {
   var maizemine = new intermine.Service({root: "https://maizemine.rnet.missouri.edu/maizemine"});
   var query = {
       from: 'Gene',
       select: [
           'chromosome.primaryIdentifier',
           'chromosome.assembly',
           'transcripts.chromosomeLocation.start',
           'transcripts.chromosomeLocation.end',
           'transcripts.chromosomeLocation.strand',
           'transcripts.exons.chromosomeLocation.start',
           'transcripts.exons.chromosomeLocation.end',
           'transcripts.primaryIdentifier',
           'transcripts.secondaryIdentifier',
           'transcripts.symbol',
           'organism.shortName'
       ],
       where: {
           primaryIdentifier: pid
       }
   };
   var createJBrowse=function(features, organism, assembly){
       var node=dom.byId("gene-structure-model");
       var height=15+Object.keys(features).length*31;
       domStyle.set(node,"height",height+'px');
       var trackConfig = {
          "style" : {
             "className" : "RefSeq",
             "subfeatureClasses" : {
                "wholeCDS" : null,
                "CodingSequence" : "RefSeq-CDS",
                "UTR" : "RefSeq-UTR"
             },
          },
          "storeClass" : "JBrowse/Store/SeqFeature/NCList",
          "type" : "FeatureTrack",
          "showLabels":false,
          "menuTemplate":null
       };

       if (assembly in assemblyMap) {
          trackConfig.onClick = {
              "label": "Feature name {name}\nFeature start {start}\nFeature end {end}",
              "url": "http://jbrowse-maizemine.rnet.missouri.edu:8080/apollo/" + assemblyMap[assembly] + "/jbrowse/index.html?loc={seq}:{start}..{end}",
              "action": "newWindow"
          }
       }
       else {
          trackConfig.onClick = {
              "label": "Feature name {name}\nFeature start {start}\nFeature end {end}"
          }
       }

       // Fake existence of jbrowse object
       var browser=new Browser({unitTestMode: true});
       browser.view={};
       var mmin=1000000000;
       var mmax=-1000000000;

       for(feature in features) {
           if(features[feature].start<mmin) mmin=features[feature].start;
           if(features[feature].end>mmax) mmax=features[feature].end;
       }
       browser.view.minVisible=function() { return mmin; };
       browser.view.maxVisible=function() { return mmax; };
       var track = new HTMLFeatures({
           refSeq: "refseq",
           config: trackConfig,
           changeCallback: function() { console.log("changeCallback"); },
           store: NCList,
           browser: browser
       });
       // Fake existence of genomeview object
       track.genomeView=browser.view;

       // fake a Block in the BlockBased tracktype
       var block={};
       block.featureLayout = new Layout(mmin, mmax);
       block.featureNodes = new Array();
       block.startBase = mmin - (mmax-mmin) * 0.1;
       block.endBase = mmax + (mmax-mmin) * 0.1;
       block.domNode=dom.byId("display");

       // Manually add block to track
       track.blocks=[block];
       track.label=dom.byId("label");
       track.measureStyles();
       track.updateStaticElements({x:0,y:0,width:2000,height:height});

       for(feature in features) {
           var simplefeature=new SimpleFeature({data: features[feature]});
           var fmin=simplefeature.get('start');
           var fmax=simplefeature.get('end');
           // Force calculate some CSS related things
           var featNode=track.renderFeature(simplefeature,simplefeature.get("name"),block,0.02,0.01,0.01,block.startBase,block.endBase);


           if(featNode) block.domNode.appendChild(featNode);
           else continue;

           // Must call center children after adding to node
           track._centerChildrenVertically(featNode);
       }
       track.updateStaticElements({x:0,y:0,width:2000,height:height});
   }
   maizemine.rows(query).then(function(rows) {
       var features={};
       rows.forEach(function printRow(row) {
           var transcript=row[7];
           if(!(transcript in features)) {
               features[transcript]={
                       "type":"mRNA",
                       "seq": row[0],
                       "assembly": row[1],
                       "start": row[2],
                       "end": row[3],
                       "strand": parseInt(row[4]),
                       "subfeatures":[],
                       "uniqueID":row[7],
                       "name":row[7]
                   };
           }
           assembly=row[1];
           organism=row[10];
           features[transcript].subfeatures.push({
                   "start": row[5],
                   "end": row[6],
                   "type": "CodingSequence",
                   "strand": features[transcript].strand
                });
       });
       
       createJBrowse(features, organism, assembly);
   });
});
</script>

<div id="display" style="margin:15px;position:absolute;width:75%;"></div>
<div id="label"></div>

</div>






<!-- /geneStructureModel.jsp -->
