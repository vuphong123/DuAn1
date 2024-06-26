package udpm.fpt.component;

import java.security.SecureRandom;

import udpm.fpt.form.ForgotPassword;
import udpm.fpt.model.User;
import udpm.fpt.service.ServiceMail;

/**
 * @author NONG HOANG VU
 */
public class VerificationCode extends javax.swing.JPanel {

    private ForgotPassword forgotPassword;
    private String email;
    private String verifyCode;
    private User user;

    public VerificationCode(ForgotPassword forgotPassword, String email, User user) {
        initComponents();
        this.forgotPassword = forgotPassword;
        this.email = email;
        this.user = user;
    }

    public String generateRandomCode(int length) {
        String characters = "0123456789";
        StringBuilder randomCode = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            randomCode.append(characters.charAt(randomIndex));
        }

        return randomCode.toString();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtVerificationCode = new udpm.fpt.swing.TextField();
        button1 = new udpm.fpt.swing.Button();
        button2 = new udpm.fpt.swing.Button();
        button3 = new udpm.fpt.swing.Button();

        setBackground(new java.awt.Color(255, 255, 255));

        txtVerificationCode.setLabelText("Verification code");

        button1.setBackground(new java.awt.Color(204, 204, 204));
        button1.setForeground(new java.awt.Color(255, 255, 255));
        button1.setText("Send");
        button1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button2.setBackground(new java.awt.Color(51, 102, 255));
        button2.setForeground(new java.awt.Color(255, 255, 255));
        button2.setText("Next");
        button2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        button3.setBackground(new java.awt.Color(51, 102, 255));
        button3.setForeground(new java.awt.Color(255, 255, 255));
        button3.setText("Back");
        button3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(198, 198, 198)
                                                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtVerificationCode, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtVerificationCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        if (txtVerificationCode.getText().trim().equals(this.verifyCode)) {
            this.forgotPassword.setForm(new NewPassword(this.forgotPassword, this.email, this.user));
        } else {
            Notification notification = new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "Invalid Code!");
            notification.showNotification();
        }
    }//GEN-LAST:event_button2ActionPerformed

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        this.forgotPassword.setForm(new EnterGmail(this.forgotPassword));
    }//GEN-LAST:event_button3ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        button1.setText("Sending");
        try {
            int codeLength = 6;
            String randomCode = generateRandomCode(codeLength);
            ServiceMail mail = new ServiceMail();
            this.verifyCode = randomCode;
            mail.sendMain(this.email, this.verifyCode);
            Notification notification = new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP, "Please check the code in your email");
            notification.showNotification();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        button1.setText("Return code");
    }//GEN-LAST:event_button1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.Button button1;
    private udpm.fpt.swing.Button button2;
    private udpm.fpt.swing.Button button3;
    private udpm.fpt.swing.TextField txtVerificationCode;
    // End of variables declaration//GEN-END:variables
}
