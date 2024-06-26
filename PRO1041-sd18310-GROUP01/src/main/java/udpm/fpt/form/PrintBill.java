/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package udpm.fpt.form;

import java.awt.print.PrinterException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import udpm.fpt.model.BILL;
import udpm.fpt.model.BILLCHITIET;
import udpm.fpt.model.KhachHang;

/**
 *
 * @author PHONG PC
 */
public class PrintBill extends javax.swing.JDialog {
    NumberFormat numberFormat = NumberFormat.getInstance();
    private SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
    private List<BILL> listBill;
    private String maHoaDon;
    private String maKhachHang;
    private List<BILLCHITIET> listBILLCHiTiet;

    /**
     * Creates new form PrintBill
     */
    public PrintBill(java.awt.Frame parent, boolean modal, List<BILL> listbill, List<BILLCHITIET> listBillChiTite, String maHoaDon, String maKhachHang) {
        super(parent, modal);
        initComponents();
        jTextArea1.setAutoscrolls(false);
        this.setLocationRelativeTo(null);
        this.listBill = listbill;
        this.listBILLCHiTiet = listBillChiTite;
        this.maHoaDon = maHoaDon;
        this.maKhachHang = maKhachHang;
        loadBill();
    }

    public void loadBill() {
        String content = "Ngày bán hàng: " + simple.format(new Date()) + "           " + "\n";
        for(BILL bill: listBill){
            if(bill.getId().equals(maHoaDon)){
                content += "Nhân viên bán hàng: " + bill.getId_NhanVien().getId() + "           " + "\n";
            }
        }
        content += "Hóa đơn: "+maHoaDon+"\n" ;
        String khachHang = "";
        if(maKhachHang.equals(1)==false && maKhachHang !=null && maKhachHang.equals("")){
            khachHang = maKhachHang;
            System.out.println("fff");
        }
        content += "Khách hàng: " +khachHang + "\n"; 
        content += "----------------------------------------------------------------------------------------------------------------------\n";
        String bangSanPham = "Mặt hàng\t\tĐơn giá\t\tSL\t       T.Tiền\n";
        String sanPham ="";
        for (BILLCHITIET bill : listBILLCHiTiet) {
            if (bill.getIdBill().getId().equals(maHoaDon)) {
                sanPham += bill.getIdSanPhamChiTiet().getId_SanPham().getTenSanPham() + " " + bill.getIdSanPhamChiTiet().getId_MauSac().getTenMauSac() + " " + bill.getIdSanPhamChiTiet().getId_Size().getTenSize() + "\n";
                sanPham += "  " + bill.getIdSanPhamChiTiet().getId_SanPham().getId() + "\t\t" + numberFormat.format(bill.getGiaTienHienTai()) + "\t\t" + bill.getSoLuongDaMua() + "\t        " + numberFormat.format(bill.getThanhTien()) + "\n";
            }
        }
        StyledDocument doc = jTextArea1.getStyledDocument();
        String thongTinTien = "----------------------------------------------------------------------------------------------------------------------\n \n";
        for (BILL bill : listBill) {
            if (bill.getId().equals(maHoaDon)) {
                thongTinTien += "Tổng tiền thanh toán: "+ numberFormat.format(bill.getTongTien()) +"\n";
                thongTinTien += "Tổng tiền đã giảm: " + numberFormat.format(bill.getTienGiam()==null? 0 : bill.getTienGiam())+  "\n" ;
                thongTinTien += "Tổng tền khách trả: "+ numberFormat.format(bill.getTienKhachTra()==null? 0 : bill.getTienKhachTra())+" \n";
                thongTinTien += "Tiền trả lại: "+ numberFormat.format( bill.getTienTraLai())+"\n";
            }
        }
        //In đậm và chỉnh cỡ chữ
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setBold(style, true);
        StyleConstants.setFontSize(style, 20);
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);
        //IN đậm không
        SimpleAttributeSet style2 = new SimpleAttributeSet();
        StyleConstants.setBold(style2, true);
        try {
            // Thêm văn bản vào document, có thể in đậm từng từ theo yêu cầu
            doc.insertString(doc.getLength(), "                         T-SHIRT BEE SHOP\n \n", style);
            doc.insertString(doc.getLength(), content, null);
            doc.insertString(doc.getLength(), bangSanPham, style2);
            doc.insertString(doc.getLength(), sanPham, null);
            doc.insertString(doc.getLength(), thongTinTien, null);
            doc.insertString(doc.getLength(), "Cảm ơn quý khách đã mua hàng\n \n", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        jTextArea1.setCaretPosition(0);
//            DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
//            for (int i = 0; i < jTable1.getRowCount(); i++) {
//                
//                String name = df.getValueAt(i, 0).toString();
//                String qt = df.getValueAt(i, 1).toString();
//                String prc = df.getValueAt(i, 2).toString();
//                
//                bill.setText(bill.getText() + name+"\t"+qt+"\t"+prc+" \n");
//                
//            }
//            jTextArea1.setText(jTextArea1.getText() + "----------------------------------------------------------------\n");
//            jTextArea1.setText(jTextArea1.getText() + "SubTotal :\t"+Too.getText()+"\n");
//            jTextArea1.setText(jTextArea1.getText() + "Cash :\t"+Cash.getText()+"\n");
//            jTextArea1.setText(jTextArea1.getText() + "Ballance :\t"+Bal.getText()+"\n");
//            jTextArea1.setText(jTextArea1.getText() + "====================================\n");
//            jTextArea1.setText(jTextArea1.getText() +"                     Thanks For Your Business...!"+"\n");
//            jTextArea1.setText(jTextArea1.getText() + "----------------------------------------------------------------\n");
//            jTextArea1.setText(jTextArea1.getText() +"                     Software by Techinbox"+"\n");
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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTextArea1.setEditable(false);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton1.setText("Ok");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Lưu hóa đơn");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            jTextArea1.print();
        } catch (PrinterException ex) {
            Logger.getLogger(PrintBill.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(PrintBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(PrintBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(PrintBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(PrintBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                PrintBill dialog = new PrintBill(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane jTextArea1;
    // End of variables declaration//GEN-END:variables
}
