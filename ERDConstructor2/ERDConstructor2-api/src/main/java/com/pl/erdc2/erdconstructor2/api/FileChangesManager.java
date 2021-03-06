package com.pl.erdc2.erdconstructor2.api;

import java.io.File;
import org.openide.util.NbBundle;
import org.openide.windows.WindowManager;
import org.openide.windows.TopComponent;
/**
 *
 * @author Piotrek
 */
@NbBundle.Messages({
    "CTL_editorTopComponent=Diagram editor"
})
public class FileChangesManager{
    private static String filename="";
    private static boolean fileWasChanged=false;
    private static TopComponent etc;
    private static File file;
    private static boolean setupMode=false;
        
    private FileChangesManager(){
        etc = WindowManager.getDefault().findTopComponent("editorTopComponent");
    }

    public static void change(){
        if(setupMode)
            return;
        if(!fileWasChanged && !filename.equals(""))
            getEtc().setHtmlDisplayName("<html><b>"+filename+"</b></html>");
        fileWasChanged=true;
    }
    
    public static void newFile(){
        fileWasChanged=false;
        filename="";
        getEtc().setHtmlDisplayName(Bundle.CTL_editorTopComponent());
    }
    
    public static void openFile(String filename, File f){
        fileWasChanged=false;
        FileChangesManager.filename = filename;
        file=f;
        getEtc().setHtmlDisplayName(filename);   
    }
    
    public static void saveFile(String filename, File f){
        fileWasChanged=false;
        FileChangesManager.filename = filename;
        file=f;
        getEtc().setHtmlDisplayName(filename);
    }

    public static void setSetupMode(boolean setupMode) {
        FileChangesManager.setupMode = setupMode;
    }

    public static String getFilename() {
        return filename;
    }

    public static boolean isFileWasChanged() {
        return fileWasChanged;
    }

    public static File getFile() {
        return file;
    }
    
    private static TopComponent getEtc(){
        if(etc==null)
            etc = WindowManager.getDefault().findTopComponent("editorTopComponent");
        return etc;
    }
}
