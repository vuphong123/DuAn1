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
import udpm.fpt.model.Unit;
import udpm.fpt.model.User;
import udpm.fpt.service.ProductService;

/**
 *
 * @author NONG HOANG VU
 */
public class UnitManagement extends javax.swing.JFrame {

//    private final ProductService list;
    private User user;
    private Boolean result = true;
    private Consumer<Boolean> resultCallback;

//    public UnitManagement(User user) {
//        initComponents();
//        this.list = new ProductService();
//        this.user = user;
//        loadDataAndFillUnit();
//        txtUnit.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                if (findUnit(txtUnit.getText())) {
//                    btnOK.setText("Delete");
//                } else {
//                    btnOK.setText("Save");
//                }
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                if (findUnit(txtUnit.getText())) {
//                    btnOK.setText("Delete");
//                } else {
//                    btnOK.setText("Save");
//                }
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                if (findUnit(txtUnit.getText())) {
//                    btnOK.setText("Delete");
//                } else {
//                    btnOK.setText("Save");
//                }
//            }
//        });
//        btnOK.addActionListener((ActionEvent e) -> {
//            if (txtUnit.getText().isBlank()) {
//                Notification n = new Notification(this, Notification.Type.INFO, Notification.Location.DEFAULT_DESKTOP, "DATA IS EMPTY");
//                n.showNotification();
//                return;
//            } else if (txtUnit.getText().length() >= 10) {
//                Notification n = new Notification(this, Notification.Type.INFO, Notification.Location.DEFAULT_DESKTOP, "DATA IS INVALID");
//                n.showNotification();
//                return;
//            }
//            if (btnOK.getText().equalsIgnoreCase("Save")) {
//                NewUnit();
//            } else if (btnOK.getText().equalsIgnoreCase("Delete")) {
//                RemoveUnit();
//            }
//        });
//        btnCancel.addActionListener((ActionEvent e) -> {
//            closeForm();
//        });
//    }

//    private void NewUnit() {
//        Date defaultValue = new Date();
//        Unit unit = new Unit();
//        unit.setMeasurement_unit(txtUnit.getText().trim());
//        unit.setCreate_at(defaultValue);
//        unit.setUser(this.user);
//        if (this.list.insertUnit(unit, this.user)) {
//            Notification n = new Notification(this, Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP, "SUCCESS");
//            n.showNotification();
//        } else {
//            Notification n = new Notification(this, Notification.Type.INFO, Notification.Location.DEFAULT_DESKTOP, "FAILED");
//            n.showNotification();
//        }
//        closeForm();
//    }

//    private void RemoveUnit() {
//        try {
//            Unit u = (Unit) cbbUnit.getSelectedItem();
//            this.list.removeByMeasurement_unit(u, this.user);
//            Notification n = new Notification(this, Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP, "SUCCESS");
//            n.showNotification();
//            closeForm();
//
//        } catch (Exception e) {
//            Notification n = new Notification(this, Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "This unit is being used with another product");
//            n.showNotification();
//        }
//    }

//    private Boolean findUnit(String data) {
//        try {
//            List<Unit> units = this.list.loadUnit().get();
//            for (Unit u : units) {
//                if (data.equalsIgnoreCase(u.getMeasurement_unit().trim())) {
//                    return true;
//                }
//            }
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace(System.out);
//        }
//        return false;
//    }

//    public void loadDataAndFillUnit() {
//        CompletableFuture<List<Unit>> future = this.list.loadUnit();
//        future.thenAcceptAsync(data -> {
//            SwingUtilities.invokeLater(() -> {
//                updateUnit(data);
//            });
//        }).exceptionally(throwable -> {
//            throwable.printStackTrace(System.out);
//            return null;
//        });
//    }

    private void updateUnit(List<Unit> data) {
        DefaultComboBoxModel<Unit> cbbModel = new DefaultComboBoxModel<>();
        cbbUnit.removeAll();
        cbbUnit.setModel((DefaultComboBoxModel) cbbModel);
        for (Unit u : data) {
            cbbModel.addElement(u);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtTitle = new javax.swing.JLabel();
        btnOK = new udpm.fpt.swing.ButtonMessage();
        btnCancel = new udpm.fpt.swing.ButtonMessage();
        txtUnit = new udpm.fpt.swing.TextField();
        cbbUnit = new udpm.fpt.swing.Combobox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 25, 25, 25));

        txtTitle.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtTitle.setForeground(new java.awt.Color(80, 80, 80));
        txtTitle.setText("Unit Management");

        btnOK.setBackground(new java.awt.Color(51, 255, 51));
        btnOK.setForeground(new java.awt.Color(255, 255, 255));
        btnOK.setText("Delete");
        btnOK.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        btnCancel.setBackground(new java.awt.Color(255, 51, 51));
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Cancel");
        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        txtUnit.setLabelText("");

        cbbUnit.setLabeText("");
        cbbUnit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbUnitItemStateChanged(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                        .addComponent(cbbUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtUnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTitle)
                    .addComponent(cbbUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(txtUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void cbbUnitItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbUnitItemStateChanged
        Unit u = (Unit) cbbUnit.getSelectedItem();
        txtUnit.setText(u.getMeasurement_unit().trim());
    }//GEN-LAST:event_cbbUnitItemStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.ButtonMessage btnCancel;
    private udpm.fpt.swing.ButtonMessage btnOK;
    private udpm.fpt.swing.Combobox cbbUnit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel txtTitle;
    private udpm.fpt.swing.TextField txtUnit;
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
