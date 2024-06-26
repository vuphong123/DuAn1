package udpm.fpt.form;

import com.raven.datechooser.DateBetween;
import com.raven.datechooser.DateChooser;
import com.raven.datechooser.listener.DateChooserAction;
import com.raven.datechooser.listener.DateChooserAdapter;
import com.sun.jdi.IntegerValue;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import udpm.fpt.component.Notification;
import udpm.fpt.main.Main;
import udpm.fpt.model.BILL;
import udpm.fpt.model.BILLCHITIET;
import udpm.fpt.model.SIZE;
import udpm.fpt.model.User;
import udpm.fpt.service.HistoryService;

/**
 *
 * @author NONG HOANG VU
 */
public class History extends javax.swing.JPanel {

    private User user;
    private Main main;
    private HistoryService hsv = new HistoryService();
    private List<BILL> listBill = new ArrayList<>();
    private List<BILLCHITIET> listBillChiTiet = new ArrayList<>();
    private SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat simpleSql = new SimpleDateFormat("yyyy-MM-dd");
    private DateChooser dcNgayBatDau = new DateChooser();
    public History(User user, Main main) {
        initComponents();
        this.user = user;
        this.main = main;
        loadDataAndFillBILL();
        loadDataAndFillBILLChiTiet();
        dateChooser();
        cbbSize();
    }

    public void loadDataAndFillBILL() {
        CompletableFuture<List<BILL>> future = (CompletableFuture<List<BILL>>) this.hsv.findAllBill();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                tableHoaDon(data);
                listBill =data;
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }
    
    public void cbbSize() {
        DefaultComboBoxModel dd = (DefaultComboBoxModel) cbbTrangThai.getModel();
        dd.removeAllElements();
        dd.addElement("");
        dd.addElement("Chưa thanh toán");
        dd.addElement("Đã thanh toán");
        dd.addElement("Bị hủy");
    }
    
    public void loadDataAndFillBILLChiTiet() {
        CompletableFuture<List<BILLCHITIET>> future = this.hsv.findAllBillChiTiet();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                listBillChiTiet = data;
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }
    public void SearchHoaDon(Date timKiem, Date timKiem2, String trangThai) {
        CompletableFuture<List<BILL>> future = this.hsv.findAllBillWhereNgayAndTrangThai(timKiem, timKiem2, trangThai);
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                tableHoaDon(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }
    public void SearchHoaDonTrangThai(String trangThai) {
        CompletableFuture<List<BILL>> future = this.hsv.findAllBillWhereTrangThai(trangThai);
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                tableHoaDon(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    public void tableHoaDon(List<BILL> listbill) {
        DefaultTableModel model = (DefaultTableModel) tbjjjj.getModel();
        model.setRowCount(0);
        for (BILL b : listbill) {
            Object[] rowData
                    = {
                        b.getId(),
                        b.getId_NhanVien().getId(),
                        b.getId_KhachHang().getId()==1 ? "" : b.getId_KhachHang().getId(),
                        b.getTongSanPham(),
                        b.getTongTien(),
                        simple.format(b.getNgayTao()),
                        b.getTrangThai(),
                        b.getTongTienSauKhiGiam()==null? "" : b.getTongTienSauKhiGiam()};
            model.addRow(rowData);
        }
    }

    public void tableHoaDonChiTiet(String idHoaDon) {
        DefaultTableModel model = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        model.setRowCount(0);
        for (BILLCHITIET b : listBillChiTiet) {
            if (b.getIdBill().getId().equals(idHoaDon)) {
                Object[] rowData
                        = {
                            b.getId(),
                            b.getIdBill().getId(),
                            b.getIdSanPhamChiTiet().getId_SanPham().getTenSanPham(),
                            b.getSoLuongDaMua(),
                            b.getGiaTienHienTai(),
                            b.getThanhTien(),};
                model.addRow(rowData);
            }
        }
    }
    
    public void dateChooser() {
        dcNgayBatDau.setTextField(txtNgayBatDau);
        dcNgayBatDau.setDateSelectionMode(DateChooser.DateSelectionMode.BETWEEN_DATE_SELECTED);
        dcNgayBatDau.setLabelCurrentDayVisible(false);
        dcNgayBatDau.setDateFormat(simple);
        dcNgayBatDau.addActionDateChooserListener(new DateChooserAdapter() {
            @Override
            public void dateBetweenChanged(DateBetween date, DateChooserAction action) {
                SearchHoaDon(date.getFromDate(), date.getToDate(), "");
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbbTrangThai = new udpm.fpt.swing.Combobox();
        txtNgayBatDau = new udpm.fpt.swing.TextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDonChiTiet = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        buttonMessage1 = new udpm.fpt.swing.ButtonMessage();
        buttonMessage2 = new udpm.fpt.swing.ButtonMessage();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbjjjj = new javax.swing.JTable();
        buttonMessage3 = new udpm.fpt.swing.ButtonMessage();

        setBackground(new java.awt.Color(255, 255, 255));

        cbbTrangThai.setLabeText("Trạng thái");
        cbbTrangThai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbTrangThaiItemStateChanged(evt);
            }
        });
        cbbTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTrangThaiActionPerformed(evt);
            }
        });

        txtNgayBatDau.setLabelText("Ngày bắt đầu");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HDCT", "Mã HD", "Tên sản phẩm", "Số lượng đã mua", "Đơn giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblHoaDonChiTiet);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Hóa đơn chi tiết");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Danh sách hóa đơn");

        buttonMessage1.setText("Xuất danh sách hóa đơn");
        buttonMessage1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMessage1ActionPerformed(evt);
            }
        });

        buttonMessage2.setText("Xem hóa đơn chi tiết");
        buttonMessage2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMessage2ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbjjjj.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Mã nhân viên", "Mã khách hàng", "Số lượng sản phẩm", "Tổng tiền", "Ngày tạo", "Trạng thái", "Tổng tiền sau khi giảm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbjjjj.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbjjjjMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbjjjj);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1425, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1401, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        buttonMessage3.setText("Đặt lại");
        buttonMessage3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMessage3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(37, 37, 37)
                                    .addComponent(txtNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(30, 30, 30)
                                    .addComponent(buttonMessage3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(buttonMessage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(buttonMessage2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(215, 215, 215))
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap(24, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(buttonMessage3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonMessage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonMessage2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbbTrangThaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbTrangThaiItemStateChanged

    }//GEN-LAST:event_cbbTrangThaiItemStateChanged

    private void buttonMessage2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMessage2ActionPerformed
        int row = tbjjjj.getSelectedRow();
        if (row == -1) {
            return;
        }
        String maHoaDon = tbjjjj.getValueAt(row, 0).toString();
        String maKhacHang = tbjjjj.getValueAt(row, 2).toString();
        String trangThai = tbjjjj.getValueAt(row, 6).toString();
        if(trangThai.equals("Chưa thanh toán") || trangThai.equals("Bị hủy")){
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                                "Chỉ sử dụng được với hóa đơn đã thành công!").showNotification();
            return;
        }
        PrintBill inHoaDon = new PrintBill(main, true, listBill, listBillChiTiet, maHoaDon, maKhacHang);
        inHoaDon.setVisible(true);
    }//GEN-LAST:event_buttonMessage2ActionPerformed

    private void tbjjjjMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbjjjjMouseClicked
        int row = tbjjjj.getSelectedRow();
        if (row == -1) {
            return;
        }
        String idHoaDon = tbjjjj.getValueAt(row, 0).toString();
        System.out.println(idHoaDon);
        tableHoaDonChiTiet(idHoaDon);
    }//GEN-LAST:event_tbjjjjMouseClicked

    private void buttonMessage1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMessage1ActionPerformed
         try
        {
        //Trang 1
        XSSFWorkbook wordkbook = new XSSFWorkbook();
        XSSFSheet sheet1 =wordkbook.createSheet("Hóa đơn");
        XSSFRow row =null;
        Cell cell=null;
        row=sheet1.createRow(2);
        cell=row.createCell(0,CellType.STRING);
        cell.setCellValue("DANH SACH HÓA ĐƠN");
        
        row=sheet1.createRow(3);
        cell=row.createCell(0,CellType.STRING);
        cell.setCellValue("STT");
        
        cell=row.createCell(1,CellType.STRING);
        cell.setCellValue("MÃ HÓA ĐƠN");
        
        cell=row.createCell(2,CellType.STRING);
        cell.setCellValue("MÃ NHÂN VIÊN");
        
        cell=row.createCell(3,CellType.STRING);
        cell.setCellValue("MÃ KHÁCH HÀNG");
        
        cell=row.createCell(4,CellType.STRING);
        cell.setCellValue("SỐ LƯỢNG SẢN PHẨM");
        
        cell=row.createCell(5,CellType.STRING);
        cell.setCellValue("TỔNG TIỀN");
        
        cell=row.createCell(6,CellType.STRING);
        cell.setCellValue("NGÀY TẠO");
        
        cell=row.createCell(7,CellType.STRING);
        cell.setCellValue("TRẠNG THÁI");
        
        cell=row.createCell(8,CellType.STRING);
        cell.setCellValue("TỔNG TIỀN SAU KHI GIẢM");
        
        for(int i=0; i<listBill.size(); i++)
        {
            //Modelbook book =arr.get(i);
            row=sheet1.createRow(5+i);
           
            cell=row.createCell(0,CellType.STRING);
            cell.setCellValue(i+1);
            
            cell=row.createCell(1,CellType.STRING);
            cell.setCellValue(listBill.get(i).getId());
                 
            cell=row.createCell(2,CellType.STRING);
            cell.setCellValue(listBill.get(i).getId_NhanVien()==null ? "" : String.valueOf(listBill.get(i).getId_NhanVien().getId()));
            
            cell=row.createCell(3,CellType.STRING);
            cell.setCellValue(listBill.get(i).getId_KhachHang()==null ? "" : String.valueOf(listBill.get(i).getId_KhachHang().getId()));
            
            cell=row.createCell(4,CellType.STRING);
            cell.setCellValue(listBill.get(i).getTongSanPham());
            
            cell=row.createCell(5,CellType.STRING);
            cell.setCellValue(String.valueOf(listBill.get(i).getTongTien()).trim().isEmpty()? "" : String.valueOf(listBill.get(i).getTongTien()));
            
            cell=row.createCell(6,CellType.STRING);
            cell.setCellValue(simple.format(listBill.get(i).getNgayTao()));
            
            cell=row.createCell(7,CellType.STRING);
            cell.setCellValue(listBill.get(i).getTrangThai());
            
            cell=row.createCell(8,CellType.STRING);
            cell.setCellValue(String.valueOf(listBill.get(i).getTongTienSauKhiGiam()).trim().isEmpty()? "" : String.valueOf(listBill.get(i).getTongTienSauKhiGiam()));
                                  
        }
        
        XSSFSheet sheet2 =wordkbook.createSheet("Hóa đơn chi tiết");
        XSSFRow row2 =null;
        Cell cell2=null;
        row2=sheet2.createRow(2);
        cell2=row2.createCell(0,CellType.STRING);
        cell2.setCellValue("DANH SACH HÓA ĐƠN CHI TIẾT");
        
        row2=sheet2.createRow(3);
        cell2=row2.createCell(0,CellType.STRING);
        cell2.setCellValue("STT");
        
        cell2=row2.createCell(1,CellType.STRING);
        cell2.setCellValue("MHDCT");
        
        cell2=row2.createCell(2,CellType.STRING);
        cell2.setCellValue("MÃ HÓA ĐƠN");
        
        cell2=row2.createCell(3,CellType.STRING);
        cell2.setCellValue("TÊN SẢN PHẨM");
        
        cell2=row2.createCell(4,CellType.STRING);
        cell2.setCellValue("SỐ LƯỢNG ĐÃ MUA");
        
        cell2=row2.createCell(5,CellType.STRING);
        cell2.setCellValue("ĐƠN GIÁ");
        
        cell2=row2.createCell(6,CellType.STRING);
        cell2.setCellValue("THÀNH TIỀN");
       
        for(int i=0; i<listBillChiTiet.size(); i++)
        {
            //Modelbook book =arr.get(i);
            row2=sheet2.createRow(5+i);
           
            cell2=row2.createCell(0,CellType.STRING);
            cell2.setCellValue(i+1);
            
            cell2=row2.createCell(1,CellType.STRING);
            cell2.setCellValue(listBillChiTiet.get(i).getId());
                 
            cell2=row2.createCell(2,CellType.STRING);
            cell2.setCellValue(listBillChiTiet.get(i).getIdBill().getId());
            
            cell2=row2.createCell(3,CellType.STRING);
            cell2.setCellValue(listBillChiTiet.get(i).getIdSanPhamChiTiet().getId_SanPham().getTenSanPham());
            
            cell2=row2.createCell(4,CellType.STRING);
            cell2.setCellValue(listBillChiTiet.get(i).getSoLuongDaMua());
            
            cell2=row2.createCell(5,CellType.STRING);
            cell2.setCellValue(listBillChiTiet.get(i).getGiaTienHienTai());
            
            cell2=row2.createCell(6,CellType.STRING);
            cell2.setCellValue(listBillChiTiet.get(i).getThanhTien());
                                  
        }
        
        File f = new File("D://danhsach.xlsx");
        try 
        {
            FileOutputStream fis = new FileOutputStream(f);
            wordkbook.write(fis);
            fis.close();
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
       
        }
        catch (IOException ex)
        {
          ex.printStackTrace();
        }
        
        
        JOptionPane.showMessageDialog(this, "in thanh cong D:\\danhsach");
        
        }
        
        
        catch(Exception ex)
        {
            ex.printStackTrace();
              JOptionPane.showMessageDialog(this, "Loi mo file");
        }
    }//GEN-LAST:event_buttonMessage1ActionPerformed

    private void buttonMessage3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMessage3ActionPerformed
        loadDataAndFillBILL();
    }//GEN-LAST:event_buttonMessage3ActionPerformed

    private void cbbTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTrangThaiActionPerformed
        String trangThai = cbbTrangThai.getSelectedItem().toString();
        if(cbbTrangThai.getSelectedIndex()>0){
           SearchHoaDonTrangThai(trangThai);
        }
    }//GEN-LAST:event_cbbTrangThaiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.ButtonMessage buttonMessage1;
    private udpm.fpt.swing.ButtonMessage buttonMessage2;
    private udpm.fpt.swing.ButtonMessage buttonMessage3;
    private udpm.fpt.swing.Combobox cbbTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbjjjj;
    private javax.swing.JTable tblHoaDonChiTiet;
    private udpm.fpt.swing.TextField txtNgayBatDau;
    // End of variables declaration//GEN-END:variables
}
