/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package udpm.fpt.component;

import udpm.fpt.Utitlity.BcryptHash;
import udpm.fpt.form.ForgotPassword;
import udpm.fpt.model.User;
import udpm.fpt.service.UserService;

/**
 *
 * @author NONG HOANG VU
 */
public class EnterGmail extends javax.swing.JPanel {

    /**
     * Creates new form EnterGmail
     */
    private ForgotPassword forgotPassword;
    private User user;

    public EnterGmail(ForgotPassword forgotPassword) {
        this.forgotPassword = forgotPassword;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtEmail = new udpm.fpt.swing.TextField();
        button1 = new udpm.fpt.swing.Button();

        setBackground(new java.awt.Color(255, 255, 255));

        txtEmail.setLabelText("Email");

        button1.setBackground(new java.awt.Color(51, 102, 255));
        button1.setForeground(new java.awt.Color(255, 255, 255));
        button1.setText("Next");
        button1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        UserService user = new UserService();
        try {
            this.user = user.getUserByGmail(txtEmail.getText().trim()).getUser();
        } catch (Exception e) {
            Notification notification = new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "This email cannot be found!");
            notification.showNotification();
            return;
        }
        if (this.user != null) {
            this.forgotPassword.setForm(new VerificationCode(this.forgotPassword, txtEmail.getText().trim(), this.user));
        } else {
            Notification notification = new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "This email cannot be found!");
            notification.showNotification();
        }
    }//GEN-LAST:event_button1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.Button button1;
    private udpm.fpt.swing.TextField txtEmail;
    // End of variables declaration//GEN-END:variables
}