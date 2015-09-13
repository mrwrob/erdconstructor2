/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pl.erdc2.globalsettings;

import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;

final class UserInfoPanel extends javax.swing.JPanel {

    private final UserInfoOptionsPanelController controller;

    UserInfoPanel(UserInfoOptionsPanelController controller) {
        this.controller = controller;
        initComponents();
        
        Preferences pref = NbPreferences.forModule(UserInfoPanel.class); 
        String name = pref.get("firstNamePreference", "");
        String lastName = pref.get("lastNamePreference", ""); 
        String indexNo = pref.get("indexNoPreference", ""); 
         
        pref.addPreferenceChangeListener(new PreferenceChangeListener() { 
            @Override
            public void preferenceChange(PreferenceChangeEvent evt) { 
                if (evt.getKey().equals("firstNamePreference")) { 
                    firsNameTextField.setText(evt.getNewValue()); 
                }
                if (evt.getKey().equals("lastNamePreference")) { 
                    lastNameTextField.setText(evt.getNewValue()); 
                }
                if (evt.getKey().equals("indexNoPreference")) { 
                    indexNoTextField.setText(evt.getNewValue()); 
                }
            } }); 
        firsNameTextField.setText(name); 
        lastNameTextField.setText(lastName); 
        indexNoTextField.setText(indexNo);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        firstNameLabel = new javax.swing.JLabel();
        lastNameLabel = new javax.swing.JLabel();
        indexNoLabel = new javax.swing.JLabel();
        firsNameTextField = new javax.swing.JTextField();
        lastNameTextField = new javax.swing.JTextField();
        indexNoTextField = new javax.swing.JTextField();

        firstNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(firstNameLabel, org.openide.util.NbBundle.getMessage(UserInfoPanel.class, "UserInfoPanel.firstNameLabel.text")); // NOI18N

        lastNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(lastNameLabel, org.openide.util.NbBundle.getMessage(UserInfoPanel.class, "UserInfoPanel.lastNameLabel.text")); // NOI18N

        indexNoLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(indexNoLabel, org.openide.util.NbBundle.getMessage(UserInfoPanel.class, "UserInfoPanel.indexNoLabel.text")); // NOI18N

        firsNameTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        firsNameTextField.setText(org.openide.util.NbBundle.getMessage(UserInfoPanel.class, "UserInfoPanel.firsNameTextField.text")); // NOI18N

        lastNameTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        lastNameTextField.setText(org.openide.util.NbBundle.getMessage(UserInfoPanel.class, "UserInfoPanel.lastNameTextField.text")); // NOI18N

        indexNoTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        indexNoTextField.setText(org.openide.util.NbBundle.getMessage(UserInfoPanel.class, "UserInfoPanel.indexNoTextField.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(firstNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lastNameLabel)
                            .addComponent(indexNoLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(indexNoTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(firsNameTextField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lastNameTextField, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(106, 106, 106))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameLabel)
                    .addComponent(firsNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastNameLabel)
                    .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(indexNoLabel)
                    .addComponent(indexNoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    void load() {
        firsNameTextField.setText(NbPreferences.forModule(UserInfoPanel.class).get("firstNamePreference", ""));
        lastNameTextField.setText(NbPreferences.forModule(UserInfoPanel.class).get("lastNamePreference", ""));
        indexNoTextField.setText(NbPreferences.forModule(UserInfoPanel.class).get("indexNoPreference", ""));
    }

    void store() {
        NbPreferences.forModule(UserInfoPanel.class).put("firstNamePreference", firsNameTextField.getText());
        NbPreferences.forModule(UserInfoPanel.class).put("lastNamePreference", lastNameTextField.getText());
        NbPreferences.forModule(UserInfoPanel.class).put("indexNoPreference", indexNoTextField.getText());
    }

    boolean valid() {
        // TODO check whether form is consistent and complete
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField firsNameTextField;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JLabel indexNoLabel;
    private javax.swing.JTextField indexNoTextField;
    private javax.swing.JLabel lastNameLabel;
    private javax.swing.JTextField lastNameTextField;
    // End of variables declaration//GEN-END:variables
}