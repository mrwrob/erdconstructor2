package com.pl.erdc2.erdconstructor2.editor;

import com.pl.erdc2.erdconstructor2.api.EntityExplorerManagerProvider;
import java.beans.PropertyVetoException;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;


public class MySelectWidgetAction extends WidgetAction.Adapter {
    @Override
    public WidgetAction.State mousePressed(Widget widget, WidgetAction.WidgetMouseEvent event) {
        widget.getScene().setFocusedWidget(widget);
        widget.getScene().repaint();
        if(widget instanceof EntityWidget){
            Node[] list = {((EntityWidget)widget).getBean()};
            try {
                EntityExplorerManagerProvider.getExplorerManager().setSelectedNodes(list);
            } catch (PropertyVetoException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return WidgetAction.State.CHAIN_ONLY;
    }  
}