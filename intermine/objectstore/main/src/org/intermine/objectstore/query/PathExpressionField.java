package org.intermine.objectstore.query;

/*
 * Copyright (C) 2002-2008 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

/**
 * An element that can appear in the SELECT clause of a query, representing extra data to be
 * collected for the Results - namely a part of the SELECT list of a QueryObjectPathExpression in
 * single-row mode.
 *
 * @author Matthew Wakeling
 */
public class PathExpressionField implements QueryPathExpression
{
    private QueryObjectPathExpression qope;
    private int fieldNumber;

    /**
     * Constructor.
     *
     * @param qope a QueryObjectPathExpression object
     * @param fieldNumber the number of the field from the path expression to represent
     */
    public PathExpressionField(QueryObjectPathExpression qope, int fieldNumber) {
        this.qope = qope;
        this.fieldNumber = fieldNumber;
    }

    /**
     * Returns the QueryObjectPathExpression.
     *
     * @return the qope
     */
    public QueryObjectPathExpression getQope() {
        return qope;
    }

    /**
     * Returns the field number.
     *
     * @return fieldNumber
     */
    public int getFieldNumber() {
        return fieldNumber;
    }

    /**
     * {@inheritDoc}
     */
    public Class getType() {
        return qope.getSelect().get(fieldNumber).getType();
    }
}
