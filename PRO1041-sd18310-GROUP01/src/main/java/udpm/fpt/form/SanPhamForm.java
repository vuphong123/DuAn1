/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package udpm.fpt.form;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import udpm.fpt.component.Notification;
import udpm.fpt.main.Main;
import udpm.fpt.model.ChatLieu;
import udpm.fpt.model.KieuDang;
import udpm.fpt.model.MAUSAC;
import udpm.fpt.model.SANPHAM;
import udpm.fpt.model.SANPHAMCHITIET;
import udpm.fpt.model.SIZE;
import udpm.fpt.service.SanPhamService;
import udpm.fpt.swing.table.TableCustom;

/**
 *
 * @author PHONG PC
 */
public class SanPhamForm extends javax.swing.JPanel {

    private Main main;
    private List<SANPHAM> listSanPham = new ArrayList<>();
    private List<SANPHAMCHITIET> listSanPhamChiTiet = new ArrayList<>();
    private List<MAUSAC> listMausac = new ArrayList<>();
    private List<SIZE> listSize = new ArrayList<>();
    private List<ChatLieu> listChatLieu = new ArrayList<>();
    private List<KieuDang> listKieuDang = new ArrayList<>();
    SanPhamService svsp = new SanPhamService();
    private DefaultTableModel model;

    /**
     * Creates new form SanPhamForm
     */
    public SanPhamForm(Main main) {
        initComponents();
        loadDataAndFillSanPham();
        loadDataAndFillSanPhamHoatDong();
        loadDataAndFillSanPhamChiTiet();
        loadDataAndFillMauSac();
        loadDataAndFillSize();
        loadDataAndFillChatLieu();
        loadDataAndFillKieuDang();
        this.main = main;
        TableCustom.apply(jScrollPane3, TableCustom.TableType.DEFAULT);
        TableCustom.apply(jScrollPane2, TableCustom.TableType.DEFAULT);
        TableCustom.apply(jScrollPane1, TableCustom.TableType.DEFAULT);
    }

    //*
    //*
    //*
    //SanPham
    public void loadSanPham(List<SANPHAM> listSP) {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        for (SANPHAM sp : listSP) {
            Object[] rowData = {
                sp.getId(),
                sp.getTenSanPham(),
                sp.getSoLuong(),
                sp.getTrangThai()
            };
            model.addRow(rowData);
        }
    }

    public void loadDataAndFillSanPham() {
        CompletableFuture<List<SANPHAM>> future = this.svsp.findALlSanPham();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                loadSanPham(data);
                listSanPham = data;
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    public void loadDataAndFillSanPhamHoatDong() {
        CompletableFuture<List<SANPHAM>> future = this.svsp.findSanPhamHoatDong();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                cbbSanPham(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    public void cbbSanPham(List<SANPHAM> lms) {
        DefaultComboBoxModel cbbMauSac = (DefaultComboBoxModel) this.cbbTenSanPham.getModel();
        cbbMauSac.removeAllElements();
        for (SANPHAM ms : lms) {
            cbbMauSac.addElement(ms.getTenSanPham());
        }
    }

    public boolean check() {
        String tenSanPham = txtTenSanPham.getText();
        String nhanHieu = txtIDSanPham.getText();
        if (tenSanPham.trim().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Đừng để trống tên sản phẩm!").showNotification();
            return false;
        }
        for (SANPHAM sp : listSanPham) {
            if (sp.getTenSanPham().equals(tenSanPham)) {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Sản phẩm đã tồn tại!").showNotification();
                return false;
            }
        }
        return true;
    }

    public boolean checkUpdate() {
        String tenSanPham = txtTenSanPham.getText();
        String nhanHieu = txtIDSanPham.getText();
        if (tenSanPham.trim().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Đừng để trống tên sản phẩm!").showNotification();
            return false;
        }
        return true;
    }

    //*
    //*
    //*    
    //SanPhamChiTiet
    public void loadSanPhamChiTiet(List<SANPHAMCHITIET> listSP) {
        DefaultTableModel model = (DefaultTableModel) tblSanPhamChiTiet.getModel();
        model.setRowCount(0);
        for (SANPHAMCHITIET sp : listSP) {
            Object[] rowData = {
                sp.getId(),
                sp.getId_SanPham().getTenSanPham(),
                sp.getId_MauSac().getTenMauSac(),
                sp.getId_Size().getTenSize(),
                sp.getId_ChatLieu() == null ? "" : sp.getId_ChatLieu().getTenVai(),
                sp.getId_KieuDang() == null ? "" : sp.getId_KieuDang().getTenKieuDang(),
                sp.getSoLuong(),
                sp.getGiaSanPham(),
                sp.getTrangThai() == true ? "Hoạt động" : "Ngừng hoạt động",};
            model.addRow(rowData);
        }
    }

    public void loadDataAndFillSanPhamChiTiet() {
        CompletableFuture<List<SANPHAMCHITIET>> future = this.svsp.findALlSanPhamChiTiet();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                loadSanPhamChiTiet(data);
                listSanPhamChiTiet = data;
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    public boolean checkThemSanPhamChiTiet() {
        String giaTien = txtGiaTien.getText();
        String soLuong = txtSoLuong.getText();
        if (giaTien.trim().isEmpty()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Đừng để trống giá tiền!").showNotification();
            return false;
        } else {
            if (giaTien.matches("^10000|[1-9][0-9]{4,}$") == false) {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Giá tiền phải là số và lớn hơn 10000!").showNotification();
                return false;
            }
        }
        if (soLuong.trim().isEmpty()) {
        } else {
            if (soLuong.matches("^(?!0\\d{3})\\d{1,3}$") == false) {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Số lượng phải là sô và không lớn hơn 1000").showNotification();
                return false;
            }
        }
        return true;
    }

    public boolean checkSuaSanPhamChiTiet() {
        String giaTien = txtGiaTien.getText();
        String soLuong = txtSoLuong.getText();
        if (giaTien.trim().isEmpty()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Đừng để trống giá tiền!").showNotification();
            return false;
        } else {
            if (giaTien.matches("^10000|[1-9][0-9]{4,}$") == false) {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Giá tiền phải là số và lớn hơn 10000!").showNotification();
                return false;
            }
        }
        if (soLuong.trim().isEmpty()) {
        } else {
            if (soLuong.matches("^(?!0\\d{3})\\d{1,3}$") == false) {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Số lượng phải là sô và không lớn hơn 1000").showNotification();
                return false;
            }
        }
        return true;
    }

    //*
    //*
    //*
    //Chất liệu
    public void cbbChatLieu(List<ChatLieu> lms) {
        DefaultComboBoxModel cbbMauSac = (DefaultComboBoxModel) this.cbbChatLieu.getModel();
        cbbMauSac.removeAllElements();
        for (ChatLieu ms : lms) {
            cbbMauSac.addElement(ms.getTenVai());
        }
    }

    public void loadDataAndFillChatLieu() {
        CompletableFuture<List<ChatLieu>> future = this.svsp.findALChatLieu();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                cbbChatLieu(data);
                listChatLieu = data;
                tableChatLieu(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    public void tableChatLieu(List<ChatLieu> list) {
        model = (DefaultTableModel) tblThuocTinh.getModel();
        model.setRowCount(0);
        int index = 1;
        for (ChatLieu ms : list) {
            Object[] rowData = {
                index++,
                ms.getId(),
                ms.getTenVai(),};
            model.addRow(rowData);
        }
    }

    public ChatLieu themChatLieu(String txtTenThuocTinh) {
        for (ChatLieu s : listChatLieu) {
            if (txtTenThuocTinh.equals(s.getTenVai())) {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Thuộc tính đã tồn tại!").showNotification();
                return null;
            }
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ChatLieu cl = new ChatLieu();
        cl.setTenVai(txtTenThuocTinh);
        cl.setNgayTao(timestamp);
        return cl;
    }

    //*
    //*
    //*
    // Kiểu dáng
    public void cbbKieuDang(List<KieuDang> lms) {
        DefaultComboBoxModel cbbMauSac = (DefaultComboBoxModel) this.cbbKieuDang.getModel();
        cbbMauSac.removeAllElements();
        for (KieuDang ms : lms) {
            cbbMauSac.addElement(ms.getTenKieuDang());
        }
    }

    public void loadDataAndFillKieuDang() {
        CompletableFuture<List<KieuDang>> future = this.svsp.findAllKieuDang();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                cbbKieuDang(data);
                listKieuDang = data;
                tableKieuDang(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    public void tableKieuDang(List<KieuDang> list) {
        model = (DefaultTableModel) tblThuocTinh.getModel();
        model.setRowCount(0);
        int index = 1;
        for (KieuDang ms : listKieuDang) {
            Object[] rowData = {
                index++,
                ms.getId(),
                ms.getTenKieuDang(),};
            model.addRow(rowData);
        }
    }

    public KieuDang themKieuDang(String txtTenThuocTinh) {
        for (KieuDang s : listKieuDang) {
            if (txtTenThuocTinh.equals(s.getTenKieuDang())) {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Thuộc tính đã tồn tại!").showNotification();
                return null;
            }
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        KieuDang cl = new KieuDang();
        cl.setTenKieuDang(txtTenThuocTinh);
        cl.setNgayTao(timestamp);
        return cl;
    }

    // Màu sắc 
    public void cbbMauSac(List<MAUSAC> lms) {
        DefaultComboBoxModel cbbMauSac = (DefaultComboBoxModel) this.cbbMauSac.getModel();
        cbbMauSac.removeAllElements();
        for (MAUSAC ms : lms) {
            cbbMauSac.addElement(ms.getTenMauSac());
        }
    }

    public void tableMauSac(List<MAUSAC> list) {
        model = (DefaultTableModel) tblThuocTinh.getModel();
        model.setRowCount(0);
        int index = 1;
        for (MAUSAC ms : list) {
            Object[] rowData = {
                index++,
                ms.getId(),
                ms.getTenMauSac(),};
            model.addRow(rowData);
        }
    }

    public void loadDataAndFillMauSac() {
        CompletableFuture<List<MAUSAC>> future = this.svsp.findALlMauSac();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                cbbMauSac(data);
                listMausac = data;
                tableMauSac(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    public MAUSAC themMauSac(String txtTenThuocTinh) {
        for (MAUSAC s : listMausac) {
            if (txtTenThuocTinh.equals(s.getTenMauSac())) {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Thuộc tính đã tồn tại!").showNotification();
                return null;
            }
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        MAUSAC cl = new MAUSAC();
        cl.setTenMauSac(txtTenThuocTinh);
        cl.setNgayTao(timestamp);
        return cl;
    }

    // Size
    public void cbbSize(List<SIZE> lms) {
        DefaultComboBoxModel cbbMauSac = (DefaultComboBoxModel) this.cbbSize.getModel();
        cbbMauSac.removeAllElements();
        for (SIZE ms : lms) {
            cbbMauSac.addElement(ms.getTenSize());
        }
    }

    public void loadDataAndFillSize() {
        CompletableFuture<List<SIZE>> future = this.svsp.findALlSize();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                cbbSize(data);
                listSize = data;
                tableSize(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    public void tableSize(List<SIZE> list) {
        model = (DefaultTableModel) tblThuocTinh.getModel();
        model.setRowCount(0);
        int index = 1;
        for (SIZE ms : list) {
            Object[] rowData = {
                index++,
                ms.getId(),
                ms.getTenSize(),};
            model.addRow(rowData);
        }
    }

    public SIZE themSize(String txtTenThuocTinh) {
        for (SIZE s : listSize) {
            if (txtTenThuocTinh.equals(s.getTenSize())) {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Thuộc tính đã tồn tại!").showNotification();
                return null;
            }
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SIZE cl = new SIZE();
        cl.setTenSize(txtTenThuocTinh);
        cl.setNgayTao(timestamp);
        return cl;
    }

    //*
    //*
    //*    
    //Thuộc tính
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
        jPanel6 = new javax.swing.JPanel();
        txtIDSanPham = new udpm.fpt.swing.TextField();
        txtTenSanPham = new udpm.fpt.swing.TextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnThemSanPham = new udpm.fpt.swing.ButtonMessage();
        btnSuaSanPham = new udpm.fpt.swing.ButtonMessage();
        btnLamMoiSanPham = new udpm.fpt.swing.ButtonMessage();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        textField5 = new udpm.fpt.swing.TextField();
        btnTimKiemSanPham = new udpm.fpt.swing.ButtonMessage();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        textField6 = new udpm.fpt.swing.TextField();
        txtGiaTien = new udpm.fpt.swing.TextField();
        txtSoLuong = new udpm.fpt.swing.TextField();
        cbbTenSanPham = new udpm.fpt.swing.Combobox();
        cbbSize = new udpm.fpt.swing.Combobox();
        cbbMauSac = new udpm.fpt.swing.Combobox();
        cbbChatLieu = new udpm.fpt.swing.Combobox();
        btnThemSanPhamChiTiet = new udpm.fpt.swing.ButtonMessage();
        btnSuaSanPhamChiTiet = new udpm.fpt.swing.ButtonMessage();
        XoaSanPhamChiTiet = new udpm.fpt.swing.ButtonMessage();
        cbbKieuDang = new udpm.fpt.swing.Combobox();
        btnThemSoLuongSanPham = new udpm.fpt.swing.ButtonMessage();
        buttonMessage2 = new udpm.fpt.swing.ButtonMessage();
        buttonMessage3 = new udpm.fpt.swing.ButtonMessage();
        buttonMessage4 = new udpm.fpt.swing.ButtonMessage();
        buttonMessage5 = new udpm.fpt.swing.ButtonMessage();
        buttonMessage6 = new udpm.fpt.swing.ButtonMessage();
        jLabel5 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSanPhamChiTiet = new javax.swing.JTable();
        textField7 = new udpm.fpt.swing.TextField();
        btnTimKiemSanPhamChiTiet = new udpm.fpt.swing.ButtonMessage();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtIdThuocTinh = new udpm.fpt.swing.TextField();
        txtTenThuocTinh = new udpm.fpt.swing.TextField();
        btnThemThuocTinhSanPham = new udpm.fpt.swing.ButtonMessage();
        btnSuaThuocTinhSanPham = new udpm.fpt.swing.ButtonMessage();
        btnLamMoiThuocTinhSanPham = new udpm.fpt.swing.ButtonMessage();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThuocTinh = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        rdoMauSac = new javax.swing.JRadioButton();
        rdoChatLieu = new javax.swing.JRadioButton();
        rdoKieuDang = new javax.swing.JRadioButton();
        rdoSize = new javax.swing.JRadioButton();

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtIDSanPham.setLabelText("Mã sản phẩm");

        txtTenSanPham.setLabelText("Tên sản phẩm");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtIDSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(txtIDSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Thông tin sản phẩm");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnThemSanPham.setBackground(new java.awt.Color(0, 204, 204));
        btnThemSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSanPham.setText("Thêm ");
        btnThemSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSanPhamActionPerformed(evt);
            }
        });

        btnSuaSanPham.setBackground(new java.awt.Color(0, 204, 204));
        btnSuaSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaSanPham.setText("Sửa");
        btnSuaSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSanPhamActionPerformed(evt);
            }
        });

        btnLamMoiSanPham.setBackground(new java.awt.Color(0, 204, 204));
        btnLamMoiSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoiSanPham.setText("Mới");
        btnLamMoiSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLamMoiSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSuaSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThemSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(btnSuaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLamMoiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSanPham);

        textField5.setLabelText("Tìm theo tên, mã sản phẩm");

        btnTimKiemSanPham.setBackground(new java.awt.Color(0, 204, 204));
        btnTimKiemSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiemSanPham.setText("Tìm kiếm");
        btnTimKiemSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(textField5, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTimKiemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textField5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTimKiemSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Danh sách sản phẩm");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(208, 208, 208)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(145, 145, 145)
                                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4)))
                        .addGap(0, 378, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sản phẩm", jPanel2);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        textField6.setLabelText("Mã SPCT");

        txtGiaTien.setLabelText("Giá tiền");

        txtSoLuong.setLabelText("Số lượng");

        cbbTenSanPham.setLabeText("Tên sản phẩm");

        cbbSize.setLabeText("Size");

        cbbMauSac.setLabeText("Màu sắc");

        cbbChatLieu.setLabeText("Chất liệu");

        btnThemSanPhamChiTiet.setBackground(new java.awt.Color(0, 204, 204));
        btnThemSanPhamChiTiet.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSanPhamChiTiet.setText("Thêm sản phẩm mới");
        btnThemSanPhamChiTiet.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemSanPhamChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSanPhamChiTietActionPerformed(evt);
            }
        });

        btnSuaSanPhamChiTiet.setBackground(new java.awt.Color(0, 204, 204));
        btnSuaSanPhamChiTiet.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaSanPhamChiTiet.setText("Sửa");
        btnSuaSanPhamChiTiet.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaSanPhamChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSanPhamChiTietActionPerformed(evt);
            }
        });

        XoaSanPhamChiTiet.setBackground(new java.awt.Color(0, 204, 204));
        XoaSanPhamChiTiet.setForeground(new java.awt.Color(255, 255, 255));
        XoaSanPhamChiTiet.setText("Chỉnh trạng thái");
        XoaSanPhamChiTiet.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        XoaSanPhamChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XoaSanPhamChiTietActionPerformed(evt);
            }
        });

        cbbKieuDang.setLabeText("Kiểu dáng");

        btnThemSoLuongSanPham.setBackground(new java.awt.Color(0, 204, 204));
        btnThemSoLuongSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSoLuongSanPham.setText("Thêm số lượng");
        btnThemSoLuongSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemSoLuongSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSoLuongSanPhamActionPerformed(evt);
            }
        });

        buttonMessage2.setText("+");

        buttonMessage3.setText("+");
        buttonMessage3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMessage3ActionPerformed(evt);
            }
        });

        buttonMessage4.setText("+");

        buttonMessage5.setText("+");

        buttonMessage6.setText("+");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(textField6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbTenSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                    .addComponent(txtGiaTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonMessage6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbbKieuDang, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                    .addComponent(cbbSize, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                    .addComponent(cbbMauSac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbChatLieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(buttonMessage2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThemSanPhamChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(buttonMessage3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSuaSanPhamChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(buttonMessage5, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(buttonMessage4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(XoaSanPhamChiTiet, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThemSoLuongSanPham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(265, 265, 265))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThemSanPhamChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonMessage2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSuaSanPhamChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonMessage3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonMessage6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txtGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(XoaSanPhamChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonMessage4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemSoLuongSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonMessage5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Thông tin sản phẩm");

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblSanPhamChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm ", "Tên sản phẩm", "Màu sắc", "Kích cỡ", "Chất liệu", "Kiểu dáng", "Số lượng", "Đơn giá", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblSanPhamChiTiet);

        textField7.setLabelText("Tên sản phẩm, mã sản phẩm chi tiết, màu sắc, size");

        btnTimKiemSanPhamChiTiet.setBackground(new java.awt.Color(0, 204, 204));
        btnTimKiemSanPhamChiTiet.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiemSanPhamChiTiet.setText("Tìm kiếm");
        btnTimKiemSanPhamChiTiet.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(textField7, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimKiemSanPhamChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textField7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTimKiemSanPhamChiTiet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Danh sách sản phẩm");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sản phẩm chi tiết", jPanel1);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 204, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Thiết lập thuộc tính");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtIdThuocTinh.setEditable(false);
        txtIdThuocTinh.setLabelText("Mã thuộc tính");

        txtTenThuocTinh.setLabelText("Tên thuộc tính");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIdThuocTinh, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(txtTenThuocTinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtIdThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(txtTenThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnThemThuocTinhSanPham.setBackground(new java.awt.Color(0, 84, 166));
        btnThemThuocTinhSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnThemThuocTinhSanPham.setText("Thêm");
        btnThemThuocTinhSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemThuocTinhSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemThuocTinhSanPhamActionPerformed(evt);
            }
        });

        btnSuaThuocTinhSanPham.setBackground(new java.awt.Color(0, 84, 166));
        btnSuaThuocTinhSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaThuocTinhSanPham.setText("Sửa");
        btnSuaThuocTinhSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaThuocTinhSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaThuocTinhSanPhamActionPerformed(evt);
            }
        });

        btnLamMoiThuocTinhSanPham.setBackground(new java.awt.Color(0, 84, 166));
        btnLamMoiThuocTinhSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoiThuocTinhSanPham.setText("Mới");
        btnLamMoiThuocTinhSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoiThuocTinhSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiThuocTinhSanPhamActionPerformed(evt);
            }
        });

        tblThuocTinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã thuộc tính", "Tên thuộc tính"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThuocTinhMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblThuocTinh);

        jLabel2.setText("Danh sách thuộc tính");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        rdoMauSac.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoMauSac);
        rdoMauSac.setSelected(true);
        rdoMauSac.setText("Màu sắc");
        rdoMauSac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoMauSacMouseClicked(evt);
            }
        });

        rdoChatLieu.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoChatLieu);
        rdoChatLieu.setText("Chất liệu");
        rdoChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoChatLieuActionPerformed(evt);
            }
        });

        rdoKieuDang.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoKieuDang);
        rdoKieuDang.setText("Kiểu dáng");
        rdoKieuDang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoKieuDangActionPerformed(evt);
            }
        });

        rdoSize.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoSize);
        rdoSize.setText("Kích cỡ");
        rdoSize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoSizeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(rdoMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(rdoChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoSize, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoMauSac)
                    .addComponent(rdoKieuDang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoChatLieu)
                    .addComponent(rdoSize))
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(142, 142, 142)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(203, 203, 203)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSuaThuocTinhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemThuocTinhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoiThuocTinhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(235, 235, 235))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1416, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnThemThuocTinhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSuaThuocTinhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLamMoiThuocTinhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118))
        );

        jTabbedPane1.addTab("Thuộc tính sản phẩm", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSuaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSanPhamActionPerformed
        int getSelect = tblSanPham.getSelectedRow();
        if (getSelect == -1) {
            return;
        }
        Integer maSanPham = Integer.valueOf(tblSanPham.getValueAt(getSelect, 0).toString());
        int soLuong = Integer.valueOf(tblSanPham.getValueAt(getSelect, 2).toString());
        String trangThai = tblSanPham.getValueAt(getSelect, 3).toString();
        String tenSanPham = txtTenSanPham.getText();
        SANPHAM newSanPham = new SANPHAM();
        for (SANPHAM sp : listSanPham) {
            if (sp.getId().equals(maSanPham)) {
                newSanPham.setNgayTao(sp.getNgayTao());
                break;
            }
        }
        newSanPham.setId(maSanPham);
        newSanPham.setSoLuong(soLuong);
        newSanPham.setTenSanPham(tenSanPham);
        newSanPham.setTrangThai(trangThai);
        FormSuaSanPham suwform = new FormSuaSanPham(main, true, newSanPham, listSanPham, listSanPhamChiTiet);
        suwform.setVisible(true);
        if (suwform.check) {
            new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                    "Thành công!").showNotification();
            loadDataAndFillSanPham();
            loadDataAndFillSanPhamChiTiet();
            loadDataAndFillSanPhamHoatDong();
        }
    }//GEN-LAST:event_btnSuaSanPhamActionPerformed

    private void btnThemSanPhamChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSanPhamChiTietActionPerformed
        String mauSac = cbbMauSac.getSelectedItem().toString();
        String size = cbbSize.getSelectedItem().toString();
        String chatLieu = cbbChatLieu.getSelectedItem().toString();
        String kieuDang = cbbKieuDang.getSelectedItem().toString();
        String tenSanPham = cbbTenSanPham.getSelectedItem().toString();
        String giaSanPham = txtGiaTien.getText();
        String soLuong = txtSoLuong.getText();
        int quatity = 0;
        Integer idSanPham = null;
        boolean check = true;
        if (checkThemSanPhamChiTiet()) {
            SANPHAMCHITIET spct = new SANPHAMCHITIET();
            for (SANPHAM sp : listSanPham) {
                if (sp.getTenSanPham().equals(tenSanPham)) {
                    spct.setId_SanPham(sp);
                    idSanPham = sp.getId();
                    break;
                }
            }
            for (SIZE sp : listSize) {
                if (sp.getTenSize().equals(size)) {
                    spct.setId_Size(sp);
                    break;
                }
            }
            for (MAUSAC sp : listMausac) {
                if (sp.getTenMauSac().equals(mauSac)) {
                    spct.setId_MauSac(sp);
                    break;
                }
            }
            for (KieuDang kd : listKieuDang) {
                if (kd.getTenKieuDang().equals(kieuDang)) {
                    spct.setId_KieuDang(kd);
                    break;
                }
            }
            for (ChatLieu cl : listChatLieu) {
                if (cl.getTenVai().equals(chatLieu)) {
                    spct.setId_ChatLieu(cl);
                    break;
                }
            }
            spct.setGiaSanPham(Double.valueOf(giaSanPham));
            spct.setTrangThai(true);
            if (soLuong.trim().isEmpty()) {
                spct.setSoLuong(quatity);
            } else {
                quatity = Integer.parseInt(soLuong);
                spct.setSoLuong(quatity);
            }
            for (SANPHAMCHITIET listspct : listSanPhamChiTiet) {
                if (Objects.equals(listspct.getGiaSanPham(), Double.valueOf(giaSanPham))
                        && Objects.equals(listspct.getId_MauSac().getId(), spct.getId_MauSac().getId())
                        && Objects.equals(listspct.getId_Size().getId(), spct.getId_Size().getId())
                        && Objects.equals(listspct.getId_ChatLieu().getTenVai(), spct.getId_ChatLieu().getTenVai())
                        && Objects.equals(listspct.getId_KieuDang().getTenKieuDang(), spct.getId_KieuDang().getTenKieuDang())
                        && Objects.equals(listspct.getId_SanPham().getTenSanPham(), spct.getId_SanPham().getTenSanPham())) {
                    new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                            "Sản phẩm với các thuộc tính tương tự đã tồn tại!").showNotification();
                    check = false;
                }
            }
            if (check == true) {
                if (svsp.saveAndUpdateSanPhamChiTiet(spct) != null && svsp.updateSoLuongSanPham(quatity, idSanPham)) {
                    listSanPhamChiTiet.add(spct);
                    new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                            "Thêm thành công!").showNotification();
                }
            }
            loadDataAndFillSanPhamChiTiet();
            loadDataAndFillSanPham();
        }
    }//GEN-LAST:event_btnThemSanPhamChiTietActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        int getSelect = tblSanPham.getSelectedRow();
        if (getSelect == -1) {
            return;
        }
        String tenSanPham = tblSanPham.getValueAt(getSelect, 1).toString();
        String id = tblSanPham.getValueAt(getSelect, 0).toString();
        txtIDSanPham.setText(id);
        txtTenSanPham.setText(tenSanPham);
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void rdoChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoChatLieuActionPerformed
        loadDataAndFillChatLieu();
    }//GEN-LAST:event_rdoChatLieuActionPerformed

    private void rdoMauSacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoMauSacMouseClicked
        loadDataAndFillMauSac();
    }//GEN-LAST:event_rdoMauSacMouseClicked

    private void rdoSizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoSizeMouseClicked
        loadDataAndFillSize();
    }//GEN-LAST:event_rdoSizeMouseClicked

    private void rdoKieuDangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoKieuDangActionPerformed
        loadDataAndFillKieuDang();
    }//GEN-LAST:event_rdoKieuDangActionPerformed

    private void btnLamMoiThuocTinhSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiThuocTinhSanPhamActionPerformed
        txtTenThuocTinh.setText("");
        txtIdThuocTinh.setText("");
    }//GEN-LAST:event_btnLamMoiThuocTinhSanPhamActionPerformed

    private void btnThemThuocTinhSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemThuocTinhSanPhamActionPerformed
        String tenThuocTinh = txtTenThuocTinh.getText();
        if (tenThuocTinh.trim().isEmpty()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Hãy nhập tên thuộc tính!").showNotification();
            return;
        }
        if (rdoMauSac.isSelected()) {
            MAUSAC ms = themMauSac(tenThuocTinh);
            if (svsp.insertAndUpdateMauSac(ms) != null) {
                new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                        "Thêm thành công!").showNotification();
                loadDataAndFillMauSac();
            }
        }
        if (rdoChatLieu.isSelected()) {
            ChatLieu cl = themChatLieu(tenThuocTinh);
            if (svsp.insertAndUpdateChatLieu(cl) != null) {
                new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                        "Thêm thành công!").showNotification();
                loadDataAndFillChatLieu();
            }
        }
        if (rdoKieuDang.isSelected()) {
            KieuDang cl = themKieuDang(tenThuocTinh);
            if (svsp.insertAndUpdateKieuDang(cl) != null) {
                new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                        "Thêm thành công!").showNotification();
                loadDataAndFillKieuDang();
            }
        }
        if (rdoSize.isSelected()) {
            SIZE cl = themSize(tenThuocTinh);
            if (svsp.insertAndUpdateSize(cl) != null) {
                new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                        "Thêm thành công!").showNotification();
                loadDataAndFillSize();
            }
        }
    }//GEN-LAST:event_btnThemThuocTinhSanPhamActionPerformed

    private void btnSuaThuocTinhSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaThuocTinhSanPhamActionPerformed
        String tenThuocTinh = txtTenThuocTinh.getText();
        String idThuocTinh = txtIdThuocTinh.getText();
        if (tenThuocTinh.trim().isEmpty()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Hãy nhập tên thuộc tính!").showNotification();
            return;
        }
        if (rdoMauSac.isSelected()) {
            MAUSAC ms = new MAUSAC();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            ms.setNgayTao(timestamp);
            ms.setTenMauSac(tenThuocTinh);
            ms.setId(Integer.valueOf(idThuocTinh));
            if (svsp.insertAndUpdateMauSac(ms) != null) {
                new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                        "Thêm thành công!").showNotification();
                loadDataAndFillMauSac();
            }
        }
        if (rdoChatLieu.isSelected()) {
            ChatLieu cl = new ChatLieu();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            cl.setNgayTao(timestamp);
            cl.setTenVai(tenThuocTinh);
            cl.setId(Integer.valueOf(idThuocTinh));
            if (svsp.insertAndUpdateChatLieu(cl) != null) {
                new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                        "Thêm thành công!").showNotification();
                loadDataAndFillChatLieu();
            }
        }
        if (rdoKieuDang.isSelected()) {
            KieuDang cl = new KieuDang();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            cl.setNgayTao(timestamp);
            cl.setTenKieuDang(tenThuocTinh);
            cl.setId(Integer.valueOf(idThuocTinh));
            if (svsp.insertAndUpdateKieuDang(cl) != null) {
                new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                        "Thêm thành công!").showNotification();
                loadDataAndFillKieuDang();
            }
        }
        if (rdoSize.isSelected()) {
            SIZE cl = new SIZE();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            cl.setNgayTao(timestamp);
            cl.setTenSize(tenThuocTinh);
            cl.setId(Integer.valueOf(idThuocTinh));
            if (svsp.insertAndUpdateSize(cl) != null) {
                new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                        "Thêm thành công!").showNotification();
                loadDataAndFillSize();
            }
        }
    }//GEN-LAST:event_btnSuaThuocTinhSanPhamActionPerformed

    private void tblThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuocTinhMouseClicked
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            return;
        }
        txtIdThuocTinh.setText(tblThuocTinh.getValueAt(row, 1).toString());
        txtTenThuocTinh.setText(tblThuocTinh.getValueAt(row, 2).toString());
    }//GEN-LAST:event_tblThuocTinhMouseClicked

    private void btnThemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSanPhamActionPerformed
        if (check()) {
            String tenSanPham = txtTenSanPham.getText();
            SANPHAM newSanPham = new SANPHAM();
            newSanPham.setTenSanPham(tenSanPham);
            newSanPham.setSoLuong(0);
            newSanPham.setTrangThai("Hoạt động");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            newSanPham.setNgayTao(timestamp);
            svsp.saveAndUpdateSanPham(newSanPham);
            new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                    "Thành công!").showNotification();
            loadDataAndFillSanPham();
            loadDataAndFillSanPhamHoatDong();
        }
    }//GEN-LAST:event_btnThemSanPhamActionPerformed

    private void btnSuaSanPhamChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSanPhamChiTietActionPerformed
        int row = tblSanPhamChiTiet.getSelectedRow();
        if (row == -1) {
            return;
        }
        Integer idSanPhamChiTiet = Integer.valueOf(tblSanPhamChiTiet.getValueAt(row, 0).toString());
        int quantity = Integer.parseInt(tblSanPhamChiTiet.getValueAt(row, 6).toString());
        String trangThai = tblSanPhamChiTiet.getValueAt(row, 8).toString();
        String mauSac = cbbMauSac.getSelectedItem().toString();
        String size = cbbSize.getSelectedItem().toString();
        String chatLieu = cbbChatLieu.getSelectedItem().toString();
        String kieuDang = cbbKieuDang.getSelectedItem().toString();
        String tenSanPhamOld = tblSanPhamChiTiet.getValueAt(row, 1).toString();
        String tenSanPhamNew = cbbTenSanPham.getSelectedItem().toString();
        String giaSanPham = txtGiaTien.getText();
        String soLuong = txtSoLuong.getText();
        int soLuong2 = 0;
        Integer idSanPhamNew = null;
        Integer idSanPhamold = null;
        boolean check = true;
        if (checkThemSanPhamChiTiet()) {
            SANPHAMCHITIET spct = new SANPHAMCHITIET();
            spct.setId(idSanPhamChiTiet);
            if (trangThai.equals("Hoạt động")) {
                spct.setTrangThai(true);
            } else {
                spct.setTrangThai(false);
            }
            for (SANPHAM sp : listSanPham) {
                if (sp.getTenSanPham().equals(tenSanPhamNew)) {
                    spct.setId_SanPham(sp);
                    idSanPhamNew = sp.getId();
                    break;
                }
            }
            for (SIZE sp : listSize) {
                if (sp.getTenSize().equals(size)) {
                    spct.setId_Size(sp);
                    break;
                }
            }
            for (MAUSAC sp : listMausac) {
                if (sp.getTenMauSac().equals(mauSac)) {
                    spct.setId_MauSac(sp);
                    break;
                }
            }
            for (KieuDang kd : listKieuDang) {
                if (kd.getTenKieuDang().equals(kieuDang)) {
                    spct.setId_KieuDang(kd);
                    break;
                }
            }
            for (ChatLieu cl : listChatLieu) {
                if (cl.getTenVai().equals(chatLieu)) {
                    spct.setId_ChatLieu(cl);
                    break;
                }
            }
            spct.setGiaSanPham(Double.valueOf(giaSanPham));
            if (soLuong.trim().isEmpty()) {
                spct.setSoLuong(soLuong2);
            } else {
                soLuong2 = Integer.valueOf(txtSoLuong.getText());
                spct.setSoLuong(soLuong2);
            }
            if (check == true) {
                if (svsp.saveAndUpdateSanPhamChiTiet(spct) != null) {
                    if (tenSanPhamNew.equals(tenSanPhamOld)) {
                        svsp.updateSoLuongSanPham2(quantity, soLuong2, idSanPhamNew);
                    } else {
                        for (SANPHAM sp : listSanPham) {
                            if (sp.getTenSanPham().equals(tenSanPhamOld)) {
                                spct.setId_SanPham(sp);
                                idSanPhamold = sp.getId();
                                break;
                            }
                        }
                        svsp.truSoLuong(quantity, idSanPhamold);
                        svsp.updateSoLuongSanPham(soLuong2, idSanPhamNew);
                    }
                    listSanPhamChiTiet.add(spct);
                    new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                            "Thành công!").showNotification();
                    loadDataAndFillSanPhamChiTiet();
                    loadDataAndFillSanPham();
                }
            }
        }
    }//GEN-LAST:event_btnSuaSanPhamChiTietActionPerformed

    private void buttonMessage3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMessage3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonMessage3ActionPerformed

    private void XoaSanPhamChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XoaSanPhamChiTietActionPerformed
        int row = tblSanPhamChiTiet.getSelectedRow();
        if (row == -1) {
            return;
        }
        String maSanPham = tblSanPhamChiTiet.getValueAt(row, 0).toString();
        String tenSanPham = tblSanPhamChiTiet.getValueAt(row, 1).toString();
        for(SANPHAM sp : listSanPham){
            if(sp.getTenSanPham().equals(tenSanPham)){
                if(sp.getTrangThai().equals("Ngừng hoạt động")){
                    new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                            "Sản phẩm đã ngừng hoạt động!").showNotification();
                    return;
                }
            }
        }
        TrangThaiSanPhamChiTiet updateTra = new TrangThaiSanPhamChiTiet(main, true, listSanPhamChiTiet, maSanPham);
        updateTra.setVisible(true);
        if (updateTra.check) {
            new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                            "Thành công!").showNotification();
            loadDataAndFillSanPhamChiTiet();
        }

    }//GEN-LAST:event_XoaSanPhamChiTietActionPerformed

    private void btnThemSoLuongSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSoLuongSanPhamActionPerformed
        
    }//GEN-LAST:event_btnThemSoLuongSanPhamActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.ButtonMessage XoaSanPhamChiTiet;
    private udpm.fpt.swing.ButtonMessage btnLamMoiSanPham;
    private udpm.fpt.swing.ButtonMessage btnLamMoiThuocTinhSanPham;
    private udpm.fpt.swing.ButtonMessage btnSuaSanPham;
    private udpm.fpt.swing.ButtonMessage btnSuaSanPhamChiTiet;
    private udpm.fpt.swing.ButtonMessage btnSuaThuocTinhSanPham;
    private udpm.fpt.swing.ButtonMessage btnThemSanPham;
    private udpm.fpt.swing.ButtonMessage btnThemSanPhamChiTiet;
    private udpm.fpt.swing.ButtonMessage btnThemSoLuongSanPham;
    private udpm.fpt.swing.ButtonMessage btnThemThuocTinhSanPham;
    private udpm.fpt.swing.ButtonMessage btnTimKiemSanPham;
    private udpm.fpt.swing.ButtonMessage btnTimKiemSanPhamChiTiet;
    private javax.swing.ButtonGroup buttonGroup1;
    private udpm.fpt.swing.ButtonMessage buttonMessage2;
    private udpm.fpt.swing.ButtonMessage buttonMessage3;
    private udpm.fpt.swing.ButtonMessage buttonMessage4;
    private udpm.fpt.swing.ButtonMessage buttonMessage5;
    private udpm.fpt.swing.ButtonMessage buttonMessage6;
    private udpm.fpt.swing.Combobox cbbChatLieu;
    private udpm.fpt.swing.Combobox cbbKieuDang;
    private udpm.fpt.swing.Combobox cbbMauSac;
    private udpm.fpt.swing.Combobox cbbSize;
    private udpm.fpt.swing.Combobox cbbTenSanPham;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rdoChatLieu;
    private javax.swing.JRadioButton rdoKieuDang;
    private javax.swing.JRadioButton rdoMauSac;
    private javax.swing.JRadioButton rdoSize;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTable tblSanPhamChiTiet;
    private javax.swing.JTable tblThuocTinh;
    private udpm.fpt.swing.TextField textField5;
    private udpm.fpt.swing.TextField textField6;
    private udpm.fpt.swing.TextField textField7;
    private udpm.fpt.swing.TextField txtGiaTien;
    private udpm.fpt.swing.TextField txtIDSanPham;
    private udpm.fpt.swing.TextField txtIdThuocTinh;
    private udpm.fpt.swing.TextField txtSoLuong;
    private udpm.fpt.swing.TextField txtTenSanPham;
    private udpm.fpt.swing.TextField txtTenThuocTinh;
    // End of variables declaration//GEN-END:variables
}
