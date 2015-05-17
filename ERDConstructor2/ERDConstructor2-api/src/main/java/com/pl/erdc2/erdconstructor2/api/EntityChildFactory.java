/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pl.erdc2.erdconstructor2.api;

import java.beans.IntrospectionException;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;


/**
 *
 * @author Piotrek
 */
public class EntityChildFactory extends ChildFactory<Entity> {

    @Override
    protected boolean createKeys(List<Entity> list) {
        //test entities
        list.add(new Entity());
        list.add(new Entity());
        return true;
    }

    @Override
    protected Node createNodeForKey(Entity key) {
        EntityNode node = null;
        
        try {
            node = new EntityNode(key);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }
    
    
    
}
