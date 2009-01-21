package org.intermine.pathquery;

/*
 * Copyright (C) 2002-2009 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import antlr.collections.AST;

/**
 * In memory representation of constraint logic expression. Parses the expression
 * provided to the constructor. Use the toString method to convert the expression
 * back to text. An IllegalArgumentException will be thrown from the constructor
 * if a parse error occurs (use the cause exception to find out why).
 *
 * @author Thomas Riley
 * @see org.intermine.pathquery.PathQuery
 */
public class LogicExpression
{
    /** Root node - always an operator. */
    private Node root;

    /**
     * Create a new instance of LogicExpression parsing the given
     * expression.
     * @param expression the logic expression
     * @throws IllegalArgumentException if parse error occurs
     */
    public LogicExpression(String expression) throws IllegalArgumentException {
        root = parse(expression);
    }

    /**
     * Parse a logic expression.
     * @param expression logic expression
     */
    private Node parse(String expression) {
        AST ast = null;
        try {
            LogicLexer lexer = new LogicLexer(new StringReader(expression));
            LogicParser parser = new LogicParser(lexer);
            Node rootNode;
            // The root context
            parser.expr();
            ast = parser.getAST();
            //new antlr.DumpASTVisitor().visit(ast);
            if (ast.getText().toLowerCase().equals("or")) {
                rootNode = new Or(ast);
            } else if (ast.getText().toLowerCase().equals("and")) {
                rootNode = new And(ast);
            } else {
                rootNode = new Variable(ast.getText());
            }
            return rootNode;
        } catch (antlr.RecognitionException e) {
            new antlr.DumpASTVisitor().visit(ast);
            IllegalArgumentException e2 = new IllegalArgumentException(e.getMessage());
            e2.initCause(e);
            throw e2;
        } catch (antlr.TokenStreamException e) {
            new antlr.DumpASTVisitor().visit(ast);
            IllegalArgumentException e2 = new IllegalArgumentException(e.getMessage());
            e2.initCause(e);
            throw e2;
        } catch (IllegalArgumentException e) {
            new antlr.DumpASTVisitor().visit(ast);
            throw e;
        }
    }

    /**
     * Get the expression as a string.
     * @return expression as string
     */
    @Override
    public String toString() {
        return root.toString();
    }

    /**
     * Get the root node.
     * @return the root node
     */
    public Node getRootNode() {
        return root;
    }

    /**
     * Remove a variable from the expression.
     * @param name variable to remove
     */
    public void removeVariable(String name) {
        if (root instanceof Operator) {
            removeVariable(name, (Operator) root);
        } else if (root instanceof Variable && ((Variable) root).getName().equals(name)) {
            throw new IllegalArgumentException("Removing root node");
        }
        String logic = toString();
        root = parse(logic);
    }

    /**
     * Remove a variable from a branch of the tree.
     * @param name variable name
     * @param node root of subtree
     */
    private void removeVariable(String name, Operator node) {
        for (Node child : new ArrayList<Node>(node.getChildren())) {
            if (child instanceof Operator) {
                removeVariable(name, (Operator) child);
            } else if (child instanceof Variable && ((Variable) child).getName().equals(name)) {
                node.removeChild(child);
            }
        }
    }

    /**
     * Remove any variables that aren't in the given set.
     * @param variables set of variable names
     */
    public void removeAllVariablesExcept(Set variables) {
        if (root instanceof Operator) {
            removeAllVariablesExcept(variables, (Operator) root);
        } else if (root instanceof Variable && !variables.contains(((Variable) root).getName())) {
            throw new IllegalArgumentException("Removing root node");
        }
        String logic = toString();
        root = parse(logic);
    }

    /**
     * Remove any variables that aren't in the given set.
     *
     * @param variables set of variable names
     * @param node root of subtree
     */
    private void removeAllVariablesExcept(Set variables, Operator node) {
        for (Node child : new ArrayList<Node>(node.getChildren())) {
            if (child instanceof Operator) {
                removeAllVariablesExcept(variables, (Operator) child);
            } else if (child instanceof Variable
                    && !variables.contains(((Variable) child).getName())) {
                node.removeChild(child);
            }
        }
    }

    /**
     * Get the Set of variable names.
     * @return set of variable names in this expression
     */
    public Set<String> getVariableNames() {
        Set<String> variables = new HashSet<String>();
        getVariableNames(variables, root);
        return variables;
    }

    private void getVariableNames(Set<String> variables, Node node) {
        if (node instanceof Operator) {
            for (Node child : ((Operator) node).getChildren()) {
                getVariableNames(variables, child);
            }
        } else {
            variables.add(((Variable) node).getName());
        }
    }

    /**
     * Takes a List of collections of String variables and returns a List of the same length,
     * containing sections of the LogicExpression with those variables in.
     *
     * @param variables a List of Collections of String variable names
     * @return a List of LogicExpression objects
     * @throws IllegalArgumentException if the LogicExpression cannot be split up in this way,
     * or if there is an overlap in variables, or if there is an unrepresented variable, or if
     * there is an extra variable
     */
    public List<LogicExpression> split(List<? extends Collection<String>> variables) {
        Set<String> presentVariables = new HashSet<String>();
        for (Collection<String> v : variables) {
            for (String var : v) {
                if (presentVariables.contains(var)) {
                    throw new IllegalArgumentException("There is an overlap in variables");
                }
                presentVariables.add(var);
            }
        }
        if (!presentVariables.equals(getVariableNames())) {
            throw new IllegalArgumentException("Variables in argument (" + presentVariables
                    + ") do not match variables in expression (" + getVariableNames() + ")");
        }
        if (root instanceof Variable) {
            return Collections.singletonList(this);
        } else if (root instanceof Or) {
            if (variables.size() == 1) {
                return Collections.singletonList(this);
            } else {
                throw new IllegalArgumentException("Cannot split OR constraint " + toString());
            }
        } else {
            And and = (And) root;
            List<List<String>> buckets = new ArrayList<List<String>>();
            for (int i = 0; i < variables.size(); i++) {
                buckets.add(new ArrayList<String>());
            }
            for (Node node : and.getChildren()) {
                Set<String> hasVariables = new HashSet<String>();
                getVariableNames(hasVariables, node);
                int bucketNo = -1;
                for (int i = 0; i < variables.size(); i++) {
                    Collection<String> bucketVariables = variables.get(i);
                    if (bucketVariables.containsAll(hasVariables)) {
                        buckets.get(i).add(node.toString());
                        bucketNo = i;
                        break;
                    }
                }
                if (bucketNo == -1) {
                    throw new IllegalArgumentException("Cannot split node " + node.toString());
                }
            }
            List<LogicExpression> retval = new ArrayList<LogicExpression>();
            for (List<String> bucket : buckets) {
                if (bucket.isEmpty()) {
                    retval.add(null);
                } else {
                    StringBuffer newExpression = new StringBuffer();
                    boolean needComma = false;
                    for (String part : bucket) {
                        if (needComma) {
                            newExpression.append(" and ");
                        }
                        needComma = true;
                        newExpression.append("(" + part + ")");
                    }
                    retval.add(new LogicExpression(newExpression.toString()));
                }
            }
            return retval;
        }
    }

    /**
     * Take a Collection of String variable names, and return the part of the Logic Expression that
     * contains those variables.
     *
     * @param variables a Collection of variable names
     * @return a section of the LogicExpression
     * @throws IllegalArgumentException if there are unrecognised variables, or if the expression
     * cannot be split up in that way
     */
    public LogicExpression getSection(Collection<String> variables) {
        if (variables.isEmpty()) {
            return null;
        }
        if (!getVariableNames().containsAll(variables)) {
            throw new IllegalArgumentException("Unrecognised variables in request");
        }
        if (root instanceof Variable) {
            return this;
        } else if (root instanceof Or) {
            if (variables.containsAll(getVariableNames())) {
                return this;
            } else {
                throw new IllegalArgumentException("Expression " + toString() + " cannot be split");
            }
        } else {
            And and = (And) root;
            StringBuffer retval = new StringBuffer();
            boolean needComma = false;
            for (Node node : and.getChildren()) {
                Set<String> hasVariables = new HashSet<String>();
                getVariableNames(hasVariables, node);
                boolean containsAll = true;
                boolean containsNone = true;
                for (String var : hasVariables) {
                    if (variables.contains(var)) {
                        containsNone = false;
                    } else {
                        containsAll = false;
                    }
                }
                if ((!containsNone) && (!containsAll)) {
                    throw new IllegalArgumentException("Expression " + node + " cannot be split");
                }
                if (containsAll) {
                    if (needComma) {
                        retval.append(" and ");
                    }
                    needComma = true;
                    retval.append("(" + node.toString() + ")");
                }
            }
            return new LogicExpression(retval.toString());
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (o instanceof LogicExpression) {
            return toString().equals(o.toString());
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * Node of parse tree.
     */
    public abstract class Node
    {
    }

    /**
     * An operator node.
     */
    public abstract class Operator extends Node
    {
        private Set<Node> children = new LinkedHashSet<Node>();

        private Operator(AST ast) {
            if (ast != null) {
                AST child = ast.getFirstChild();
                while (child != null) {
                    Node childNode = null;
                    if (child.getText().equals("or")) {
                        childNode = new Or(child);
                    } else if (child.getText().equals("and")) {
                        childNode = new And(child);
                    } else {
                        childNode = new Variable(child.getText());
                    }
                    addChild(childNode);
                    child = child.getNextSibling();
                }
            }
        }

        /**
         * Override to provide text symbol for this operator. Used in toString.
         * @return operator name
         */
        protected abstract String getOperator();

        /**
         * Produce an expression for this branch of the tree.
         * @return expression representing this branch
         */
        @Override
        public String toString() {
            StringBuffer expr = new StringBuffer();
            boolean needComma = false;
            for (Node child : getChildren()) {
                if (needComma) {
                    expr.append(" " + getOperator() + " ");
                }
                needComma = true;
                String subexpr = child.toString();
                if (child instanceof Or && this instanceof And) {
                    subexpr = "(" + subexpr + ")";
                }
                expr.append(subexpr);
            }
            return expr.toString();
        }

        /**
         * Get an unmodifiable copy of the node's children.
         * @return unmodifiable set of node children
         */
        public Set<Node> getChildren() {
            return Collections.unmodifiableSet(children);
        }

        private void removeChild(Node child) {
            children.remove(child);
        }

        /**
         * Adds a node to the collection of children.
         *
         * @param child the new Node
         */
        protected void addChild(Node child) {
            children.add(child);
        }
    }

    /**
     * An AND operator node.
     */
    public class And extends Operator
    {
        private And(AST ast) {
            super(ast);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected String getOperator() {
            return "and";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void addChild(Node child) {
            if (child instanceof And) {
                for (Node subChild : ((And) child).getChildren()) {
                    addChild(subChild);
                }
            } else {
                super.addChild(child);
            }
        }
    }

    /**
     * An OR operator node.
     */
    public class Or extends Operator
    {
        private Or(AST ast) {
            super(ast);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected String getOperator() {
            return "or";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void addChild(Node child) {
            if (child instanceof Or) {
                for (Node subChild : ((Or) child).getChildren()) {
                    addChild(subChild);
                }
            } else {
                super.addChild(child);
            }
        }
    }

    /**
     * A variable node.
     */
    public class Variable extends Node
    {
        private String name;

        private Variable(String name) {
            this.name = name;
        }

        /**
         * Get variable name.
         * @return variable name
         */
        public String getName() {
            return name;
        }

        /**
         * Just returns the variable name.
         * @return string representation of this node
         */
        @Override
        public String toString() {
            return getName();
        }
    }
}
