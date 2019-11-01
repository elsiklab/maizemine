#!/usr/bin/bash
ant -Dsource=maize-cds-refseq-fasta -v
ant -Dsource=maize-pep-v3-fasta -v
ant -Dsource=maize-pep-v4-fasta -v
ant -Dsource=maize-protein-refseq-fasta -v
ant -Dsource=maize-xref -v
ant -Dsource=kegg -v
ant -Dsource=kegg-metadata -v
ant -Dsource=reactome-gramene-pathway -v
ant -Dsource=corncyc -v
ant -Dsource=symbol -v
ant -Dsource=description -v
