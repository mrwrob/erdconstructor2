package com.pl.erdc2.erdconstructor2.api;

import java.awt.Image;
import java.beans.IntrospectionException;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.Lookups;

@Messages({
    "# {0} - relationship",
    "RelationshipDefaultName=Relatioship {0}"
})
public class RelationshipNode extends BeanNode<Relationship> {

    
    public RelationshipNode(Relationship bean) throws IntrospectionException {
        super(bean, Children.LEAF, Lookups.singleton(bean));
        bean.setId(getNextIdValue());
        bean.setName(Bundle.RelationshipDefaultName(bean.getId()));
        setDisplayName(bean.getName());
    }
    public RelationshipNode(Relationship bean, Children children) throws IntrospectionException {
        super(bean, children, Lookups.singleton(bean));
        bean.setId(getNextIdValue());
        bean.setName(Bundle.RelationshipDefaultName(bean.getId()));
        setDisplayName(bean.getName());
    }
    @Override
    public Image getIcon (int type) {    
        return ImageUtilities.loadImage("images/relationshipIcon.png");
    }
    @Override
    public Image getOpenedIcon(int i) {
        return getIcon (i);
    }
    
    private static int getNextIdValue(){
        int max=0;
        for(Node n : EntityExplorerManagerProvider.getRelatioshipNodeRoot().getChildren().getNodes()){
            int id=0;
            if(n instanceof RelationshipNode)
                id=((RelationshipNode)n).getLookup().lookup(Relationship.class).getId();
            max= id>max ? id : max;
        }
        return ++max;
    }
    
}
