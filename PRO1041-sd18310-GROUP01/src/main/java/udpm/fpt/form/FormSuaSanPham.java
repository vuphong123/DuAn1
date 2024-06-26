/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package udpm.fpt.form;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import udpm.fpt.component.Notification;
import udpm.fpt.model.SANPHAM;
import udpm.fpt.model.SANPHAMCHITIET;
import udpm.fpt.service.SanPhamService;

/**
 *
 * @author PHONG PC
 */
public class FormSuaSanPham extends javax.swing.JDialog {

    private List<SANPHAM> listSanPham = new ArrayList<>();
    private List<SANPHAMCHITIET> listSanPhamChiTiet = new ArrayList<>();
    SanPhamService svsp = new SanPhamService();
    SANPHAM sp = new SANPHAM();
    boolean check = false;

    /**
     * Creates new form FormSuaSanPham
     */
    public FormSuaSanPham(java.awt.Frame parent, boolean modal, SANPHAM sp, List<SANPHAM> list, List<SANPHAMCHITIET> listSP) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.sp = sp;
        this.listSanPham = list;
        this.listSanPhamChiTiet = listSP;
        cbbTrangThai();
        setForm();
    }

    public void setForm() {
        txtTenSanPham.setText(sp.getTenSanPham());
        cbbTrangThai.setSelectedItem(sp.getTrangThai());
    }

    public boolean check() {
        if (txtTenSanPham.getText().trim().isEmpty()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Tên sản phẩm trống!").showNotification();
            return false;
        } else {
            if (txtTenSanPham.getText().trim().length() < 3) {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Phải lớn hơn 3 ký tự!").showNotification();
                return false;
            }
        }
        for (SANPHAM sppp : listSanPham) {
            if (sp.getTenSanPham().equals(txtTenSanPham.getText())) {

            } else {
                if (sppp.getTenSanPham().equals(txtTenSanPham.getText())) {
                    new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                            "Tên sản phẩm đã tồn tại!").showNotification();
                    return false;
                }
            }
        }
        return true;
    }

    public void cbbTrangThai() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbTrangThai.getModel();
        model.removeAllElements();
        model.addElement("Hoạt động");
        model.addElement("Ngừng hoạt động");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtTenSanPham = new udpm.fpt.swing.TextField();
        cbbTrangThai = new udpm.fpt.swing.Combobox();
        btnDongY = new udpm.fpt.swing.ButtonMessage();
        btnHuy = new udpm.fpt.swing.ButtonMessage();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtTenSanPham.setLabelText("Tên sản phẩm");

        cbbTrangThai.setLabeText("Trạng thái");

        btnDongY.setText("Đồng ý");
        btnDongY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDongYActionPerformed(evt);
            }
        });

        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnDongY, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDongY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDongYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDongYActionPerformed
        if (check()) {
            String txtTenSanPham = this.txtTenSanPham.getText();
            String trangTahi = cbbTrangThai.getSelectedItem().toString();
            sp.setTenSanPham(txtTenSanPham);
            sp.setTrangThai(trangTahi);
            svsp.saveAndUpdateSanPham(sp);
            for (SANPHAMCHITIET spct : listSanPhamChiTiet) {
                if (spct.getId_SanPham().getTenSanPham().equals(sp.getTenSanPham())) {
                    spct.setTrangThai(false);
                    svsp.saveAndUpdateSanPhamChiTiet(spct);
                }
            }
            check = true;
            this.dispose();
        }
    }//GEN-LAST:event_btnDongYActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnHuyActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.ButtonMessage btnDongY;
    private udpm.fpt.swing.ButtonMessage btnHuy;
    private udpm.fpt.swing.Combobox cbbTrangThai;
    private javax.swing.JPanel jPanel1;
    private udpm.fpt.swing.TextField txtTenSanPham;
    // End of variables declaration//GEN-END:variables
}