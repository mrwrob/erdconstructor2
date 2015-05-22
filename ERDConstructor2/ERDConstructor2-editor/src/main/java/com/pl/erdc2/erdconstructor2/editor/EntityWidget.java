package com.pl.erdc2.erdconstructor2.editor;

import com.pl.erdc2.erdconstructor2.api.EntityNode;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.Widget;


public class EntityWidget extends Widget{
    private String title;
    private final EntityNode bean;
    
    public EntityWidget(GraphSceneImpl scene, EntityNode _bean){
        super(scene);
        bean=_bean;
        title="";
        setCheckClipping(true);   
        
        Font f = new Font("Arial", Font.BOLD, FONT_SIZE);
        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.TRACKING, 0.05);
        ARIAL_BOLD = f.deriveFont(attributes);
        f = new Font("Calibri", Font.PLAIN, FONT_SIZE);
        CALIBRI =  f.deriveFont(attributes);
    }
    
    private static final Border RESIZE_BORDER = BorderFactory.createResizeBorder(8,Color.BLACK,true);
    private static final Border DEFAULT_BORDER = BorderFactory.createEmptyBorder(8);
    private final static int FONT_SIZE=14;
    private final static int ENTITY_TITLE_PADDING=10;
    private final static int ENTITY_TITLE_SIZE=2*ENTITY_TITLE_PADDING+FONT_SIZE-4;
    private final static int BORDER_ROUND=10;
    private final static Color ENTITY_SELECTED_BLUE=new Color(103, 145, 215);
    private final static Color ENTITY_BLUE=new Color(83, 117, 189);
    private final static Color ENTITY_BACKGROUND=new Color(236, 239, 248);
    private final Font ARIAL_BOLD;
    private final Font CALIBRI;
    private final static Stroke BASIC_STROKE=new BasicStroke(1);
    private final static Stroke STROKE_2PX=new BasicStroke(2);

 
    
    @Override
    protected void paintWidget(){
        final Graphics2D g2 = getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       
        final Rectangle bounds = getClientArea();
        String s = title.length()>0 ? title : "Encja";
        
        g2.setColor(ENTITY_BACKGROUND);
        g2.fillRoundRect(bounds.x, bounds.y, bounds.width-1, bounds.height-1,BORDER_ROUND,BORDER_ROUND);
        
        if(isSelected())
            g2.setColor(ENTITY_SELECTED_BLUE); 
        else
            g2.setColor(ENTITY_BLUE);
        g2.fillRoundRect(bounds.x, bounds.y, bounds.width-1, ENTITY_TITLE_SIZE, BORDER_ROUND, BORDER_ROUND);
        g2.fillRect(bounds.x, bounds.y+10, bounds.width-1, ENTITY_TITLE_SIZE-10);
        
        g2.setStroke(STROKE_2PX);
        g2.drawRoundRect(bounds.x, bounds.y, bounds.width-1, bounds.height-1, BORDER_ROUND, BORDER_ROUND);
        
        g2.setColor(Color.WHITE);
        g2.setStroke(BASIC_STROKE);
        g2.drawLine(bounds.x+2, bounds.y+ENTITY_TITLE_SIZE, bounds.x+bounds.width-3, bounds.y+ENTITY_TITLE_SIZE);
        
        g2.setFont(ARIAL_BOLD);
        g2.setColor(Color.WHITE); 
        g2.setStroke(BASIC_STROKE);
        double textWidth = g2.getFont().getStringBounds(s, g2.getFontRenderContext()).getWidth();
        double textPosition = (bounds.width)/2-textWidth/2;
        g2.drawString(s, Math.round(textPosition)+bounds.x, bounds.y+FONT_SIZE+5);
     
    }
    
    public boolean isSelected(){
        return this.getScene().getFocusedWidget().equals(this);
    }
    
    @Override
    public void notifyStateChanged(ObjectState previousState, ObjectState newState) {
        super.notifyStateChanged(previousState, newState);
        this.setBorder(
                    newState.isSelected() ? 
                    (newState.isHovered() ? RESIZE_BORDER : DEFAULT_BORDER) : 
                    (newState.isHovered() ? RESIZE_BORDER : DEFAULT_BORDER));
     }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EntityNode getBean() {
        return bean;
    }
}
