package com.pl.erdc2.erdconstructor2.editor;

import com.pl.erdc2.erdconstructor2.api.RelationshipNode;
import org.openide.nodes.Node;
import org.openide.nodes.NodeAdapter;
import org.openide.nodes.NodeMemberEvent;

/**
 *
 * @author Piotrek
 */
public class RelationshipNodeRootNodeListener extends NodeAdapter{
    private final GraphSceneImpl gs;

    public RelationshipNodeRootNodeListener(GraphSceneImpl gs) {
        this.gs = gs;
    }
    
    @Override
    public void childrenAdded(NodeMemberEvent ev) {
        for(Node n : ev.getDelta()){
            if(n instanceof RelationshipNode){
                gs.addNode(n);
                gs.validate();
            }
        }
    }
}
