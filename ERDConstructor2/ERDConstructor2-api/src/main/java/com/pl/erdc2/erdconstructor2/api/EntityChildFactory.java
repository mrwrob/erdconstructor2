package com.pl.erdc2.erdconstructor2.api;

import java.beans.IntrospectionException;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;


public class EntityChildFactory extends ChildFactory<Entity> {

    @Override
    protected boolean createKeys(List<Entity> list) {
        return true;
    }

    @Override
    protected Node createNodeForKey(Entity key) {
        EntityNode node = null;  
        try {
            node = new EntityNode(key, Children.create(new ColumnChildFactory(), true));
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }
    
    
    
}
