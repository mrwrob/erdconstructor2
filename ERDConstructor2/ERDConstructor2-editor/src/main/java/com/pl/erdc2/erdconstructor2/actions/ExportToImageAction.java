/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pl.erdc2.erdconstructor2.actions;

import com.pl.erdc2.erdconstructor2.editor.EditorTopComponent;
import com.pl.erdc2.erdconstructor2.editor.GraphSceneImpl;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.windows.WindowManager;

/**
 *
 * @author Piotr
 */

@ActionID(category = "File", id = "com.pl.erdc2.ExportToImageAction")
@ActionRegistration(displayName = "#CTL_ExportToImageAction")
@ActionReference(path = "Menu/File", position = 12)
@NbBundle.Messages("CTL_ExportToImageAction=Export to image")
public class ExportToImageAction implements ActionListener{
    
    private static final Logger logger = Logger.getLogger(ExportToImageAction.class);
    
    public ExportToImageAction(){
        BasicConfigurator.configure();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        EditorTopComponent etc;
        etc = (EditorTopComponent) WindowManager.getDefault().findTopComponent("editorTopComponent");
        GraphSceneImpl scene = etc.getScene();
        JComponent view = scene.getView();
        BufferedImage bi = new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D graphics = bi.createGraphics();
        scene.paint(graphics);
        graphics.dispose();

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Export Scene As ...");
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                }
                return file.getName().toLowerCase().endsWith(".png");
            }

            @Override
            public String getDescription() {
                return "Portable Network Graphics (.png)";
            }
        });
        if (chooser.showSaveDialog(view) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = chooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".png"))
        {
            file = new File(file.getParentFile(), file.getName() + ".png");
        }
        if (file.exists()) {
            DialogDescriptor descriptor = new DialogDescriptor(
                    "File (" + file.getAbsolutePath() + ") already exists. Do you want to overwrite it?",
                    "File Exists", true, DialogDescriptor.YES_NO_OPTION, DialogDescriptor.NO_OPTION, null);
            DialogDisplayer.getDefault().createDialog(descriptor).setVisible(true);
            if (descriptor.getValue() != DialogDescriptor.YES_OPTION) {
                return;
            }
        }

        try {
            ImageIO.write(bi, "png", file);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex); 
            logger.error(ex);
        }
    }    
}
