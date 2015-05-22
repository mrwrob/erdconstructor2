package com.pl.erdc2.erdconstructor2.editor;

import java.beans.PropertyVetoException;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author Piotrek
 */
public class MySelectWidgetAction extends WidgetAction.Adapter {
    private final ExplorerManager em;
    
    public MySelectWidgetAction(ExplorerManager _em) {
        em=_em;
    }
    
    @Override
        public WidgetAction.State mousePressed(Widget widget, WidgetAction.WidgetMouseEvent event) {
            widget.getScene().setFocusedWidget(widget);
            widget.getScene().repaint();
            if(widget instanceof EntityWidget){
                Node[] list = {((EntityWidget)widget).getBean()};
                try {
                    em.setSelectedNodes(list);
                } catch (PropertyVetoException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            return WidgetAction.State.CHAIN_ONLY;
        }
        
    }