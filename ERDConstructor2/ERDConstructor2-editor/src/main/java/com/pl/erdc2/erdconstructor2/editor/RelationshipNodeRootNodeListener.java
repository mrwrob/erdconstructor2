package com.pl.erdc2.erdconstructor2.editor;

import com.pl.erdc2.erdconstructor2.api.RelationshipNode;
import java.io.IOException;
import java.util.Iterator;
import org.netbeans.api.visual.widget.Widget;
import org.openide.nodes.Node;
import org.openide.nodes.NodeAdapter;
import org.openide.nodes.NodeMemberEvent;
import org.openide.util.Exceptions;

/**
 *
 * @author Kuba
 */
public class RelationshipNodeRootNodeListener extends NodeAdapter{
     private final GraphSceneImpl gs;
     
     public RelationshipNodeRootNodeListener(GraphSceneImpl _gs)
     {
      gs=_gs;   
     }
     
     @Override
     public void childrenRemoved(NodeMemberEvent ev) 
     {
         Widget toRemove = null;
         for(Node n : ev.getDelta()){
            if(n instanceof RelationshipNode){
                for(Widget w : gs.getConnectionLayer().getChildren()){
                if(w instanceof RelationshipWidget){
                    RelationshipWidget rw = (RelationshipWidget)w;
                    if(rw.getBean().equals(n)){
                        toRemove=w;
                    }
                }
            }
                gs.getConnectionLayer().removeChild(toRemove);
                gs.validate();
            }
        }
     }
    
}
