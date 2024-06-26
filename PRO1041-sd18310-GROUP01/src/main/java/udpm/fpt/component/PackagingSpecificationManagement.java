package udpm.fpt.component;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import udpm.fpt.model.PackagingSpecification;
import udpm.fpt.model.User;
import udpm.fpt.service.ProductService;

/**
 *
 * @author NONG HOANG VU
 */
public class PackagingSpecificationManagement extends javax.swing.JFrame {

    private final ProductService list;
    private Boolean result = true;
    private User user;
    private Consumer<Boolean> resultCallback;

    public PackagingSpecificationManagement(User user) {
        initComponents();
        this.list = new ProductService();
        this.user = user;
        loadDataAndFillPackagingSpecification();
        txtPackagingSpecification.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (findPackagingSpecification(txtPackagingSpecification.getText())) {
                    btnOK.setText("Delete");
                } else {
                    btnOK.setText("Save");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (findPackagingSpecification(txtPackagingSpecification.getText())) {
                    btnOK.setText("Delete");
                } else {
                    btnOK.setText("Save");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (findPackagingSpecification(txtPackagingSpecification.getText())) {
                    btnOK.setText("Delete");
                } else {
                    btnOK.setText("Save");
                }
            }
        });
        btnOK.addActionListener((ActionEvent e) -> {
            if (txtPackagingSpecification.getText().isBlank()) {
                Notification n = new Notification(this, Notification.Type.INFO, Notification.Location.DEFAULT_DESKTOP, "DATA IS EMPTY");
                n.showNotification();
                return;
            } else if (txtPackagingSpecification.getText().length() >= 100) {
                Notification n = new Notification(this, Notification.Type.INFO, Notification.Location.DEFAULT_DESKTOP, "DATA IS INVALID");
                n.showNotification();
                return;
            }
            if (btnOK.getText().equalsIgnoreCase("Save")) {
                NewUnit();
            } else if (btnOK.getText().equalsIgnoreCase("Delete")) {
                RemovePackagingSpecification();
            }
        });
        btnCancel.addActionListener((ActionEvent e) -> {
            closeForm();
        });
    }

    private void NewUnit() {
        Date defaultValue = new Date();
        PackagingSpecification p = new PackagingSpecification();
        p.setPackaging_type(txtPackagingSpecification.getText().trim());
        p.setCreate_at(defaultValue);
        p.setUser(this.user);
//        if (this.list.insertPackagingSpecification(p, this.user)) {
//            Notification n = new Notification(this, Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP, "SUCCESS");
//            n.showNotification();
//        } else {
//            Notification n = new Notification(this, Notification.Type.INFO, Notification.Location.DEFAULT_DESKTOP, "FAILED");
//            n.showNotification();
//        }
        closeForm();
    }

    private void RemovePackagingSpecification() {
        try {
            PackagingSpecification p = (PackagingSpecification) cbbPackagingSpecification.getSelectedItem();
//            this.list.removePackagingSpecification(p, this.user);
            Notification n = new Notification(this, Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP, "SUCCESS");
            n.showNotification();
            closeForm();

        } catch (Exception e) {
            Notification n = new Notification(this, Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "This packagingSpecification is being used with another product");
            n.showNotification();
        }
    }

    private Boolean findPackagingSpecification(String data) {
//        try {
//            List<PackagingSpecification> specificationsList = this.list.loadPackagingSpecification().get();
//            for (PackagingSpecification specification : specificationsList) {
//                if (data.equalsIgnoreCase(specification.getPackaging_type().trim())) {
//                    return true;
//                }
//            }
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace(System.out);
//        }
        return false;
    }

    public void loadDataAndFillPackagingSpecification() {
//        CompletableFuture<List<PackagingSpecification>> future = this.list.loadPackagingSpecification();
//        future.thenAcceptAsync(data -> {
//            SwingUtilities.invokeLater(() -> {
//                updatePackagingSpecification(data);
//            });
//        }).exceptionally(throwable -> {
//            throwable.printStackTrace(System.out);
//            return null;
//        });
    }

    private void updatePackagingSpecification(List<PackagingSpecification> data) {
        DefaultComboBoxModel<PackagingSpecification> cbbModel = new DefaultComboBoxModel<>();
        cbbPackagingSpecification.removeAll();
        cbbPackagingSpecification.setModel((DefaultComboBoxModel) cbbModel);
        for (PackagingSpecification specification : data) {
            cbbModel.addElement(specification);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtTitle = new javax.swing.JLabel();
        btnOK = new udpm.fpt.swing.ButtonMessage();
        btnCancel = new udpm.fpt.swing.ButtonMessage();
        txtPackagingSpecification = new udpm.fpt.swing.TextField();
        cbbPackagingSpecification = new udpm.fpt.swing.Combobox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 25, 25, 25));

        txtTitle.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtTitle.setForeground(new java.awt.Color(80, 80, 80));
        txtTitle.setText("Packaging Specification Management");

        btnOK.setBackground(new java.awt.Color(51, 255, 51));
        btnOK.setForeground(new java.awt.Color(255, 255, 255));
        btnOK.setText("Delete");
        btnOK.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        btnCancel.setBackground(new java.awt.Color(255, 51, 51));
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Cancel");
        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        txtPackagingSpecification.setLabelText("");

        cbbPackagingSpecification.setLabeText("");
        cbbPackagingSpecification.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbPackagingSpecificationItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbbPackagingSpecification, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtPackagingSpecification, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTitle)
                    .addComponent(cbbPackagingSpecification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(txtPackagingSpecification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cbbPackagingSpecificationItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbPackagingSpecificationItemStateChanged
        PackagingSpecification p = (PackagingSpecification) cbbPackagingSpecification.getSelectedItem();
        txtPackagingSpecification.setText(p.getPackaging_type().trim());
    }//GEN-LAST:event_cbbPackagingSpecificationItemStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.ButtonMessage btnCancel;
    private udpm.fpt.swing.ButtonMessage btnOK;
    private udpm.fpt.swing.Combobox cbbPackagingSpecification;
    private javax.swing.JPanel jPanel1;
    private udpm.fpt.swing.TextField txtPackagingSpecification;
    private javax.swing.JLabel txtTitle;
    // End of variables declaration//GEN-END:variables

    private void closeForm() {
        this.dispose();
        if (resultCallback != null) {
            resultCallback.accept(result);
        }
    }

    public void setResultCallback(Consumer<Boolean> callback) {
        this.resultCallback = callback;
    }

    public Boolean getResult() {
        return result;
    }
}
