package com.pl.erdc2.erdconstructor2.actions;

import com.pl.erdc2.erdconstructor2.api.Entity;
import com.pl.erdc2.erdconstructor2.api.EntityExplorerManagerProvider;
import com.pl.erdc2.erdconstructor2.api.Relationship;
import com.pl.erdc2.erdconstructor2.editor.EditorTopComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

/**
 *
 * @author Piotrek
 */
@ActionID(category = "File", id = "com.pl.erdc2.SaveFileAction")
@ActionRegistration(displayName = "#CTL_SaveFileAction")
@ActionReference(path = "Menu/File", position = 9)
@Messages({"CTL_SaveFileAction=Save File",
        "CTL_SaveFileActionButton=Save",
        "Save_Error=An error occurred while attempting to save to your hard drive"
})
public final class SaveFileAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        File home = new File(System.getProperty("user.home"));
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("ERDC files", "erdc");
        File toAdd = new FileChooserBuilder("user-dir").setTitle(Bundle.CTL_SaveFileAction()).
                setDefaultWorkingDirectory(home).setApproveText(Bundle.CTL_SaveFileActionButton()).setFileFilter(filter1).showSaveDialog();
        if (toAdd != null) {            
            try {
                if(!toAdd.getAbsolutePath().endsWith(".erdc"))
                    toAdd = new File(toAdd.getAbsolutePath()+".erdc");
                
                if(!toAdd.exists())
                    toAdd.createNewFile();
                
                FileOutputStream fout = new FileOutputStream(toAdd);
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                
                EditorTopComponent etc;
                etc = (EditorTopComponent) WindowManager.getDefault().findTopComponent("editorTopComponent");
                etc.getScene().prepareToSerialize();
                
                SaveWrapper wrap = new SaveWrapper();
                ArrayList<Entity> entities = new ArrayList<>();
                for(Node n : EntityExplorerManagerProvider.getEntityNodeRoot().getChildren().getNodes()){
                    Entity ent = n.getLookup().lookup(Entity.class);
                    if(ent!=null)
                        entities.add(ent);
                }
                wrap.entities = entities.toArray(new Entity[entities.size()]);
                
                ArrayList<Relationship> relations = new ArrayList<>();
                for(Node n : EntityExplorerManagerProvider.getRelatioshipNodeRoot().getChildren().getNodes()){
                    Relationship rel = n.getLookup().lookup(Relationship.class);
                    if(rel!=null)
                        relations.add(rel);
                }
                wrap.relations = relations.toArray(new Relationship[relations.size()]);
                oos.writeObject(wrap);
                oos.close();
                fout.close();
            } catch (IOException ex) {
                NotifyDescriptor.Message message = new NotifyDescriptor.Message(Bundle.Save_Error());
                message.setMessageType(NotifyDescriptor.Message.ERROR_MESSAGE);
                Object result = DialogDisplayer.getDefault().notify(message);
                Exceptions.printStackTrace(ex);
            }
            
        }
    }
    
    public static class SaveWrapper implements Serializable{
        public Entity[] entities;
        public Relationship[] relations;
    }
}
