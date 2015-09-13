package com.pl.erdc2.erdconstructor2.columneditor;

import com.pl.erdc2.erdconstructor2.api.Column;
import com.pl.erdc2.erdconstructor2.api.ColumnNode;
import com.pl.erdc2.erdconstructor2.api.EntityNode;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author Piotrek
 */
public class EntityPanel extends JPanel{
    JTable table;
    JLabel entityLablel;
    JLabel nameLablel;
    JLabel nameField;
    JScrollPane tablePanel;
    JButton addButton;
    EntityNode selectedNode = null;
    private static final Logger logger = Logger.getLogger(EntityPanel.class);

    public EntityPanel() {
        BasicConfigurator.configure();
        setName(Bundle.CTL_ColumnEditorTopComponent());
        setToolTipText(Bundle.HINT_ColumnEditorTopComponent());
                
        setLayout(new GridBagLayout());

        table = new JTable();
        table.setModel(new ColumnTableItemModel());
        JComboBox comboBox = new JComboBox();
        for(String s : Column.DATA_TYPES)
            comboBox.addItem(s);
        comboBox.setEditable(true);
        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(comboBox));
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(30);
        table.getColumnModel().getColumn(3).setPreferredWidth(300);
        table.setRowHeight(22);
        tablePanel = new JScrollPane(table);  
        
        entityLablel = new JLabel(Bundle.Entity());
        entityLablel.setFont(new Font("Calibri", Font.PLAIN, 24));
        nameLablel = new JLabel(Bundle.Name());
        nameLablel.setFont(new Font("Calibri", Font.BOLD, 17));
        nameField = new JLabel();
        nameField.setFont(new Font("Calibri", Font.PLAIN, 17));
        
        addButton = new JButton();
        addButton.setEnabled(false);
        addButton.setText(Bundle.Add_Column_Button());
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewColumn();
            }
        });
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,15,0,15);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(entityLablel, gbc);
        gbc.gridy = 1;
        add(nameLablel, gbc);
        gbc.gridx = 1;
        add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(addButton, gbc);
        gbc.gridy = 3;
        gbc.gridwidth=2;
        gbc.gridheight=13;
        gbc.weighty=10;
        gbc.fill = GridBagConstraints.BOTH;
        add(tablePanel, gbc);
    }
    
    private void addNewColumn(){
        if(selectedNode==null)
            return;
        
        Column toAdd = new Column();
        try {
            ColumnNode cn = new ColumnNode(toAdd);
            Node[] nodesAdd = {cn};
            selectedNode.getChildren().add(nodesAdd);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
            logger.error(ex);
        }
        updateTable();                
    }
    
    public void updateTable(){
        if(selectedNode==null)
            return;
        
        ColumnTableItemModel model = (ColumnTableItemModel) table.getModel();
        model.clear();

        for(Node n : selectedNode.getChildren().getNodes()){
            Column col = n.getLookup().lookup(Column.class);
            if(col!=null)
                model.add(col);
        }
        
        addButton.setEnabled(true);
        nameField.setText(selectedNode.getDisplayName());
        model.fireTableDataChanged();
    }
}
