package com.pl.erdc2.erdconstructor2.api;

import java.awt.Image;
import java.util.List;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author Piotrek
 * Entity Explorer manager provider based on code from https://blogs.oracle.com/geertjan/entry/sharing_explorermanagers_between_topcomponents
 */
@Messages({
    "EntityNodeRootName=Entities",
    "RelatioshipNodeRootName=Relationships"
})
public class EntityExplorerManagerProvider{
    private static ExplorerManager em;
    private static final EntityExplorerManagerProvider instance = new EntityExplorerManagerProvider();
    private static Node entityNodeRoot;
    private static Node relatioshipNodeRoot;
    
    private EntityExplorerManagerProvider(){
        em =  new ExplorerManager();
        em.setRootContext(new AbstractNode(Children.create(new ChildFactory() {
            @Override
            protected boolean createKeys(List list) {return true;}
        }, true)));
        entityNodeRoot = new AbstractNode(Children.create(new EntityChildFactory(), true)){
            @Override
            public Image getIcon(int type) {
                return ImageUtilities.loadImage("images/entities.png");
            }
            @Override
            public Image getOpenedIcon(int i) {
                return getIcon (i);
            }
        };
        entityNodeRoot.setDisplayName(Bundle.EntityNodeRootName());
        
        relatioshipNodeRoot = new AbstractNode(Children.create(new RelationshipChildFactory(), true)){
            @Override
            public Image getIcon(int type) {
                return ImageUtilities.loadImage("images/relationships.png");
            }
            @Override
            public Image getOpenedIcon(int i) {
                return getIcon (i);
            }
        };
        relatioshipNodeRoot.setDisplayName(Bundle.RelatioshipNodeRootName());
        
        Node[] nodes = {entityNodeRoot, relatioshipNodeRoot};
        em.getRootContext().getChildren().add(nodes);
    }
    
    public static void clean(){
       entityNodeRoot.getChildren().remove(entityNodeRoot.getChildren().getNodes());
       relatioshipNodeRoot.getChildren().remove(relatioshipNodeRoot.getChildren().getNodes());
    }
    
    public static ExplorerManager getExplorerManager(){
        return em;
    }

    public static Node getEntityNodeRoot() {
        return entityNodeRoot;
    }

    public static Node getRelatioshipNodeRoot() {
        return relatioshipNodeRoot;
    }
    
}
