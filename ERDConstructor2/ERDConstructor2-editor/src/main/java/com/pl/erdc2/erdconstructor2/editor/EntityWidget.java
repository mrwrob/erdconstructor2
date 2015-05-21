/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pl.erdc2.erdconstructor2.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Piotrek
 */
public class EntityWidget extends Widget{
    boolean isSelected=false;
    private String title;
    
    public EntityWidget(Scene scene) {
        super(scene);
        title="";
        setCheckClipping(true);
        this.setMinimumSize(new Dimension(100,100));
        getActions().addAction(ActionFactory.createResizeAction());
        getActions().addAction(ActionFactory.createMoveAction());
        getActions().addAction(scene.createWidgetHoverAction());
        
        Font f = new Font("Arial", Font.BOLD, FONT_SIZE);
        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.TRACKING, 0.05);
        TAHOMA_BOLD = f.deriveFont(attributes);
        f = new Font("Calibri", Font.PLAIN, FONT_SIZE);
        CALIBRI =  f.deriveFont(attributes);
    }
    
    private static final Border RESIZE_BORDER = BorderFactory.createResizeBorder(8,Color.BLACK,true);
    private static final Border DEFAULT_BORDER = BorderFactory.createEmptyBorder(8);
    final static int FONT_SIZE=14;
    final static int ENTITY_TITLE_PADDING=10;
    final static int ENTITY_TITLE_SIZE=2*ENTITY_TITLE_PADDING+FONT_SIZE-4;
    final static int BORDER_ROUND=9;
    final static Color ENTITY_BLUE=new Color(83, 117, 189);
    final static Color ENTITY_BACKGROUND=new Color(236, 239, 248);
    final Font TAHOMA_BOLD;
    final Font CALIBRI;
    final static Stroke BASIC_STROKE=new BasicStroke(1);
    final static Stroke STROKE_2PX=new BasicStroke(2);
    
    @Override
    protected void paintWidget(){
        final Graphics2D g2 = getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       
        final Rectangle bounds = getClientArea();
        String s = title.length()>0 ? title : "Encja";
        
        g2.setColor(ENTITY_BACKGROUND);
        g2.fillRoundRect(bounds.x, bounds.y, bounds.width-1, bounds.height-1,BORDER_ROUND,BORDER_ROUND);
        
        g2.setColor(ENTITY_BLUE);
        g2.fillRoundRect(bounds.x, bounds.y, bounds.width-1, ENTITY_TITLE_SIZE, BORDER_ROUND, BORDER_ROUND);
        g2.fillRect(bounds.x, bounds.y+10, bounds.width-1, ENTITY_TITLE_SIZE-10);
        
        g2.setStroke(STROKE_2PX);
        g2.drawRoundRect(bounds.x, bounds.y, bounds.width-1, bounds.height-1, BORDER_ROUND, BORDER_ROUND);
        
        g2.setColor(Color.WHITE);
        g2.setStroke(BASIC_STROKE);
        g2.drawLine(bounds.x+2, bounds.y+ENTITY_TITLE_SIZE-1, bounds.x+bounds.width-3, bounds.y+ENTITY_TITLE_SIZE-1);
        
        g2.setFont(TAHOMA_BOLD);
        g2.setColor(Color.WHITE); 
        g2.setStroke(BASIC_STROKE);
        double textWidth = g2.getFont().getStringBounds(s, g2.getFontRenderContext()).getWidth();
        double textPosition = (bounds.width)/2-textWidth/2;
        g2.drawString(s, Math.round(textPosition)+bounds.x, bounds.y+FONT_SIZE+5);
     
    }
    
    @Override
    public void notifyStateChanged(ObjectState previousState, ObjectState newState) {
        super.notifyStateChanged(previousState, newState);
        this.setBorder(
                    newState.isSelected() ? 
                    (newState.isHovered() ? RESIZE_BORDER : DEFAULT_BORDER) : 
                    (newState.isHovered() ? RESIZE_BORDER : DEFAULT_BORDER));
        if(newState.isSelected()){
            this.isSelected=true;
        }else{
            this.isSelected=false;
        }
     }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
