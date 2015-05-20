/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pl.erdc2.erdconstructor2.editor;

import com.pl.erdc2.erdconstructor2.api.Entity;
import com.pl.erdc2.erdconstructor2.api.EntityExplorerManagerProvider;
import com.pl.erdc2.erdconstructor2.api.EntityNode;
import java.awt.BorderLayout;
import java.util.Collection;
import javax.swing.JScrollPane;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.pl.erdc2.erdconstructor2.editor//editor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "EditorTopComponent",
        iconBase = "com/pl/erdc2/erdconstructor2/editor/graphIcon2.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "com.pl.erdc2.erdconstructor2.editor.EditorTopComponent")
@ActionReference(path = "Menu/Window" , position = 1)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_editorAction",
        preferredID = "editorTopComponent"
)
@Messages({
    "CTL_editorAction=Diagram editor",
    "CTL_editorTopComponent=Diagram editor",
    "HINT_editorTopComponent=This is a erd diagram editor window"
})
public final class EditorTopComponent extends TopComponent implements LookupListener{
    private final ExplorerManager em;

    public EditorTopComponent() {
        initComponents();
        setName(Bundle.CTL_editorTopComponent());
        setToolTipText(Bundle.HINT_editorTopComponent());
        setLayout(new BorderLayout());

        em = EntityExplorerManagerProvider.getInstance().getExplorerManager();
        
        GraphSceneImpl scene = new GraphSceneImpl();

        JScrollPane shapePane = new JScrollPane();

        shapePane.setViewportView(scene.createView());
        
        add(shapePane, BorderLayout.CENTER);
        //add(scene.createSatelliteView(), BorderLayout.WEST);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    Lookup.Result<Entity> allEntitiesInLookup;

    @Override
    public void resultChanged(LookupEvent ev) {
        //System.out.println("Result changed ");
        allEntitiesInLookup.allItems();
        if(!allEntitiesInLookup.allInstances().isEmpty()){
            Entity entity = allEntitiesInLookup.allInstances().iterator().next();
            //Selected entity
        } 
    }
    
    @Override
    public void componentOpened() {
        allEntitiesInLookup = Utilities.actionsGlobalContext().lookupResult(Entity.class);
        allEntitiesInLookup.addLookupListener(this);
    }

    @Override
    public void componentClosed() {
        allEntitiesInLookup.removeLookupListener(this);
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    
}
