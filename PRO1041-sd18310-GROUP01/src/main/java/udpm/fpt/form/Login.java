package udpm.fpt.form;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import javax.swing.ImageIcon;

import udpm.fpt.Utitlity.BcryptHash;
import udpm.fpt.Utitlity.LoginInfo;
import udpm.fpt.Utitlity.LoginInfoSerializationUtil;
import udpm.fpt.component.Notification;
import udpm.fpt.main.Main;
import udpm.fpt.service.LoginService;

/**
 * @author NONG HOANG VU
 */
public class Login extends javax.swing.JFrame {

    private final LoginService login;
    private final BcryptHash bcryptHash = new BcryptHash();

    public Login() {
        initComponents();
        this.login = new LoginService();
        this.login.loadAsync();
        init();
    }

    private void init() {
        Image icon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Icon/cow.png"))).getImage();
        this.setIconImage(icon);
        isRemember();
    }

    public String getIPAddress() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            return localhost.getHostAddress().trim();
        } catch (UnknownHostException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    private void isRemember() {
        LoginInfo cookie = new LoginInfoSerializationUtil().readLoginInfoFromFile();
        if (cookie != null) {
            if (bcryptHash.decodeBase64(cookie.getIpAddress()).equals(getIPAddress())) {
                txtUsername.setText(cookie.getUsername());
                txtPassword.setText(bcryptHash.decodeBase64(cookie.getPassword()));
                ckbRemember.setSelected(true);
            }
        }
    }

    private void rememberPassword(Boolean selected) {
        LoginInfo loginInfo = new LoginInfo();
        if (selected) {
            loginInfo.setUsername(txtUsername.getText());
            loginInfo.setPassword(bcryptHash.encodeBase64(String.valueOf(txtPassword.getPassword())));
            loginInfo.setIpAddress(bcryptHash.encodeBase64(getIPAddress()));
        } else {
            loginInfo = null;
        }
        new LoginInfoSerializationUtil().saveLoginInfoToFile(loginInfo);
    }

    private void getLogin() {
        if (this.login.login(txtUsername.getText(), String.valueOf(txtPassword.getPassword())) != null) {
            new Main(this.login.login(txtUsername.getText(), String.valueOf(txtPassword.getPassword())))
                    .setVisible(true);
            rememberPassword(ckbRemember.isSelected());
            this.dispose();
        } else {
            Notification notification = new Notification(this, Notification.Type.INFO,
                    Notification.Location.DEFAULT_DESKTOP,
                    bcryptHash.decodeBase64("SW52YWxpZCB1c2VybmFtZSBvciBwYXNzd29yZCE="));
            notification.showNotification();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        body = new javax.swing.JPanel();
        txtPassword = new udpm.fpt.swing.PasswordField();
        txtUsername = new udpm.fpt.swing.TextField();
        jLabel2 = new javax.swing.JLabel();
        btnLogin = new udpm.fpt.swing.Button();
        jLabel1 = new javax.swing.JLabel();
        ckbRemember = new udpm.fpt.swing.JCheckBoxCustom();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setResizable(false);

        body.setBackground(new java.awt.Color(255, 255, 255));

        txtPassword.setLabelText("Password");
        txtPassword.setShowAndHide(true);
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswordKeyPressed(evt);
            }
        });

        txtUsername.setLabelText("Username");
        txtUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsernameKeyPressed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(127, 127, 127));
        jLabel2.setText("Login");

        btnLogin.setBackground(new java.awt.Color(102, 204, 255));
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Login");
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoginMouseClicked(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(102, 153, 255));
        jLabel1.setText("Forgot password?");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        ckbRemember.setBackground(new java.awt.Color(51, 51, 255));
        ckbRemember.setForeground(new java.awt.Color(127, 127, 127));
        ckbRemember.setText("Remember me");

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyLayout.createSequentialGroup()
                .addContainerGap(151, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(150, 150, 150))
            .addGroup(bodyLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(bodyLayout.createSequentialGroup()
                        .addComponent(ckbRemember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addComponent(txtUsername, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addGap(15, 15, 15)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ckbRemember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        new ForgotPassword().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void btnLoginMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnLoginMouseClicked
        getLogin();
    }// GEN-LAST:event_btnLoginMouseClicked

    private void txtUsernameKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtUsernameKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtPassword.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            txtPassword.requestFocus();
        }
    }// GEN-LAST:event_txtUsernameKeyPressed

    private void txtPasswordKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtPasswordKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            getLogin();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            txtUsername.requestFocus();
        }
    }// GEN-LAST:event_txtPasswordKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private udpm.fpt.swing.Button btnLogin;
    private udpm.fpt.swing.JCheckBoxCustom ckbRemember;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private udpm.fpt.swing.PasswordField txtPassword;
    private udpm.fpt.swing.TextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
