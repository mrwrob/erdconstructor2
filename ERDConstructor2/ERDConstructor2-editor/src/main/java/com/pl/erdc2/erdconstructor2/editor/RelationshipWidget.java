package com.pl.erdc2.erdconstructor2.editor;

import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;
import com.pl.erdc2.erdconstructor2.api.RelationshipNode;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.LabelWidget;

public class RelationshipWidget extends ConnectionWidget{
    private final RelationshipNode bean;
    private LabelWidget label;
    
    public RelationshipWidget(Scene scene, RelationshipNode bean) {
        super(scene);
        this.bean = bean;
    }

    @Override
    public void notifyStateChanged (ObjectState previousState, ObjectState state) {
        setForeground (this.getLineColor() != null ? this.getLineColor() : getScene ().getLookFeel ().getLineColor (state));
        setPaintControlPoints (state.isSelected ());
        if(state.isFocused()){
            label.setBackground(getScene().getLookFeel().getLineColor(state).brighter());
        }
        else{
            label.setBackground(getScene().getLookFeel().getBackground());
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
}
