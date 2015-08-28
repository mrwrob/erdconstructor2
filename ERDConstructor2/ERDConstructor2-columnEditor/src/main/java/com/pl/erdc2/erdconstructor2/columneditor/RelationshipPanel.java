package com.pl.erdc2.erdconstructor2.columneditor;

import com.pl.erdc2.erdconstructor2.api.RelationshipNode;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Piotrek
 */
public class RelationshipPanel extends JPanel{
    JLabel relationshipLablel;
    JLabel nameLabel;
    JLabel descriptionLabel;
    JLabel entityLabel;
    JLabel typeLabel;
    JTextField nameField;
    JTextArea descriptionField;
    JComboBox<String> entity1;
    JComboBox<String> entity2;
    JComboBox<String> type1;
    JComboBox<String> type2;
    RelationshipNode selectedNode = null;

    public RelationshipPanel() {
        setName(Bundle.CTL_ColumnEditorTopComponent());
        setToolTipText(Bundle.HINT_ColumnEditorTopComponent());
                
        setLayout(new GridBagLayout());
        
        relationshipLablel = new JLabel(Bundle.Relationship());
        relationshipLablel.setFont(new Font("Calibri", Font.PLAIN, 24));
        nameLabel = new JLabel(Bundle.Name());
        nameLabel.setFont(new Font("Calibri", Font.BOLD, 17));
        descriptionLabel = new JLabel(Bundle.Description());
        descriptionLabel.setFont(new Font("Calibri", Font.BOLD, 17));
        entityLabel = new JLabel(Bundle.Entity());
        entityLabel.setFont(new Font("Calibri", Font.BOLD, 17));
        typeLabel = new JLabel(Bundle.Type());
        typeLabel.setFont(new Font("Calibri", Font.BOLD, 17));
        
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200,20));
        descriptionField = new JTextArea();
        descriptionField.setPreferredSize(new Dimension(200,60));
        entity1 = new JComboBox<>();
        entity2 = new JComboBox<>();
        type1 = new JComboBox<>();
        type2 = new JComboBox<>();
        
        JLabel empty = new JLabel("");
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,15,0,15);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        add(relationshipLablel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth=2;
        add(nameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(descriptionLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;        
        add(descriptionField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth=1;
        add(entityLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(typeLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(entity1, gbc);
        gbc.gridx = 1;
        gbc.gridy = 6;
        add(type1, gbc);
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(entity2, gbc);
        gbc.gridx = 1;
        gbc.gridy = 7;
        add(type2, gbc);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth=2;
        gbc.gridheight=15;
        gbc.weighty=15;
        gbc.fill = GridBagConstraints.BOTH;
        add(empty, gbc);
        
    }
}
