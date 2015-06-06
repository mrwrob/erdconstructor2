package com.pl.erdc2.erdconstructor2.treeview;

import com.pl.erdc2.erdconstructor2.api.EntityExplorerManagerProvider;
import java.awt.BorderLayout;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.pl.erdc2.erdconstructor2.treeview//TreeViewer//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "TreeViewerTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window", id = "com.pl.erdc2.erdconstructor2.treeview.TreeViewerTopComponent")
@ActionReference(path = "Menu/Window" , position = 0)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_TreeViewerAction",
        preferredID = "TreeViewerTopComponent"
)
@Messages({
    "CTL_TreeViewerAction=TreeViewer",
    "CTL_TreeViewerTopComponent=TreeViewer Window",
    "HINT_TreeViewerTopComponent=This is a TreeViewer window"
})
public final class TreeViewerTopComponent extends TopComponent implements ExplorerManager.Provider{
    private final ExplorerManager em;

    public TreeViewerTopComponent() {
        em = EntityExplorerManagerProvider.getInstance().getExplorerManager();

        initComponents();
        setName(Bundle.CTL_TreeViewerTopComponent());
        setToolTipText(Bundle.HINT_TreeViewerTopComponent());
        
        setLayout(new BorderLayout());
        BeanTreeView entityViewer = new BeanTreeView();      
        add(entityViewer, BorderLayout.CENTER);
        
        associateLookup(ExplorerUtils.createLookup(em, getActionMap()));
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
    @Override
    public void componentOpened() {
        
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return em;
    }
}
