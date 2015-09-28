package com.pl.erdc2.erdconstructor2.api;

import java.awt.Image;
import java.beans.IntrospectionException;

import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import java.util.Observable;
import java.util.Observer;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.NodeListener;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.Lookups;

@Messages({
    "# {0} - entity",
    "EntityDefaultName=Entity {0}"
})

public class EntityNode extends BeanNode<Entity>  implements Observer{  
    public EntityNode(Entity bean) throws IntrospectionException {
        super(bean, Children.LEAF, Lookups.singleton(bean));
        if(bean.getId()==0){
            bean.setId(getNextIdValue());
            bean.setName(Bundle.EntityDefaultName(bean.getId()));
        }
        setDisplayName(bean.getName());
    }

    public EntityNode(Entity bean, Children children) throws IntrospectionException {
        super(bean, children, Lookups.singleton(bean));
        if(bean.getId()==0){
            bean.setId(getNextIdValue());
            bean.setName(Bundle.EntityDefaultName(bean.getId()));
        }
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
    
    private static int getNextIdValue(){
        int max=0;
        for(Node n : EntityExplorerManagerProvider.getEntityNodeRoot().getChildren().getNodes()){
            int id=0;
            if(n instanceof EntityNode)
                id=((EntityNode)n).getLookup().lookup(Entity.class).getId();
            max= id>max ? id : max;
        }
        return ++max;
    }

    
    @Override
    public void update(Observable o, Object arg) {
     /*   if(!(o instanceof Entity))
            return;
        
        Entity col = (Entity)o;
        String property = (String)arg;
        if(property.equals("name")){ 
            String old = this.getDisplayName();
            this.setDisplayName(col.getName());
            this.fireDisplayNameChange(old, col.getName());
        }*/
    }
}
