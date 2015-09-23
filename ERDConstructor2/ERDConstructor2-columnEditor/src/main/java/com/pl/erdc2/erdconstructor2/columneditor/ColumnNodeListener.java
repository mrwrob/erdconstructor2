/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pl.erdc2.erdconstructor2.columneditor;

import org.openide.nodes.NodeAdapter;
import org.openide.nodes.NodeEvent;

/**
 *
 * @author Kuba
 */
public class ColumnNodeListener extends NodeAdapter{
    private EntityPanel ep;
    public ColumnNodeListener(EntityPanel ep)
    {
        this.ep=ep;
    }
    
    @Override
    public void nodeDestroyed (NodeEvent ev)
    {
        ep.updateTable();
    }
    
    
}
