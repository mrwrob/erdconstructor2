package com.pl.erdc2.erdconstructor2.editor;

import com.pl.erdc2.erdconstructor2.api.EntityExplorerManagerProvider;
import com.pl.erdc2.erdconstructor2.api.EntityNode;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.widget.LayerWidget;

public class GraphSceneImpl extends GraphScene implements LookupListener{
    private final LayerWidget mainLayer;
    private final Random random;
    private final LayerWidget connectionLayer;
    private final LayerWidget interactionLayer;
    private final Lookup.Result<EntityNode> entitesLookup;
    private final TopComponent associatedTopComponent;

    private boolean addRelationshipMode;
    
    public GraphSceneImpl(TopComponent tc) {
        associatedTopComponent = tc;
        this.random = new Random();
        mainLayer = new LayerWidget(this);
        connectionLayer = new LayerWidget(this);
        interactionLayer = new LayerWidget(this);
        
        EntityExplorerManagerProvider.getEntityNodeRoot().addNodeListener(new EntityNodeRootNodeListener(this));
        
        entitesLookup = Utilities.actionsGlobalContext().lookupResult(EntityNode.class);
        entitesLookup.addLookupListener(this);
        
        getActions().addAction(new MyRelationshipAddModeAction());
        
        addChild(connectionLayer);
        addChild(interactionLayer);
        addChild(mainLayer);
        getActions().addAction(ActionFactory.createZoomAction());
        getActions().addAction(ActionFactory.createPanAction());
        getActions().addAction(ActionFactory.createWheelPanAction());
        getActions().addAction(new MySelectWidgetAction());

        for(Node n : EntityExplorerManagerProvider.getEntityNodeRoot().getChildren().getNodes()){
            if(n instanceof EntityNode){
                this.addNode(n);
            }
        }
    }
    
    @Override
    protected Widget attachNodeWidget(Object n) {
        EntityNode bean;
        if(n instanceof EntityNode){
            bean = (EntityNode)n;
        }
        else
            return null;
        
        EntityWidget widget = new EntityWidget(this, bean);
        widget.setPreferredSize(new Dimension(200, 100));
        widget.setPreferredLocation(new Point(10+random.nextInt(400), 10+random.nextInt(400)));
        
        widget.getActions().addAction(new MyRelationshipAddModeAction());
        widget.getActions().addAction(this.createWidgetHoverAction());
        widget.getActions().addAction(ActionFactory.createResizeAction());
        widget.getActions().addAction(new MySelectWidgetAction());
       
        widget.getActions().addAction( ActionFactory.createAlignWithMoveAction(mainLayer,interactionLayer,ActionFactory.createDefaultAlignWithMoveDecorator()));
        
        widget.recalculateMinSize();
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

    public LayerWidget getMainLayer() {
        return mainLayer;
    }

    public TopComponent getAssociatedTopComponent() {
        return associatedTopComponent;
    }

    public boolean isAddRelationshipMode() {
        return addRelationshipMode;
    }

    public void setAddRelationshipMode(boolean addRelationshipMode) {
        this.addRelationshipMode = addRelationshipMode;
        if(addRelationshipMode==true){
            this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }
        else{
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    public void toggleAddRelationshipMode(){
        this.setAddRelationshipMode(!addRelationshipMode);
    }

    public LayerWidget getConnectionLayer() {
        return connectionLayer;
    }
}

