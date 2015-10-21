package com.pl.erdc2.erdconstructor2.editor;

import com.pl.erdc2.erdconstructor2.api.Entity;
import com.pl.erdc2.erdconstructor2.api.EntityExplorerManagerProvider;
import com.pl.erdc2.erdconstructor2.api.Relationship;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;
import org.openide.nodes.Node;
import org.openide.util.NbBundle;

@NbBundle.Messages({
    "# {0} - entity",
    "Confirm_Entity_Delete=Are you sure you want to delete {0} entity?",
    "# {0} - relationship",
    "Confirm_Relationship_Delete=Are you sure you want to delete {0} relationship?",
    "Confirm=Confirm",
    "Yes_Option=Yes",
    "No_Option=No"})
public class MyKeyListener extends WidgetAction.Adapter {
    private static final Logger logger = Logger.getLogger(MyKeyListener.class);
    private static GraphSceneImpl scene;
    
    public MyKeyListener(GraphSceneImpl gs) {
        scene = gs;
    }

    @Override
    public State keyReleased(Widget widget, WidgetKeyEvent event) {
        if(event.getKeyCode()==KeyEvent.VK_DELETE){
            if(scene.getFocusedWidget() instanceof EntityWidget){
                EntityWidget ew = (EntityWidget)scene.getFocusedWidget();
                
                int response = JOptionPane.showOptionDialog(null,
                        (Object) Bundle.Confirm_Entity_Delete(ew.getBean().getDisplayName()), 
                        Bundle.Confirm(), JOptionPane.YES_NO_OPTION, 
                        JOptionPane.INFORMATION_MESSAGE, null, 
                        new String[]{Bundle.Yes_Option(), Bundle.No_Option()}, "default");
                if (response == JOptionPane.NO_OPTION)
                    return WidgetAction.State.CHAIN_ONLY;
                
                int entityId = ew.getBean().getLookup().lookup(Entity.class).getId();
                Node relationNodes[] = EntityExplorerManagerProvider.getRelatioshipNodeRoot().getChildren().getNodes();
                ArrayList<Node> listRelationsToDelete = new ArrayList<>();
                for(Node n: relationNodes){
                    Relationship r = n.getLookup().lookup(Relationship.class);
                    if(r.getSourceEntityId()== entityId || r.getDestinationEntityId() == entityId){
                        listRelationsToDelete.add(n);
                    }
                }
                Node[] arrayRelatonsToDelete = new Node[listRelationsToDelete.size()];
                arrayRelatonsToDelete = listRelationsToDelete.toArray(arrayRelatonsToDelete);

                EntityExplorerManagerProvider.getRelatioshipNodeRoot().getChildren().remove(arrayRelatonsToDelete); 
                
                Node[] toDelete = {ew.getBean()};
                EntityExplorerManagerProvider.getEntityNodeRoot().getChildren().remove(toDelete);
            }
            else if(scene.getFocusedWidget() instanceof RelationshipWidget){
                RelationshipWidget rw = (RelationshipWidget)scene.getFocusedWidget();
                
                int response = JOptionPane.showOptionDialog(null,
                        (Object) Bundle.Confirm_Relationship_Delete(rw.getBean().getDisplayName()), 
                        Bundle.Confirm(), JOptionPane.YES_NO_OPTION, 
                        JOptionPane.INFORMATION_MESSAGE, null, 
                        new String[]{Bundle.Yes_Option(), Bundle.No_Option()}, "default");
                if (response == JOptionPane.NO_OPTION)
                    return WidgetAction.State.CHAIN_ONLY;
                
                Node delete[] = {rw.getBean()};
                EntityExplorerManagerProvider.getRelatioshipNodeRoot().getChildren().remove(delete);
            }
        }
        return WidgetAction.State.CHAIN_ONLY;
    }

    @Override
    public State mouseClicked(Widget widget, WidgetMouseEvent event) {
        scene.getView().requestFocusInWindow();
        return WidgetAction.State.CHAIN_ONLY;    
    }
    
    
}