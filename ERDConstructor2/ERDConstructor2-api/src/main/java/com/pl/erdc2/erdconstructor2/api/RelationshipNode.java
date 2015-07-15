package com.pl.erdc2.erdconstructor2.api;

import java.awt.Image;
import java.beans.IntrospectionException;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.Lookups;

@Messages({
    "# {0} - relationship",
    "RelationshipDefaultName=Relatioship {0}"
})
public class RelationshipNode extends BeanNode<Relationship> {
    private static int ct=0;
    
    public RelationshipNode(Relationship bean) throws IntrospectionException {
        super(bean, Children.LEAF, Lookups.singleton(bean));
        bean.setName(Bundle.RelationshipDefaultName(++ct));
        setDisplayName(bean.getName());
    }
    public RelationshipNode(Relationship bean, Children children) throws IntrospectionException {
        super(bean, children, Lookups.singleton(bean));
        bean.setName(Bundle.RelationshipDefaultName(++ct));
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
    
}
