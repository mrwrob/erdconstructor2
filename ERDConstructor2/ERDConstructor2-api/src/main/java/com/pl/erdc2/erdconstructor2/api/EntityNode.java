/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pl.erdc2.erdconstructor2.api;

import java.awt.Image;
import java.beans.IntrospectionException;
import org.openide.nodes.BeanNode;
import org.openide.util.ImageUtilities;

/**
 *
 * @author Piotrek
 */
public class EntityNode extends BeanNode<Entity> {

    public EntityNode(Entity bean) throws IntrospectionException {
        super(bean);
        bean.setName("Entity");
        setDisplayName ("Entity");
    }
    
    @Override
    public Image getIcon (int type) {    
        return ImageUtilities.loadImage("images/entityIcon.png");
    }
    @Override
    public Image getOpenedIcon(int i) {
        return getIcon (i);
    }
}
