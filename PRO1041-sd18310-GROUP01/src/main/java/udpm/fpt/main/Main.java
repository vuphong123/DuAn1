package udpm.fpt.main;

import java.awt.Image;

import udpm.fpt.component.MenuLayout;
import udpm.fpt.form.*;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import udpm.fpt.Utitlity.BcryptHash;
import udpm.fpt.component.Notification;
import udpm.fpt.form.History;
import udpm.fpt.form.Dashboard;
import udpm.fpt.form.Login;
import udpm.fpt.form.Setting;
import udpm.fpt.form.UserManagementForm;
import udpm.fpt.model.User;

public class Main extends javax.swing.JFrame {

    private User user;
    public final String ADMIN_ROLE = "Admin";
    private final MigLayout layout;
    private final MainForm main;
    private final MenuLayout menu;
    private final Animator animator;

    public Main(User user) {
        initComponents();
        openDefault();
        Image icon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Icon/cow.png"))).getImage();
        this.setIconImage(icon);
        layout = new MigLayout("fill", "0[fill]0", "0[fill]0");
        this.user = user;
        main = new MainForm(this.user);
        menu = new MenuLayout();
        menu.getMenu().initMoving(Main.this);
        main.initMoving(Main.this);
        mainPanel.setLayer(menu, JLayeredPane.POPUP_LAYER);
        mainPanel.setLayout(layout);
        mainPanel.add(main);
        mainPanel.add(menu, "pos -215 0 100% 100%", 0);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                float x = (fraction * 215);
                float alpha;
                if (menu.isShow()) {
                    x = -x;
                    alpha = 0.5f - (fraction / 2);
                } else {
                    x -= 215;
                    alpha = fraction / 2;
                }
                layout.setComponentConstraints(menu, "pos " + (int) x + " 0 100% 100%");
                if (alpha < 0) {
                    alpha = 0;
                }
                menu.setAlpha(alpha);
                mainPanel.revalidate();
            }

            @Override
            public void end() {
                menu.setShow(!menu.isShow());
                if (!menu.isShow()) {
                    menu.setVisible(false);
                }
            }

        };
        animator = new Animator(200, target);
        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    if (!animator.isRunning()) {
                        if (menu.isShow()) {
                            animator.start();
                        }
                    }
                }
            }
        });
        main.addEventMenu((ActionEvent ae) -> {
            if (!animator.isRunning()) {
                if (!menu.isShow()) {
                    menu.setVisible(true);
                    animator.start();
                }
            }
        });
        menu.getMenu().addEventMenuSelected((int index) -> {
            switch (index) {
                case 0 -> {
                    main.show(new Dashboard());
                }
                case 1 -> {
                    main.show(new SanPhamForm(this));               
                }
                case 2 -> {
                    main.show(new FormBill(this,this.user));
                }
                case 3 -> {
                    if (ADMIN_ROLE.equalsIgnoreCase(this.user.getRole())) {
                        main.show(new History(this.user, this));
                    } else {
                        showAccessWarning();
                    }
                }
                case 4 -> {
                    if (ADMIN_ROLE.equalsIgnoreCase(this.user.getRole())) {
                        main.show(new VoucherTable(this.user));
                    } else {
                        showAccessWarning();
                    }
                }
                case 5 -> {
                    if (ADMIN_ROLE.equalsIgnoreCase(this.user.getRole())) {
                        main.show(new UserManagementForm(this.user, this));
                    } else {
                        showAccessWarning();
                    }
                }
                case 6 -> {
                    if (ADMIN_ROLE.equalsIgnoreCase(this.user.getRole())) {
                        main.show(new KhachHangForm());
                    } else {
                        showAccessWarning();
                    }
                }
                case 7 -> {
                    main.show(new ThongKeForm());
                }
                case 8 -> {
                    main.show(new Setting(this.user, this));
                }
                case 9 -> {
                    new Login().setVisible(true);
                    this.dispose();
                }
                default -> {
                    System.out.println("index: " + index);
                }
            }
        });
    }

    private void showAccessWarning() {
        Notification notification = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_RIGHT, new BcryptHash().decodeBase64("T25seSBhZG1pbmlzdHJhdG9ycyBoYXZlIGFjY2Vzcw=="));
        notification.showNotification();
        main.show(new Dashboard());
    }

    public enum SizeOption {
        FULL_SIZE, DEFAULT_SIZE
    }

    public void settingSize(SizeOption setSize) {
        if (null != setSize) {
            switch (setSize) {
                case FULL_SIZE ->
                    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
                case DEFAULT_SIZE -> {
                    this.setSize(1500, 850);
                    this.setLocationRelativeTo(null);
                    this.pack();
                }
                default -> {
                }
            }
        }
    }

    public String readFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Setting.ser"))) {
            return (String) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    private void openDefault() {
        if (readFile() == null) {
            settingSize(SizeOption.DEFAULT_SIZE);
        } else if (readFile().equalsIgnoreCase("Default")) {
            settingSize(SizeOption.DEFAULT_SIZE);
        } else {
            settingSize(SizeOption.FULL_SIZE);
        }
    }

    public void notificationShowWARNING(String message) {
        new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_RIGHT, message).showNotification();
    }

    public void notificationShowINFO(String message) {
        new Notification(this, Notification.Type.INFO, Notification.Location.TOP_RIGHT, message).showNotification();
    }

    public void notificationShowSUCCESS(String message) {
        new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_RIGHT, message).showNotification();
    }

    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TH True Milk");
        setUndecorated(true);
        setResizable(false);

        mainPanel.setBackground(new java.awt.Color(250, 250, 250));
        mainPanel.setOpaque(true);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1500, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 850, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane mainPanel;
    // End of variables declaration//GEN-END:variables
}
