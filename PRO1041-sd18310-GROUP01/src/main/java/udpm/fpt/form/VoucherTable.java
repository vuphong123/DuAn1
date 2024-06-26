/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package udpm.fpt.form;

import com.raven.datechooser.DateChooser;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import udpm.fpt.component.Notification;
import udpm.fpt.main.Main;
import udpm.fpt.model.KhachHang;
import udpm.fpt.model.MAUSAC;
import udpm.fpt.model.User;
import udpm.fpt.model.Voucher;
import udpm.fpt.service.KhachHangService;
import udpm.fpt.service.VoucherService;
import udpm.fpt.swing.NumberOnlyFilter;
import udpm.fpt.swing.table.TableCustom;
import udpm.serviceDelivery.MailSender;
import udpm.serviceDelivery.NumberFilter;

/**
 *
 * @author Thanh Dat
 */
public class VoucherTable extends javax.swing.JPanel {

    private KhachHangService svkh = new KhachHangService();
    MailSender mail = new MailSender();
    private VoucherService vcs = new VoucherService();
    DefaultTableModel dtm = new DefaultTableModel();
    private DateChooser dcNgayBatDau = new DateChooser();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private User user;
    private DateChooser dcNgayKetThuc = new DateChooser();
    private DateChooser dcNgayTao = new DateChooser();
    private DateChooser dcNgayCapNhat = new DateChooser();
    private DateChooser dcTimKiem = new DateChooser();
    private List<Voucher> vc = new ArrayList<>();
    private List<KhachHang> listKH = new ArrayList<>();
    Main main;

    public VoucherTable(User user) {
        this.user = user;
        initComponents();
        TableCustom.apply(jScrollPane1, TableCustom.TableType.DEFAULT);
        //Ngày bắt đầu
        dcNgayBatDau.setTextField(txtNgayBatDau);
        dcNgayBatDau.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        dcNgayBatDau.setLabelCurrentDayVisible(false);
        dcNgayBatDau.setDateFormat(sdf);
        //Ngày kết thúc
        dcNgayKetThuc.setTextField(txtNgayKetThuc);
        dcNgayKetThuc.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        dcNgayKetThuc.setLabelCurrentDayVisible(false);
        dcNgayKetThuc.setDateFormat(sdf);
        //Ngày tạo
        dcNgayTao.setTextField(txtNgayTao);
        dcNgayTao.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        dcNgayTao.setLabelCurrentDayVisible(false);
        dcNgayTao.setDateFormat(sdf);
        //Tìm kiếm
        dcTimKiem.setTextField(txtTimKiem);
        dcTimKiem.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        dcTimKiem.setLabelCurrentDayVisible(false);
        dcTimKiem.setDateFormat(sdf);
        ((AbstractDocument) txtDieuKienGiam.getDocument()).setDocumentFilter(new NumberFilter());
        ((AbstractDocument) txtPhanTramGiam.getDocument()).setDocumentFilter(new NumberFilter());
        loadTable();
        DataAndFillKhachHang();
    }

    public void loadTable() {
        this.dtm = (DefaultTableModel) tblVoucher.getModel();
        dtm.setRowCount(0);
        for (Voucher voucher : this.vcs.getAll()) {
            this.vc.add(voucher);
            Object[] ob = {
                voucher.getId(),
                voucher.getTenPhieu(),
                voucher.getDieuKienGiam(),
                sdf.format(voucher.getNgayBatDau()),
                sdf.format(voucher.getNgayKetThuc()),
                sdf.format(voucher.getNgayTao()),
                voucher.getSoLuong(),
                trangThai(voucher.getTrangThai()),
                voucher.getPhamTramGiam(),};
            dtm.addRow(ob);
        }
    }

    public Voucher getVoucher() {
        Voucher vc = new Voucher();
        vc.setTenPhieu(txtTenPhieu.getText());
        vc.setDieuKienGiam(Double.parseDouble(txtDieuKienGiam.getText()));
        try {
            vc.setNgayBatDau(sdf.parse(txtNgayBatDau.getText()));
        } catch (Exception ex) {
            System.err.println("Err" + ex);
        }
        try {
            vc.setNgayKetThuc(sdf.parse(txtNgayKetThuc.getText()));
        } catch (Exception ex) {
            System.err.println("Err" + ex);
        }
        try {
            vc.setNgayTao(sdf.parse(txtNgayTao.getText()));
        } catch (Exception ex) {
            System.err.println("Err" + ex);
        }
        LocalDate date1 = LocalDate.parse(txtNgayBatDau.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate date2 = LocalDate.parse(txtNgayKetThuc.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate date3 = LocalDate.parse(sdf.format(new Date()), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        if (date1.isAfter(date3)) {
            vc.setTrangThai(2);
        } else if (date3.isAfter(date2)) {
            vc.setTrangThai(3);
        } else if (date1.isBefore(date3) && date2.isAfter(date3) || date1.isEqual(date3) || date2.isEqual(date3)) {
            vc.setTrangThai(1);
        }
        vc.setSoLuong(Integer.valueOf(txtSoLuong.getText()));
        vc.setPhamTramGiam(Double.parseDouble(txtPhanTramGiam.getText()));
        return vc;
    }

    public Voucher updateVoucher() {
        int row = tblVoucher.getSelectedRow();
        Voucher vc = new Voucher();
        vc.setTenPhieu(txtTenPhieu.getText());
        vc.setDieuKienGiam(Double.parseDouble(txtDieuKienGiam.getText()));
        try {
            vc.setNgayBatDau(sdf.parse(txtNgayBatDau.getText()));
        } catch (Exception ex) {
            System.err.println("Err" + ex);
        }
        try {
            vc.setNgayKetThuc(sdf.parse(txtNgayKetThuc.getText()));
        } catch (Exception ex) {
            System.err.println("Err" + ex);
        }
        try {
            vc.setNgayTao(sdf.parse(txtNgayTao.getText()));
        } catch (Exception ex) {
            System.err.println("Err" + ex);
        }
        vc.setPhamTramGiam(Double.parseDouble(txtPhanTramGiam.getText()));
        return vc;
    }

    public boolean checkinfor() {
        if (txtTenPhieu.getText().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Không được để chống thông tin").showNotification();
            return false;
        } else if (txtDieuKienGiam.getText().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Không được để chống thông tin").showNotification();
            return false;
        } else if (txtPhanTramGiam.getText().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Không được để chống thông tin").showNotification();
            return false;
        } else if (Double.parseDouble(txtPhanTramGiam.getText()) > 25) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Không thể giảm quá 25%").showNotification();
            return false;
        }
        for (Voucher vccc : vc) {
            if (vccc.getTenPhieu().equalsIgnoreCase(txtTenPhieu.getText())) {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Phiếu giảm giá này đã tồn tại vui lòng nhập tên khác").showNotification();
                txtTenPhieu.requestFocus();
                return false;
            }
        }
        LocalDate date1 = LocalDate.parse(txtNgayBatDau.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate date2 = LocalDate.parse(txtNgayKetThuc.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate date3 = LocalDate.parse(sdf.format(new Date()), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate date2PlusOneDay = date2.plusDays(1);
        if (date1.isBefore(date3)) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Ngày bắt dầu không thể nhỏ hơn ngày hôn nay").showNotification();
            return false;
        }
        if (date2.isBefore(date1)) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Ngày kết thúc không thể nhỏ hơn ngày bắt đầu").showNotification();
            return false;
        }
        return true;
    }

    public String trangThai(int trangThai) {
        if (trangThai == 1) {
            return "Đang diễn ra";
        } else if (trangThai == 2) {
            return "Chưa bắt đầu";
        } else {
            return "đã kết thúc";
        }
    }

    public void tableSearch() {
        this.dtm = (DefaultTableModel) tblVoucher.getModel();
        dtm.setRowCount(0);
        this.vc.clear();
        try {
            for (Voucher voucher : this.vcs.Search(sdf.parse(txtTimKiem.getText()))) {
                this.vc.add(voucher);
                Object[] ob = {
                    voucher.getId(),
                    voucher.getTenPhieu(),
                    voucher.getDieuKienGiam(),
                    sdf.format(voucher.getNgayBatDau()),
                    sdf.format(voucher.getNgayKetThuc()),
                    sdf.format(voucher.getNgayTao()),
                    voucher.getSoLuong(),
                    voucher.getTrangThai(),
                    voucher.getPhamTramGiam(),};
                dtm.addRow(ob);
            }
        } catch (Exception ex) {
            Logger.getLogger(Voucher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void DataAndFillKhachHang() {
        CompletableFuture<List<KhachHang>> future = this.svkh.finAllKhachHang();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                listKH = data;
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    /**
     * ;
     * v
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtTenPhieu = new udpm.fpt.swing.TextField();
        txtNgayBatDau = new udpm.fpt.swing.TextField();
        txtNgayKetThuc = new udpm.fpt.swing.TextField();
        txtNgayTao = new udpm.fpt.swing.TextField();
        txtSoLuong = new udpm.fpt.swing.TextField();
        txtPhanTramGiam = new udpm.fpt.swing.TextField();
        btnAdd = new udpm.fpt.swing.Button();
        btnUpdate = new udpm.fpt.swing.Button();
        btnDelete = new udpm.fpt.swing.Button();
        btnClear = new udpm.fpt.swing.Button();
        btnReset = new udpm.fpt.swing.Button();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVoucher = new javax.swing.JTable();
        txtTimKiem = new udpm.fpt.swing.TextField();
        btnTimKiem = new udpm.fpt.swing.Button();
        txtDieuKienGiam = new udpm.fpt.swing.TextField();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 204, 204));
        jLabel1.setText("Phiếu giảm giá");

        txtTenPhieu.setLabelText("Tên phiếu");

        txtNgayBatDau.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtNgayBatDau.setLabelText("Ngày bắt đầu\n");

        txtNgayKetThuc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtNgayKetThuc.setLabelText("Ngày kết thúc");

        txtNgayTao.setEditable(false);
        txtNgayTao.setLabelText("Ngày Tạo");

        txtSoLuong.setLabelText("Số lượng");

        txtPhanTramGiam.setLabelText("Phần Trăm Giảm %");

        btnAdd.setBackground(new java.awt.Color(0, 204, 204));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(51, 255, 255));
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(51, 255, 255));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(51, 255, 255));
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(51, 255, 255));
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("Reset Table");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        tblVoucher.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên phiếu", "Điều kiện giảm", "Ngày bắt đầu", "Ngày kết thúc", "Ngày tạo", "Số lượng", "Trạng thai", "Phần trăm giảm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVoucher.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblVoucher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVoucherMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblVoucher);

        txtTimKiem.setLabelText("Tìm theo ngày kết thúc\n");

        btnTimKiem.setBackground(new java.awt.Color(51, 255, 255));
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        txtDieuKienGiam.setLabelText("Điều kiện giảm cho sản phẩm");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(625, 625, 625)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTenPhieu, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                            .addComponent(txtNgayBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDieuKienGiam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 248, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayTao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(223, 223, 223)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPhanTramGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtPhanTramGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(91, 91, 91)
                                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtDieuKienGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(15, 15, 15))
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (this.checkinfor()) {
            Voucher vcher = getVoucher();
            if (vcs.addNew(vcher)) {
                vc.add(vcher);
                new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                        "Thêm thành công").showNotification();
                List<String> listMail = new ArrayList<>();
                for (KhachHang kh : listKH) {
                    if (kh.getGmail() != null) {
                        listMail.add(kh.getGmail());
                    }
                }
                NumberFormat numberFormat = NumberFormat.getInstance();
                String message ="Voucher: " + vcher.getTenPhieu() + " được tạo ra <br>"
                        +"Từ ngày " + sdf.format(vcher.getNgayBatDau()) + " đến ngày " + sdf.format(vcher.getNgayKetThuc()) + "<br>"
                        +"Với số lượng: " + vcher.getSoLuong() + "<br>"
                        +"Áp dụng cho hóa đơn từ: " + numberFormat.format(vcher.getDieuKienGiam()) + " VND <br>" 
                        +"Với lượng giảm lên tới: " + numberFormat.format(vcher.getPhamTramGiam()) + " % <br>"
                        +"Nhanh chân nhanh chân tới T-SHIRT BEE sử dụng ngay";
                new Thread(() -> {
                    mail.sendMail(message, listMail);
                }).start();
            } else {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Thêm thất bại").showNotification();
            }
            this.loadTable();
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int row = tblVoucher.getSelectedRow();
        if (row < 0) {
            return;
        }
        String maVouCher = tblVoucher.getValueAt(row, 0).toString();
        Voucher vcher = updateVoucher();
        vcher.setTrangThai(4);
        vcher.setId(Integer.valueOf(maVouCher));
        if (vcs.updateVoucher(vcher)) {
            new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                    "thành công").showNotification();
        } else {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "thất bại").showNotification();
        }
        this.loadTable();

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int row = tblVoucher.getSelectedRow();
        if (row < 0) {
            return;
        }
        if (this.checkinfor()) {
            Voucher vcher = updateVoucher();
            if (vcs.deletedVoucher(vcher.getId())) {
                new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                        "Update thành công").showNotification();
            } else {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Updaet thất bại").showNotification();
            }
            this.loadTable();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        txtTenPhieu.setText("");
        txtDieuKienGiam.setText("");
        txtNgayBatDau.setText("");
        txtNgayKetThuc.setText("");
        txtNgayTao.setText("");
        txtSoLuong.setText("");
        txtPhanTramGiam.setText("");
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        loadTable();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        tableSearch();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked

    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void tblVoucherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVoucherMouseClicked
        int row = tblVoucher.getSelectedRow();
        if (row == -1) {
            return;
        }
        Voucher vcher = this.vc.get(row);
        txtTenPhieu.setText(vcher.getTenPhieu());
        txtDieuKienGiam.setText(String.valueOf(vcher.getDieuKienGiam()));
        txtNgayBatDau.setText(sdf.format(vcher.getNgayBatDau()));
        txtNgayKetThuc.setText(sdf.format(vcher.getNgayKetThuc()));
        txtNgayTao.setText(sdf.format(vcher.getNgayTao()));
        txtSoLuong.setText(String.valueOf(vcher.getSoLuong()));
        txtPhanTramGiam.setText(String.valueOf(vcher.getPhamTramGiam()));
    }//GEN-LAST:event_tblVoucherMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.Button btnAdd;
    private udpm.fpt.swing.Button btnClear;
    private udpm.fpt.swing.Button btnDelete;
    private udpm.fpt.swing.Button btnReset;
    private udpm.fpt.swing.Button btnTimKiem;
    private udpm.fpt.swing.Button btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblVoucher;
    private udpm.fpt.swing.TextField txtDieuKienGiam;
    private udpm.fpt.swing.TextField txtNgayBatDau;
    private udpm.fpt.swing.TextField txtNgayKetThuc;
    private udpm.fpt.swing.TextField txtNgayTao;
    private udpm.fpt.swing.TextField txtPhanTramGiam;
    private udpm.fpt.swing.TextField txtSoLuong;
    private udpm.fpt.swing.TextField txtTenPhieu;
    private udpm.fpt.swing.TextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
