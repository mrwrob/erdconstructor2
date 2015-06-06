package com.pl.erdc2.erdconstructor2.api;

import java.beans.IntrospectionException;
import java.util.Observable;
import java.util.Observer;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;

public class ColumnNode  extends BeanNode<Column>  implements Observer{
    public ColumnNode(Column bean) throws IntrospectionException {
        super(bean, Children.LEAF, Lookups.singleton(bean));
        bean.setName("");
        bean.setDescription("");
        bean.addObserver(this);
        this.setDisplayName(bean.getName());
        
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Column){
            Column col = (Column)o;
            String old = this.getDisplayName();
            this.setDisplayName(col.getName());
            this.fireDisplayNameChange(old, col.getName());
        }
    }
    
}
