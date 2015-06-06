package com.pl.erdc2.erdconstructor2.api;

import java.beans.IntrospectionException;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;


public class ColumnChildFactory extends ChildFactory<Column> { 

    @Override
    protected boolean createKeys(List<Column> toPopulate) {
        return true;
    }

    @Override
    protected Node createNodeForKey(Column key) {
        ColumnNode node = null;
        try {
            node = new ColumnNode(key);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }
    
}
