package com.pl.erdc2.erdconstructor2.editor;

import com.pl.erdc2.erdconstructor2.api.Entity;
import com.pl.erdc2.erdconstructor2.api.EntityExplorerManagerProvider;
import com.pl.erdc2.erdconstructor2.api.Relationship;
import com.pl.erdc2.erdconstructor2.api.RelationshipNode;
import java.beans.IntrospectionException;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

public class MyRelationshipAddModeAction extends WidgetAction.Adapter {
    private static boolean drawingRelationship =false;
    private static Widget firstWidgetOfRelationship;
    private static ConnectionWidget shadow;

    @Override
    public WidgetAction.State mousePressed(Widget widget, WidgetAction.WidgetMouseEvent event) {
        
        GraphSceneImpl gs = (GraphSceneImpl)widget.getScene();

        if(widget instanceof EntityWidget){    
            if(gs.isAddRelationshipMode()){
                drawingRelationship = true;
                firstWidgetOfRelationship = widget;
                
                shadow = new ConnectionWidget(gs);
                shadow.setRouter(RouterFactory.createDirectRouter());
                shadow.setSourceAnchor(AnchorFactory.createFreeRectangularAnchor(firstWidgetOfRelationship, true));
                shadow.setTargetAnchor(AnchorFactory.createFixedAnchor(event.getPoint()));
                gs.getConnectionLayer().addChild(shadow);
                
                return WidgetAction.State.CONSUMED;
            }
        }
        return WidgetAction.State.CHAIN_ONLY;
    } 
    
    @Override
    public State mouseReleased(Widget widget, WidgetMouseEvent event) {
        GraphSceneImpl gs = (GraphSceneImpl)widget.getScene();
        if(!drawingRelationship)
            return WidgetAction.State.CHAIN_ONLY;
        
        drawingRelationship = false;
        gs.getConnectionLayer().removeChild(shadow);
        shadow=null;
        
        if(widget instanceof EntityWidget){    
            Relationship r = new Relationship();
            RelationshipNode node;
            r.setSourceEntityId(((EntityWidget)firstWidgetOfRelationship).getBean().getLookup().lookup(Entity.class).getId());
            r.setDestinationEntityId(((EntityWidget)widget).getBean().getLookup().lookup(Entity.class).getId());
            try {
                node = new RelationshipNode(r);
                Node[] toAdd = {node};
                EntityExplorerManagerProvider.getRelatioshipNodeRoot().getChildren().add(toAdd);
            } catch (IntrospectionException ex) {
                Exceptions.printStackTrace(ex);
            }
            
            return WidgetAction.State.CONSUMED;
        }
        
        return WidgetAction.State.CHAIN_ONLY;
    }

    @Override
    public State mouseDragged(Widget widget, WidgetMouseEvent event) {
        if(drawingRelationship && shadow!=null){
            shadow.setVisible(true);
            if(widget instanceof GraphSceneImpl){
                shadow.setTargetAnchor(AnchorFactory.createFixedAnchor(event.getPoint()));
                shadow.repaint();
            }
            else if(widget instanceof EntityWidget && !widget.equals(firstWidgetOfRelationship)){
                shadow.setTargetAnchor(AnchorFactory.createFreeRectangularAnchor (widget, true));
                shadow.repaint();
            }else{
                shadow.setVisible(false);
                shadow.repaint();
            }
        }              
        return WidgetAction.State.CHAIN_ONLY;
    }
    
    @Override
    public State mouseEntered(Widget widget, WidgetMouseEvent event) {
        if(drawingRelationship && shadow!=null && widget instanceof GraphSceneImpl){
            GraphSceneImpl gs = (GraphSceneImpl)widget.getScene();
            gs.getConnectionLayer().removeChild(shadow);
            shadow=null;
            drawingRelationship=false;
        }
        return WidgetAction.State.CHAIN_ONLY;
    }
}