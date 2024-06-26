/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package udpm.fpt.component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import udpm.fpt.form.FormBill;
import udpm.fpt.model.Customer;
import udpm.fpt.model.KhachHang;
import udpm.fpt.service.BillService;
import udpm.fpt.service.KhachHangService;
import udpm.serviceDelivery.MailSender;

/**
 *
 * @author PHONG PC
 */
public final class ChooseCustomersForm extends javax.swing.JFrame {

    MailSender mail = new MailSender();
    FormBill form;
    private final BillService sv = new BillService();
    KhachHangService khsv = new KhachHangService();
    List<KhachHang> listKH = new ArrayList<>();

    /**
     * Creates new form ChooseCustomersForm
     *
     * @param bill
     */
    public ChooseCustomersForm(FormBill bill) {
        initComponents();
        this.setLocationRelativeTo(null);
        loadDataCustomer();
        form = bill;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
    }

    public void loadDataCustomer() {
        CompletableFuture<List<KhachHang>> future = this.sv.findKhachHang();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                loadCustomer(data);
                loadThongTinKhachHang(data);
                listKH = data;
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }
//    public void SearchCustomer(String name) {
//        CompletableFuture<List<Customer>> future = this.sv.searchCustomer(name);
//        future.thenAcceptAsync(data -> {
//            SwingUtilities.invokeLater(() -> {
//                loadCustomer(data);
//            });
//        }).exceptionally(throwable -> {
//            throwable.printStackTrace(System.out);
//            return null;
//        });
//    }

    private void loadCustomer(List<KhachHang> list) {
        DefaultTableModel model = (DefaultTableModel) tblCustomer.getModel();

        model.setColumnCount(0);
        model.addColumn("Mã khách hàng");
        model.addColumn("Tên khách hàng");
        model.setRowCount(0);
        for (KhachHang hoaDon : list) {
            Object[] row = new Object[]{
                hoaDon.getId(),
                hoaDon.getTenKhachHang(),};
            model.addRow(row);
        }
    }

    private void loadThongTinKhachHang(List<KhachHang> list) {
        DefaultTableModel model = (DefaultTableModel) tblThongTinKhachHang.getModel();

        model.setColumnCount(0);
        model.addColumn("Mã khách hàng");
        model.addColumn("Tên khách hàng");
        model.addColumn("SDT");
        model.addColumn("Gmail");
        model.setRowCount(0);
        for (KhachHang hoaDon : list) {
            Object[] row = new Object[]{
                hoaDon.getId(),
                hoaDon.getTenKhachHang(),
                hoaDon.getSdt(),
                hoaDon.getGmail()
            };
            model.addRow(row);
        }
    }

    public boolean check() {
        String sdtDinhDang = "^0\\d{9,10}$";
        if (txtTenKhachHang.getText().trim().isEmpty()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Tên khách hàng trống!").showNotification();
            return false;
        }
        if (txtSDT.getText().trim().isEmpty()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "SDT trống!").showNotification();
            return false;
        }
        if (!txtSDT.getText().matches(sdtDinhDang)) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Số điện thoại sai định dạng!").showNotification();
            return false;
        }
        for (KhachHang khh : listKH) {
            if (txtSDT.getText().equals(khh.getSdt())) {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Số điện thoại đã tồn tại!").showNotification();
                return false;
            }
        }
        String emailDinhDang = "^[a-zA-Z0-9._%+-]+@gmail.com$";
        if (txtEmail.getText().trim().isEmpty()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Gmail trống!").showNotification();
            return false;
        }
        if (!txtEmail.getText().matches(emailDinhDang)) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Gmail sai định dạng!").showNotification();
            return false;
        }
        for (KhachHang khh : listKH) {
            if (txtEmail.getText().equals(khh.getGmail())) {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Gmail đã tồn tại!").showNotification();
                return false;
            }
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        btnC = new udpm.fpt.swing.ButtonMessage();
        txtSearchCustomer = new udpm.fpt.swing.TextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCustomer = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtTenKhachHang = new udpm.fpt.swing.TextField();
        txtSDT = new udpm.fpt.swing.TextField();
        jLabel1 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThongTinKhachHang = new javax.swing.JTable();
        buttonMessage1 = new udpm.fpt.swing.ButtonMessage();
        txtEmail = new udpm.fpt.swing.TextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setOpaque(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btnC.setBackground(new java.awt.Color(0, 153, 255));
        btnC.setForeground(new java.awt.Color(255, 255, 255));
        btnC.setText("Search");
        btnC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCActionPerformed(evt);
            }
        });

        txtSearchCustomer.setLabelText("Search by name");
        txtSearchCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchCustomerActionPerformed(evt);
            }
        });

        tblCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblCustomer.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCustomerMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCustomer);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtSearchCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnC, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSearchCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Chọn khách hàng", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        txtTenKhachHang.setLabelText("Họ và tên");

        txtSDT.setLabelText("SDT");

        jLabel1.setText("Giới tính:");

        rdoNam.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoNam);
        rdoNam.setSelected(true);
        rdoNam.setText("Nam");

        rdoNu.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        tblThongTinKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblThongTinKhachHang);

        buttonMessage1.setBackground(new java.awt.Color(0, 153, 255));
        buttonMessage1.setForeground(new java.awt.Color(255, 255, 255));
        buttonMessage1.setText("Thêm khách hàng");
        buttonMessage1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        buttonMessage1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMessage1ActionPerformed(evt);
            }
        });

        txtEmail.setLabelText("Email");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoNam)
                        .addGap(26, 26, 26)
                        .addComponent(rdoNu)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                    .addComponent(txtSDT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(215, 215, 215)
                        .addComponent(buttonMessage1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoNam)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoNu)
                    .addComponent(jLabel1))
                .addGap(23, 23, 23)
                .addComponent(buttonMessage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thêm khách hàng mới", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchCustomerActionPerformed

    }//GEN-LAST:event_txtSearchCustomerActionPerformed

    private void btnCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCActionPerformed
        String name = txtSearchCustomer.getText();
//        SearchCustomer(name);
    }//GEN-LAST:event_btnCActionPerformed

    private void tblCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCustomerMouseClicked
        int row = tblCustomer.getSelectedRow();
        if (row == -1) {
            return;
        }
        KhachHang ct = new KhachHang();
        ct.setId(Integer.valueOf(tblCustomer.getValueAt(row, 0).toString()));
        ct.setTenKhachHang(tblCustomer.getValueAt(row, 1).toString());
        form.setCustomer(ct);
        this.dispose();
    }//GEN-LAST:event_tblCustomerMouseClicked

    private void buttonMessage1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMessage1ActionPerformed
        KhachHang kh = new KhachHang();
        if (check()) {
            kh.setNgayTao(new Date());
            kh.setSdt(txtSDT.getText());
            kh.setTenKhachHang(txtTenKhachHang.getText());
            kh.setGmail(txtEmail.getText());
            kh.setTrangThai(true);
            if (rdoNam.isSelected()) {
                kh.setGioiTinh(0);
            } else {
                kh.setGioiTinh(1);
            }
            khsv.themSuaKhachHang(kh);
            List<String> listTenNhan = new ArrayList<>();
            listTenNhan.add(txtEmail.getText());
            loadDataCustomer();
            new Thread(() -> {
                    mail.sendMail("Cảm ơn bạn đã tạo tài khoản trên cửa hàng T-SHIRT BEE", listTenNhan);
                }).start();
        }
    }//GEN-LAST:event_buttonMessage1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.ButtonMessage btnC;
    private javax.swing.ButtonGroup buttonGroup1;
    private udpm.fpt.swing.ButtonMessage buttonMessage1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblCustomer;
    private javax.swing.JTable tblThongTinKhachHang;
    private udpm.fpt.swing.TextField txtEmail;
    private udpm.fpt.swing.TextField txtSDT;
    private udpm.fpt.swing.TextField txtSearchCustomer;
    private udpm.fpt.swing.TextField txtTenKhachHang;
    // End of variables declaration//GEN-END:variables
}
