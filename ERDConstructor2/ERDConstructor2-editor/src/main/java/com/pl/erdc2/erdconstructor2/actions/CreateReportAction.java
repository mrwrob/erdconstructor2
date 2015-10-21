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
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;
import org.openide.util.NbPreferences;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import org.apache.poi.xwpf.usermodel.Document;
import org.openide.nodes.Node;
import org.openide.windows.WindowManager;
import com.pl.erdc2.globalsettings.UserInfoPanel;
import helper.CustomXWPFDocument;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
/**
 *
 * @author Piotr
 */
@ActionID(category = "File", id = "com.pl.erdc2.CreateReportAction")
@ActionRegistration(displayName = "#CTL_CreateReportAction")
@ActionReference(path = "Menu/File", position = 14)
@NbBundle.Messages({"CTL_CreateReportAction=Create report",
        "NameAndSurname=Imię i nazwisko",
        "Confirm_Replace_File=Czy na pewno chcesz nadpisać plik?",
        "Confirm=Potwierdzenie",
        "Yes_Option=Tak",
        "No_Option=Nie"})

public class CreateReportAction implements ActionListener{
    
    private static final Logger logger = Logger.getLogger(CreateReportAction.class);
    
    public CreateReportAction(){
        BasicConfigurator.configure();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            CustomXWPFDocument doc = new CustomXWPFDocument();

            XWPFParagraph p1 = doc.createParagraph();
            p1.setAlignment(ParagraphAlignment.LEFT);
            p1.setVerticalAlignment(TextAlignment.TOP);
 
            UserSettingsDto userSettings = getUserSettings();
            
            XWPFRun r1 = p1.createRun();
            r1.setBold(true);
            r1.setFontSize(14);
            String name = userSettings.getFirstName() + " " + userSettings.getLastName();
            r1.setText(name.trim().equals("") ? Bundle.NameAndSurname() : name);
            r1.setFontFamily("Calibri");
            r1.addCarriageReturn();
            r1.setBold(true);
            r1.setText("Indeks: " + userSettings.getIndexNo());
            r1.addBreak();
            
            XWPFParagraph p2 = doc.createParagraph();
            p2.setAlignment(ParagraphAlignment.LEFT);

            XWPFRun r2 = p2.createRun();
            r2.setFontSize(17);
            r2.setBold(true);
            r2.setUnderline(UnderlinePatterns.WORDS);
            r2.setFontFamily("Cambria");
            r2.setText("Temat projektu");
            r2.addCarriageReturn();
            r2.addCarriageReturn();
            
            XWPFParagraph p3 = doc.createParagraph();
            p3.setAlignment(ParagraphAlignment.LEFT);
            
            XWPFRun r4 = p3.createRun();
            r4.setFontSize(17);
            r4.setBold(true);
            r4.setUnderline(UnderlinePatterns.WORDS);
            r4.setFontFamily("Cambria");
            r4.setText("Szczegółowy opis projektu");
            r4.addCarriageReturn();
            r4.addCarriageReturn();

            XWPFRun r5 = p3.createRun();
            r5.setFontSize(12);
            r5.setText("Szczegółowy opis twojego projektu ...");
            r5.addCarriageReturn();
            r5.setFontFamily("Calibri");

            XWPFParagraph p4 = doc.createParagraph();
            p4.setPageBreak(true);

            XWPFRun r6 = p4.createRun();
            r6.setFontSize(20);
            r6.setBold(true);
            r6.setUnderline(UnderlinePatterns.WORDS);
            r6.setFontFamily("Cambria");
            r6.setText("Diagram ERD");      
            r6.addBreak();

            EditorTopComponent etc;
            etc = (EditorTopComponent) WindowManager.getDefault().findTopComponent("editorTopComponent");
            GraphSceneImpl scene = etc.getScene();
            JComponent view = scene.getView();
            BufferedImage bi = new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D graphics = bi.createGraphics();
            scene.paint(graphics);
            graphics.dispose();

            String filename = "d.png";
            File f = new File(filename);
            ImageIO.write(bi, "png", f);
    
            String blipId = p4.getDocument().addPictureData(new FileInputStream(new File("d.png")), Document.PICTURE_TYPE_PNG);
            doc.createPicture(blipId, doc.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), 650, view.getHeight()*650/view.getWidth());
            
            XWPFParagraph p5 = doc.createParagraph();
            p5.setPageBreak(true);
            
            XWPFRun r8 = p5.createRun();
            r8.setFontSize(20);
            r8.setBold(true);
            r8.setUnderline(UnderlinePatterns.WORDS);
            r8.setText("Opis zbioru encji");
            r8.setFontFamily("Cambria");
            r8.addBreak();

            for(Node n : EntityExplorerManagerProvider.getEntityNodeRoot().getChildren().getNodes()){
                Entity ent = n.getLookup().lookup(Entity.class);  
                XWPFTable table = doc.createTable();
                CTTbl tablec        = table.getCTTbl();
                CTTblPr pr         = tablec.getTblPr();
                CTTblWidth  tblW = pr.getTblW();
                tblW.setW(BigInteger.valueOf(5000));
                tblW.setType(STTblWidth.PCT);
                pr.setTblW(tblW);
                tablec.setTblPr(pr);

                XWPFTableRow tableRow1 = table.getRow(0);
                XWPFParagraph p = tableRow1.getCell(0).getParagraphs().get(0);
                p.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun r = p.createRun();
                r.setFontSize(14);
                r.setBold(true);
                r.setText(ent.getName());
                r.setFontFamily("Calibri");
                tableRow1.getCell(0).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);


                XWPFTableRow tableRow2 = table.createRow();
                p = tableRow2.getCell(0).getParagraphs().get(0);
                p.setAlignment(ParagraphAlignment.CENTER);
                r = p.createRun();
                r.setFontFamily("Calibri");
                r.setText((ent.getDescription()==null ? "Opis encji" : ent.getDescription()));


                XWPFTableRow tableRow3 = table.createRow();
                p = tableRow3.getCell(0).getParagraphs().get(0);
                p.setAlignment(ParagraphAlignment.CENTER);
                r = p.createRun();
                r.setBold(true);
                r.setFontFamily("Calibri");
                r.setText("Nazwa");

                p = tableRow3.addNewTableCell().getParagraphs().get(0);
                p.setAlignment(ParagraphAlignment.CENTER);
                r = p.createRun();
                r.setBold(true);
                r.setFontFamily("Calibri");
                r.setText("Klucz główny");

                p = tableRow3.addNewTableCell().getParagraphs().get(0);
                p.setAlignment(ParagraphAlignment.CENTER);
                r = p.createRun();
                r.setBold(true);
                r.setFontFamily("Calibri");
                r.setText("Typ/dziedzina");

                p = tableRow3.addNewTableCell().getParagraphs().get(0);
                p.setAlignment(ParagraphAlignment.CENTER);
                r = p.createRun();
                r.setBold(true);
                r.setFontFamily("Calibri");
                r.setText("Opis");

                spanCellsAcrossRow(table, 0, 0, 4);
                spanCellsAcrossRow(table, 1, 0, 4);

                for(Node columnNode : n.getChildren().getNodes()){
                    Column c = columnNode.getLookup().lookup(Column.class);

                    XWPFTableRow tableRowA = table.createRow();
                    p = tableRowA.getCell(0).getParagraphs().get(0);
                    p.setAlignment(ParagraphAlignment.CENTER);
                    r = p.createRun();
                    r.setFontFamily("Calibri");
                    r.setText(c.getName()==null ? "" : c.getName());

                    p = tableRowA.addNewTableCell().getParagraphs().get(0);
                    p.setAlignment(ParagraphAlignment.CENTER);
                    r = p.createRun();
                    r.setFontFamily("Calibri");
                    if(c.isPrimary())
                        r.setBold(true);
                    r.setText(c.isPrimary() ? "Tak" : "Nie");

                    p = tableRowA.addNewTableCell().getParagraphs().get(0);
                    p.setAlignment(ParagraphAlignment.CENTER);
                    r = p.createRun();
                    r.setFontFamily("Calibri");
                    r.setText(c.getType()==null ? "" : c.getType());

                    p = tableRowA.addNewTableCell().getParagraphs().get(0);
                    p.setAlignment(ParagraphAlignment.CENTER);
                    r = p.createRun();
                    r.setFontFamily("Calibri");
                    r.setText(c.getDescription());

                }

                p = doc.createParagraph();
                p.setPageBreak(false);
                p.createRun().addBreak();
            } 

            XWPFParagraph p8 = doc.createParagraph();
            XWPFRun r12 = p8.createRun();
            r12.setFontSize(20);
            r12.setBold(true);
            r12.setUnderline(UnderlinePatterns.WORDS);
            r12.setText("Opis związków");
            r12.setFontFamily("Cambria");
            r12.addCarriageReturn();
            
            XWPFTable table = doc.createTable();
            CTTbl tablec        = table.getCTTbl();
            CTTblPr pr         = tablec.getTblPr();
            CTTblWidth  tblW = pr.getTblW();
            tblW.setW(BigInteger.valueOf(5000));
            tblW.setType(STTblWidth.PCT);
            pr.setTblW(tblW);
            tablec.setTblPr(pr);

            XWPFTableRow tableRow = table.createRow();
            XWPFParagraph p;
            XWPFRun r;
            p = tableRow.getCell(0).getParagraphs().get(0);
            p.setAlignment(ParagraphAlignment.CENTER);
            r = p.createRun();
            r.setBold(true);
            r.setFontFamily("Calibri");
            r.setText("Nazwa związku");

            p = tableRow.addNewTableCell().getParagraphs().get(0);
            p.setAlignment(ParagraphAlignment.CENTER);
            r = p.createRun();
            r.setBold(true);
            r.setFontFamily("Calibri");
            r.setText("Zbiór encji 1");

            p = tableRow.addNewTableCell().getParagraphs().get(0);
            p.setAlignment(ParagraphAlignment.CENTER);
            r = p.createRun();
            r.setBold(true);
            r.setFontFamily("Calibri");
            r.setText("Zbiór encji 2");

            p = tableRow.addNewTableCell().getParagraphs().get(0);
            p.setAlignment(ParagraphAlignment.CENTER);
            r = p.createRun();
            r.setBold(true);
            r.setFontFamily("Calibri");
            r.setText("Liczność związku");
            
            p = tableRow.addNewTableCell().getParagraphs().get(0);
            p.setAlignment(ParagraphAlignment.CENTER);
            r = p.createRun();
            r.setBold(true);
            r.setFontFamily("Calibri");
            r.setText("Opis");
            
            for(Node n : EntityExplorerManagerProvider.getRelatioshipNodeRoot().getChildren().getNodes()){
                    Relationship rel = n.getLookup().lookup(Relationship.class);
                    
                    XWPFTableRow tableRowA = table.createRow();
                    p = tableRowA.getCell(0).getParagraphs().get(0);
                    p.setAlignment(ParagraphAlignment.CENTER);
                    r = p.createRun();
                    r.setFontFamily("Calibri");
                    r.setText(rel.getName());

                    Entity e1 = EntityExplorerManagerProvider.getEntityById(rel.getSourceEntityId());
                    p = tableRowA.addNewTableCell().getParagraphs().get(0);
                    p.setAlignment(ParagraphAlignment.CENTER);
                    r = p.createRun();
                    r.setFontFamily("Calibri");
                    r.setText(e1.getName());

                    Entity e2 = EntityExplorerManagerProvider.getEntityById(rel.getDestinationEntityId());
                    p = tableRowA.addNewTableCell().getParagraphs().get(0);
                    p.setAlignment(ParagraphAlignment.CENTER);
                    r = p.createRun();
                    r.setFontFamily("Calibri");
                    r.setText(e2.getName());

                    p = tableRowA.addNewTableCell().getParagraphs().get(0);
                    p.setAlignment(ParagraphAlignment.CENTER);
                    r = p.createRun();
                    r.setFontFamily("Calibri");
                    r.setText(rel.getSourceType()+" : "+rel.getDestinationType());
                    
                    p = tableRowA.addNewTableCell().getParagraphs().get(0);
                    p.setAlignment(ParagraphAlignment.CENTER);
                    r = p.createRun();
                    r.setFontFamily("Calibri");
                    r.setText(rel.getDescription()==null ? "" : rel.getDescription());
            }
            
            p = doc.createParagraph();
            p.setPageBreak(false);
            p.createRun().addBreak();
            
            XWPFParagraph p10 = doc.createParagraph();
            XWPFRun r14 = p10.createRun();
            r14.setFontSize(20);
            r14.setBold(true);
            r14.setUnderline(UnderlinePatterns.WORDS);
            r14.setFontFamily("Cambria");
            r14.setText("Schemat relacyjnej bazy danych");
            
                    
            String dictionaryPath = dictionaryPath();
            if(dictionaryPath != null){
                FileOutputStream out = new FileOutputStream(dictionaryPath);            
                doc.write(out);
                out.close();
            }                        
            
        }catch(IOException | InvalidFormatException ex){
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
                
        while(true){            
            int chooserResponse = chooser.showSaveDialog(chooser);            
            if(chooserResponse == JFileChooser.CANCEL_OPTION){
                return null;
            }
            
            String fullPath = chooser.getSelectedFile().getAbsolutePath();            
            if(!fullPath.endsWith(".docx")){
                return fullPath + ".docx";
            }
   
            File testFile = new File(fullPath);
            if(testFile.exists()){
                int response = JOptionPane.showOptionDialog(null,
                        (Object) Bundle.Confirm_Replace_File(), 
                        Bundle.Confirm(), JOptionPane.YES_NO_OPTION, 
                        JOptionPane.INFORMATION_MESSAGE, null, 
                        new String[]{Bundle.Yes_Option(), Bundle.No_Option()}, "default");
                if (response == JOptionPane.YES_OPTION) {
                    System.out.println("aa");
                    return fullPath;
                } 
            }
        }
    }
    
    private void spanCellsAcrossRow(XWPFTable table, int rowNum, int colNum, int span) {
        XWPFTableCell  cell = table.getRow(rowNum).getCell(colNum);
        CTTcPr tcPr = cell.getCTTc().getTcPr();
        if (null == tcPr)    
            tcPr = cell.getCTTc().addNewTcPr();
        tcPr.addNewGridSpan();
        cell.getCTTc().getTcPr().getGridSpan().setVal(BigInteger.valueOf((long)span));
    }
}
