package com.pl.erdc2.erdconstructor2.editor;

import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;
import com.pl.erdc2.erdconstructor2.api.RelationshipNode;
import java.awt.Point;
import java.awt.Rectangle;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.MoveProvider;
import org.netbeans.api.visual.action.MoveStrategy;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;

public class RelationshipWidget extends ConnectionWidget{
    private final RelationshipNode bean;
    private LabelWidget label;
    private final ConnectionPoint point;
    
    public RelationshipWidget(Scene scene, RelationshipNode bean) {
        super(scene);
        this.bean = bean;
        point = new ConnectionPoint (this.getScene(),this);
        point.getActions().addAction(new MySelectWidgetAction());
        ((GraphSceneImpl)this.getScene()).getInteractionLayer().addChild(point);
        point.hide();
        point.getActions().addAction(ActionFactory.createMoveAction(new MoveStrategy () {
            @Override
            public Point locationSuggested (Widget widget, Point originalLocation, Point suggestedLocation) {
                RelationshipWidget rw = ((ConnectionPoint)widget).getRelationshipWidget();
                rw.reroute();
                rw.revalidate();
                return suggestedLocation;
            }
        },new MoveProvider () {
            @Override
            public void movementStarted (Widget widget) {
            }
            @Override
            public void movementFinished (Widget widget) {
                RelationshipWidget rw = ((ConnectionPoint)widget).getRelationshipWidget();
                rw.reroute();
                rw.revalidate();
            }
            @Override
            public Point getOriginalLocation (Widget widget) {
                return widget.getPreferredLocation ();
            }
            @Override
            public void setNewLocation (Widget widget, Point location) {
                widget.setPreferredLocation (location);
            }
        }));
    }

    @Override
    public void notifyStateChanged (ObjectState previousState, ObjectState state) {
        setForeground (this.getLineColor() != null ? this.getLineColor() : getScene ().getLookFeel ().getLineColor (state));
        setPaintControlPoints (state.isSelected ());
        if(state.isFocused()){
            label.setBackground(getScene().getLookFeel().getLineColor(state).brighter());
            point.show();
        }
        else{
            label.setBackground(getScene().getLookFeel().getBackground());
            point.hide();
        }
    }
    
    public RelationshipNode getBean() {
        return bean;
    }

    public LabelWidget getLabel() {
        return label;
    }

    public void setLabel(LabelWidget label) {
        this.label = label;
    }

    public ConnectionPoint getPoint() {
        return point;
    }

    public void updateControlPointPosition(){
        Point p = new Point();
        Rectangle a = getSourceAnchor().getRelatedWidget().convertLocalToScene(this.getSourceAnchor().getRelatedWidget().getBounds());
        Rectangle b = getTargetAnchor().getRelatedWidget().convertLocalToScene(this.getTargetAnchor().getRelatedWidget().getBounds());
        
        if(a.y > b.y){
            Rectangle t = b;
            b=a;
            a=t;
        }
        if(a.y+a.height>=b.y){
            if(a.y+a.height>=b.y+b.height)
                p.y=b.y+ (b.height)/2;
            else
                p.y = a.y+a.height - (a.y+a.height-b.y)/2;
        }
        else
            p.y = a.y+a.height + (b.y - (a.y+a.height))/2;
        
        if(a.x > b.x){
            Rectangle t = b;
            b=a;
            a=t;
        }
        if(a.x+a.width>=b.x){
            if(a.x+a.width>=b.x+b.width)
                p.x=b.x + b.width/2;
            else
                p.x = a.x+a.width - (a.x+a.width-b.x)/2;
        }
        else
            p.x = a.x+a.width +  (b.x - (a.x+a.width))/2;
        
        this.point.setPreferredLocation(p);
        point.revalidate();
        this.reroute();
        this.revalidate();
        this.getScene().validate();
    }
}
