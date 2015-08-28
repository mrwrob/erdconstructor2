package com.pl.erdc2.erdconstructor2.api;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.beans.IntrospectionException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.Lookups;

@Messages({
    "# {0} - entity",
    "EntityDefaultName=Entity {0}"
})
public class EntityNode extends BeanNode<Entity> {      
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

    @Override
    public Action[] getActions(boolean popup) {
        return new Action[]{new DeleteAction(this)};
    }

    private class DeleteAction extends AbstractAction {

        EntityNode entityNode;
        
        public DeleteAction(EntityNode entityNode) {
            this.entityNode = entityNode;
            putValue(NAME, "Delete Entity");
        }

        @Override
        public void actionPerformed(ActionEvent e) {                
            Node root = EntityExplorerManagerProvider.getEntityNodeRoot();
            Node[] toDelete = {entityNode};
            root.getChildren().remove(toDelete);
        }
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
}
