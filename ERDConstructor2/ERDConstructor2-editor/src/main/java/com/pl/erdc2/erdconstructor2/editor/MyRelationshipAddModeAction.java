package com.pl.erdc2.erdconstructor2.editor;

import com.pl.erdc2.erdconstructor2.api.Entity;
import com.pl.erdc2.erdconstructor2.api.EntityExplorerManagerProvider;
import com.pl.erdc2.erdconstructor2.api.Relationship;
import com.pl.erdc2.erdconstructor2.api.RelationshipNode;
import java.beans.IntrospectionException;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.openide.nodes.Children;
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
            r.setSourceEntity(((EntityWidget)firstWidgetOfRelationship).getBean().getLookup().lookup(Entity.class));
            r.setDestinationEntity(((EntityWidget)firstWidgetOfRelationship).getBean().getLookup().lookup(Entity.class));
            try {
                node = new RelationshipNode(r, Children.LEAF);
                Node[] toAdd = {node};
                EntityExplorerManagerProvider.getRelatioshipNodeRoot().getChildren().add(toAdd);
            } catch (IntrospectionException ex) {
                Exceptions.printStackTrace(ex);
                return WidgetAction.State.CONSUMED;
            }
            
            ConnectionWidget conn = new ConnectionWidget (gs);
            conn.setRouter(RouterFactory.createDirectRouter());
            conn.setSourceAnchor (AnchorFactory.createFreeRectangularAnchor (firstWidgetOfRelationship, true));
            conn.setTargetAnchor (AnchorFactory.createFreeRectangularAnchor (widget, true));

            LabelWidget label = new LabelWidget (gs, node.getDisplayName());
            label.setOpaque(true);
            label.getActions().addAction(ActionFactory.createMoveAction());
            conn.addChild(label);
            conn.setConstraint(label, LayoutFactory.ConnectionWidgetLayoutAlignment.CENTER_RIGHT, 0.5f);
            
            gs.getConnectionLayer().addChild(conn);

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