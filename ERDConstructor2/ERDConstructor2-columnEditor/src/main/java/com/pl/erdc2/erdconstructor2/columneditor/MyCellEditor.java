/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pl.erdc2.erdconstructor2.columneditor;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;

/**
 *
 * @author Filip
 */
public class MyCellEditor extends DefaultCellEditor
{
    public MyCellEditor(final JTextField textField ) {
        super( textField );
        textField.addFocusListener( new FocusAdapter()
        {
            @Override
            public void focusGained( final FocusEvent e )
            {
                textField.selectAll();
            }
        } );
    }  
}