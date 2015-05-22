/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pl.erdc2.erdconstructor2.api;

import org.openide.explorer.ExplorerManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 *
 * @author Piotrek
 * Entity Explorer manager provider based on code from https://blogs.oracle.com/geertjan/entry/sharing_explorermanagers_between_topcomponents
 */
public class EntityExplorerManagerProvider{
    private static ExplorerManager em;
    private static final EntityExplorerManagerProvider instance = new EntityExplorerManagerProvider();
    
    private EntityExplorerManagerProvider(){
        em =  new ExplorerManager();
        em.setRootContext(new AbstractNode(Children.create(new EntityChildFactory(), true)));
    }
    
    public static EntityExplorerManagerProvider getInstance(){
        return instance;
    } 
    
    public ExplorerManager getExplorerManager(){
        return em;
    }

}
