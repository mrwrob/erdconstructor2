package com.pl.erdc2.erdconstructor2.editor;

import com.pl.erdc2.erdconstructor2.api.EntityNode;
import java.beans.PropertyChangeEvent;
import org.openide.nodes.Node;
import org.openide.nodes.NodeEvent;
import org.openide.nodes.NodeListener;
import org.openide.nodes.NodeMemberEvent;
import org.openide.nodes.NodeReorderEvent;

/**
 *
 * @author Piotrek
 */
public class EntityNodeRootNodeListener implements NodeListener{
    private final GraphSceneImpl gs;

    public EntityNodeRootNodeListener(GraphSceneImpl _gs) {
        gs = _gs;
    }
    
    @Override
    public void childrenAdded(NodeMemberEvent ev) {
        for(Node n : ev.getDelta()){
            if(n instanceof EntityNode){
                EntityNode en = (EntityNode)n;
                en.addNodeListener(new ColumnNodeListener((gs)));
                gs.addNode(n);
                gs.validate();
            }
        }
    }

    @Override
    public void childrenRemoved(NodeMemberEvent ev) {
         for(Node n : ev.getDelta()){
            if(n instanceof EntityNode){
                System.out.println("EntityNode REMOVED");
            }
        }
    }

    @Override
    public void childrenReordered(NodeReorderEvent ev) {
        System.out.println("children reordered:");
    }

    @Override
    public void nodeDestroyed(NodeEvent ev) {
        System.out.println("children destroyed:");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("children property change:");
    }
    
}
