package org.intermine.bio.dataconversion;

/*
 *  * Copyright (C) 2002-2015 FlyMine
 *   *
 *    * This code may be freely distributed and modified under the
 *     * terms of the GNU Lesser General Public Licence.  This should
 *      * be distributed with the code.  See the LICENSE file for more
 *       * information or http://www.gnu.org/copyleft/lesser.html.
 *        *
 *         */

import org.apache.log4j.Logger;
import org.intermine.xml.full.Item;

/**
 *   A handler for Maize GFF features.
 *   @author Kim Rutherford
 */
public class MaizeGffGFF3SeqHandler extends GFF3SeqHandler
{
    /**
      * {@inheritDoc}
      */
    @Override
    public Item makeSequenceItem(GFF3Converter converter, String identifier, String source) {
	Item seq = createItem(converter);
        seq.setAttribute("primaryIdentifier", identifier);
	seq.setAttribute("source", source);	
        return seq;
    }

}
