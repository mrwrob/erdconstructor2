package com.pl.erdc2.erdconstructor2.actions;

import com.pl.erdc2.erdconstructor2.api.EntityExplorerManagerProvider;
import com.pl.erdc2.erdconstructor2.editor.EditorTopComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

/**
 *
 * @author Piotrek
 */
@ActionID(category = "File", id = "com.pl.erdc2.NewFileAction")
@ActionRegistration(displayName = "#CTL_NewFileAction")
@ActionReference(path = "Menu/File", position = 1)
@Messages("CTL_NewFileAction=New File")
public final class NewFileAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        EditorTopComponent etc;
        etc = (EditorTopComponent) WindowManager.getDefault().findTopComponent("editorTopComponent");
        etc.getScene().clean();
        EntityExplorerManagerProvider.clean();
        etc.getScene().revalidate();
        etc.getScene().repaint();
        etc.repaint();
    }    
}
