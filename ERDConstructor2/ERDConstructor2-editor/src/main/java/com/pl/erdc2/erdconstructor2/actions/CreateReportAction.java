package com.pl.erdc2.erdconstructor2.actions;

import com.pl.erdc2.erdconstructor2.api.Column;
import com.pl.erdc2.erdconstructor2.api.Entity;
import com.pl.erdc2.erdconstructor2.api.EntityExplorerManagerProvider;
import com.pl.erdc2.erdconstructor2.api.Relationship;
import com.pl.erdc2.erdconstructor2.editor.EditorTopComponent;
import com.pl.erdc2.erdconstructor2.editor.GraphSceneImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.BreakClear;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.LineSpacingRule;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;
import org.openide.util.NbPreferences;
import com.pl.erdc2.globalsettings.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Document;
import org.openide.nodes.Node;
import org.openide.windows.WindowManager;
/**
 *
 * @author Piotr
 */
@ActionID(category = "File", id = "com.pl.erdc2.CreateReportAction")
@ActionRegistration(displayName = "#CTL_CreateReportAction")
@ActionReference(path = "Menu/File", position = 14)
@NbBundle.Messages("CTL_CreateReportAction=Create report")
public class CreateReportAction implements ActionListener{
    
    private static final Logger logger = Logger.getLogger(CreateReportAction.class);
    
    public CreateReportAction(){
        BasicConfigurator.configure();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            XWPFDocument doc = new XWPFDocument();

            XWPFParagraph p1 = doc.createParagraph();
            p1.setAlignment(ParagraphAlignment.LEFT);
            p1.setVerticalAlignment(TextAlignment.TOP);
 
            UserSettingsDto userSettings = getUserSettings();
            
            XWPFRun r1 = p1.createRun();
            r1.setBold(true);
            r1.setFontSize(16);
            r1.setText(userSettings.getFirstName() + " " + userSettings.getLastName());
            r1.setFontFamily("Calibri");
            r1.addCarriageReturn();
            r1.setBold(true);
            r1.setText("Indeks: " + userSettings.getIndexNo());

            XWPFParagraph p2 = doc.createParagraph();
            p2.setAlignment(ParagraphAlignment.LEFT);

            XWPFRun r2 = p2.createRun();
            r2.setFontSize(20);
            r2.setBold(true);
            r2.setUnderline(UnderlinePatterns.WORDS);
            r2.setText("Temat projektu:");

            XWPFRun r3 = p2.createRun();
            r3.setFontSize(12);
            r3.setText("Temat Twojego projektu ...");
            r2.addCarriageReturn();
            
            XWPFParagraph p3 = doc.createParagraph();
            p3.setAlignment(ParagraphAlignment.LEFT);
            
            XWPFRun r4 = p3.createRun();
            r4.setFontSize(20);
            r4.setBold(true);
            r4.setUnderline(UnderlinePatterns.WORDS);
            r4.setText("Szczegółowy opis projektu:");
            r4.addCarriageReturn();

            XWPFRun r5 = p3.createRun();
            r5.setFontSize(12);
            r5.setText("Szczegółowy opis Twojego projektu ...");
            r2.addCarriageReturn();

            XWPFParagraph p4 = doc.createParagraph();
            p4.setPageBreak(true);

            XWPFRun r6 = p4.createRun();
            r6.setFontSize(20);
            r6.setBold(true);
            r6.setUnderline(UnderlinePatterns.WORDS);
            r6.setText("Diagram ERD:");
            r6.addCarriageReturn();            
            // TODO
            // Nie mogę zapisać/ dodać obrazka
//            EditorTopComponent etc;
//            etc = (EditorTopComponent) WindowManager.getDefault().findTopComponent("editorTopComponent");
//            GraphSceneImpl scene = etc.getScene();
//            JComponent view = scene.getView();
//            BufferedImage bi = new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(bi, "png", baos);
//            InputStream is = new ByteArrayInputStream(baos.toByteArray());
//            r7.addPicture(is, Document.PICTURE_TYPE_PNG, "diagram", view.getWidth(), view.getHeight());
           
            XWPFParagraph p5 = doc.createParagraph();
            p5.setPageBreak(false);
            
            XWPFRun r8 = p5.createRun();
            r8.setFontSize(20);
            r8.setBold(true);
            r8.setUnderline(UnderlinePatterns.WORDS);
            r8.setText("Opis zbioru encji:");
            r8.addCarriageReturn();

            for(Node n : EntityExplorerManagerProvider.getEntityNodeRoot().getChildren().getNodes()){
                    Entity ent = n.getLookup().lookup(Entity.class);  
                    
                    XWPFParagraph p6 = doc.createParagraph();
                    p6.setPageBreak(false);
                    p6.setBorderBottom(Borders.DOUBLE);
                    p6.setBorderTop(Borders.DOUBLE);
                    p6.setBorderRight(Borders.DOUBLE);
                    p6.setBorderLeft(Borders.DOUBLE);
                    p6.setBorderBetween(Borders.SINGLE);
        
                    XWPFRun r9 = p6.createRun();
                    r9.setFontSize(12);
                    r9.setBold(true);
                    r9.setText("Zbiór encji: " + ent.getName());
                    r9.addCarriageReturn();
                    
                    XWPFRun r10 = p6.createRun();
                    r10.setFontSize(12);
                    r10.setText("Opis: " + ent.getDescription());
                    r10.addCarriageReturn();
                    
                    // TODO 
                    // Nie działają kolumny, nei wiem czmu kolekcje są puste
                    for(Column c : ent.getColumns()){
                        XWPFParagraph p7 = doc.createParagraph();
                        p7.setPageBreak(false);
                        XWPFRun r11 = p7.createRun();
                        r11.setFontSize(12);
                        r11.setText("Nazwa kolumny: " + c.getName());
                        r11.addCarriageReturn();
                        r11.setText("Typ kolumny: " + c.getType());
                        r11.addCarriageReturn();
                        r11.setText("Opis kolumny: " + c.getDescription());
                        r11.addCarriageReturn();
                    }
            } 

            XWPFParagraph p8 = doc.createParagraph();
            XWPFRun r12 = p8.createRun();
            r12.setFontSize(20);
            r12.setBold(true);
            r12.setUnderline(UnderlinePatterns.WORDS);
            r12.setText("Opis związków encji:");
            r12.addCarriageReturn();
            
            for(Node n : EntityExplorerManagerProvider.getRelatioshipNodeRoot().getChildren().getNodes()){
                    Relationship rel = n.getLookup().lookup(Relationship.class);
                    
                    XWPFParagraph p9 = doc.createParagraph();
                    XWPFRun r13 = p9.createRun();
                    r13.setFontSize(12);
                    r13.setBold(false);
                    r13.setText("Nazwa związku: " + rel.getName());
                    r13.addCarriageReturn();
                    r13.setText("Opis związku: " + rel.getDescription());
                    r13.addCarriageReturn();
                    // TODO 
                    // 1. brak typu relacji 1 : n itp.
                    // 2. brak nazw encji które łączy
            }
                    
            String dictionaryPath = dictionaryPath();
            FileOutputStream out = new FileOutputStream(dictionaryPath);
            doc.write(out);
            out.close();
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
            logger.error(ex);
        }
    }

    private UserSettingsDto getUserSettings() {
        UserSettingsDto userSettingsDto = new UserSettingsDto();
        Preferences pref = NbPreferences.forModule(UserInfoPanel.class);
        userSettingsDto.setFirstName(pref.get("firstNamePreference", ""));
        userSettingsDto.setLastName(pref.get("lastNamePreference", ""));
        userSettingsDto.setIndexNo(pref.get("indexNoPreference", ""));
        return userSettingsDto;
    }
    
    public static String dictionaryPath(){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setSelectedFile(new File("report.docx"));
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                }
                return file.getName().toLowerCase().endsWith(".docx");
            }

            @Override
            public String getDescription() {
                return "Word Document (.docx)";
            }
        });
        
        chooser.showSaveDialog(chooser);
        String fullPath = chooser.getSelectedFile().getAbsolutePath();
        if(!fullPath.endsWith(".docx")){
            return fullPath + ".docx";
        }
        return fullPath;
    }
}
