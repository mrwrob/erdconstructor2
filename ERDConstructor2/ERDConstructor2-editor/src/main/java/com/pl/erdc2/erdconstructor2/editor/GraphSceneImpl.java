/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pl.erdc2.erdconstructor2.editor;

import com.pl.erdc2.erdconstructor2.api.Entity;
import com.pl.erdc2.erdconstructor2.api.EntityExplorerManagerProvider;
import com.pl.erdc2.erdconstructor2.api.EntityNode;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.Node;


public class GraphSceneImpl extends GraphScene{
    private final LayerWidget mainLayer;
    private final Random r;
    private final ExplorerManager em;
    
    public GraphSceneImpl() {
        this.r = new Random();
        mainLayer = new LayerWidget(this);
        addChild(mainLayer);
        
        em = EntityExplorerManagerProvider.getInstance().getExplorerManager();
        em.getRootContext().addNodeListener(new EntityNodeRootNodeListener(this));
        
        for(Node n : em.getRootContext().getChildren().getNodes()){
            if(n instanceof EntityNode){
                this.addNode(n);
            }
        }
    }

    public void addEntity(EntityNode en){
        this.attachNodeWidget(en);
    }
    
    @Override
    protected Widget attachNodeWidget(Object n) {
        EntityNode bean;
        Entity entity;
        if(n instanceof EntityNode){
            bean = (EntityNode)n;
        }
        else
            return null;
        
        entity = bean.getLookup().lookup(Entity.class);
        
        EntityWidget widget = new EntityWidget(this);
        widget.setTitle(entity.getName());
        widget.setPreferredSize(new Dimension(200, 100));
        widget.setPreferredLocation(new Point(10+r.nextInt(400), 10+r.nextInt(400)));
        widget.getActions().addAction(ActionFactory.createMoveAction());
       
        mainLayer.addChild(widget);
        return widget;
    }

    @Override
    protected Widget attachEdgeWidget(Object e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void attachEdgeSourceAnchor(Object e, Object n, Object n1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void attachEdgeTargetAnchor(Object e, Object n, Object n1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
