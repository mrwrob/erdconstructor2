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
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;


public class GraphSceneImpl extends GraphScene implements LookupListener{
    private final LayerWidget mainLayer;
    private final Random r;
    private final ExplorerManager em;
    private final LayerWidget connectionLayer;
    private final LayerWidget interactionLayer;
    private final Lookup.Result<EntityNode> entitesLookup;


    public GraphSceneImpl() {
        this.r = new Random();
        mainLayer = new LayerWidget(this);
        connectionLayer = new LayerWidget(this);
        interactionLayer = new LayerWidget(this);
        
        em = EntityExplorerManagerProvider.getInstance().getExplorerManager();
        em.getRootContext().addNodeListener(new EntityNodeRootNodeListener(this));
        
        entitesLookup = Utilities.actionsGlobalContext().lookupResult(EntityNode.class);
        entitesLookup.addLookupListener(this);
        
        addChild(mainLayer);
        addChild(connectionLayer);
        addChild(interactionLayer);
        getActions().addAction(ActionFactory.createZoomAction());
        getActions().addAction(ActionFactory.createPanAction());
        getActions().addAction(ActionFactory.createWheelPanAction());
        getActions().addAction(new MySelectWidgetAction(em));
          
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
        
        EntityWidget widget = new EntityWidget(this, bean);
        widget.setTitle(entity.getName());
        widget.setPreferredSize(new Dimension(200, 100));
        widget.setPreferredLocation(new Point(10+r.nextInt(400), 10+r.nextInt(400)));
       
        widget.getActions().addAction(this.createWidgetHoverAction());
        widget.getActions().addAction(ActionFactory.createResizeAction());
        widget.getActions().addAction(new MySelectWidgetAction(em));
        widget.getActions().addAction( ActionFactory.createAlignWithMoveAction(mainLayer,interactionLayer,ActionFactory.createDefaultAlignWithMoveDecorator()));

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

    @Override
    public void resultChanged(LookupEvent ev) {
        if(entitesLookup.allItems().size()==1){
            EntityNode node = entitesLookup.allInstances().iterator().next();
            if(node==null)
                return;
            
            for(Widget w : mainLayer.getChildren()){
                if(w instanceof EntityWidget){
                    EntityWidget ew = (EntityWidget)w;
                    if(ew.getBean().equals(node)){
                        ew.getScene().setFocusedWidget(ew);
                        ew.getScene().repaint();
                    }
                }
            }
            
        }
    }
}

