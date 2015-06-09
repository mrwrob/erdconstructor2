package com.pl.erdc2.erdconstructor2.api;

import java.awt.Image;
import java.beans.IntrospectionException;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.Lookups;

@Messages({
    "# {0} - entity",
    "EntityDefaultName=Entity {0}"
})
public class EntityNode extends BeanNode<Entity> {
    private static int ct=0;
    
    public EntityNode(Entity bean) throws IntrospectionException {
        super(bean, Children.LEAF, Lookups.singleton(bean));
        bean.setName(Bundle.EntityDefaultName(++ct));
        setDisplayName(bean.getName());
    }

    public EntityNode(Entity bean, Children children) throws IntrospectionException {
        super(bean, children, Lookups.singleton(bean));
        bean.setName(Bundle.EntityDefaultName(++ct));
        setDisplayName(bean.getName());
    }
    
    @Override
    public Image getIcon (int type) {    
        return ImageUtilities.loadImage("images/entityIcon.png");
    }
    @Override
    public Image getOpenedIcon(int i) {
        return getIcon (i);
    }
}
