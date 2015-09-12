package com.pl.erdc2.erdconstructor2.api;

import java.awt.Image;
import java.beans.IntrospectionException;
import java.util.Observable;
import java.util.Observer;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.Lookups;

public class ColumnNode  extends BeanNode<Column> implements Observer{
    public ColumnNode(Column bean) throws IntrospectionException {
        super(bean, Children.LEAF, Lookups.singleton(bean));
        if(bean.getName()==null){
            bean.setName("Attribute");
            bean.setDescription("");
        }
        bean.addObserver(this);
        this.setDisplayName(bean.getName());
        
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Column){
            Column col = (Column)o;
            String property = (String)arg;
            if(property.equals("name")){ 
                String old = this.getDisplayName();
                this.setDisplayName(col.getName());
                this.fireDisplayNameChange(old, col.getName());
            }
            else if(property.equals("primary")){
                this.fireIconChange();
            }
        }
    }
    
    @Override
    public Image getIcon (int type) {
        Column c = this.getLookup().lookup(Column.class);
        if(c!=null && c.isPrimary()){
            return ImageUtilities.loadImage("images/keyColumnIconS.png");
        }
        return ImageUtilities.loadImage("images/columnIconS.png");
    }
    
    @Override
    public Image getOpenedIcon(int i) {
        return getIcon (i);
    }
}
