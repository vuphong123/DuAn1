package udpm.fpt.component;

import udpm.fpt.Utitlity.BcryptHash;
import udpm.fpt.form.ForgotPassword;
import udpm.fpt.form.Login;
import udpm.fpt.model.User;
import udpm.fpt.service.UserService;

/**
 * @author NONG HOANG VU
 */
public class NewPassword extends javax.swing.JPanel {

    private ForgotPassword forgotPassword;
    private String email;
    private User user;

    public NewPassword(ForgotPassword forgotPassword, String email, User user) {
        initComponents();
        this.forgotPassword = forgotPassword;
        this.email = email;
        this.user = user;
    }

    private Boolean validate(String pw1, String pw2) {
        if (pw1.length() < 5) {
            Notification notification = new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "Password is a required field and must contain at least 5 characters !!!");
            notification.showNotification();
            return true;
        } else if (!pw1.equals(pw2)) {
            Notification notification = new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "The password does not match the first password!!!");
            notification.showNotification();
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtPassword = new udpm.fpt.swing.PasswordField();
        txtConfig = new udpm.fpt.swing.PasswordField();
        button1 = new udpm.fpt.swing.Button();
        button2 = new udpm.fpt.swing.Button();

        setBackground(new java.awt.Color(255, 255, 255));

        txtPassword.setLabelText("New password");
        txtPassword.setShowAndHide(true);

        txtConfig.setLabelText("Config password");
        txtConfig.setShowAndHide(true);

        button1.setBackground(new java.awt.Color(51, 102, 255));
        button1.setForeground(new java.awt.Color(255, 255, 255));
        button1.setText("Next");
        button1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button2.setBackground(new java.awt.Color(51, 102, 255));
        button2.setForeground(new java.awt.Color(255, 255, 255));
        button2.setText("Back");
        button2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtConfig, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        this.forgotPassword.setForm(new VerificationCode(this.forgotPassword, this.email, this.user));
    }//GEN-LAST:event_button2ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        if (!validate(String.valueOf(txtPassword.getPassword()), String.valueOf(txtConfig.getPassword()))) {
            UserService changePw = new UserService();
            User newPw = this.user;
            newPw.setPassword(new BcryptHash().hashPassword(String.valueOf(txtPassword.getPassword())));
            if (changePw.changePassword(newPw)) {
                Notification notification = new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP, "Success!!");
                notification.showNotification();
                new Login().setVisible(true);
                this.forgotPassword.dispose();
            }
        }

    }//GEN-LAST:event_button1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.Button button1;
    private udpm.fpt.swing.Button button2;
    private udpm.fpt.swing.PasswordField txtConfig;
    private udpm.fpt.swing.PasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
