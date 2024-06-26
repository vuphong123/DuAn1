/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package udpm.fpt.form;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import static udpm.fpt.Application.getBean;
import udpm.fpt.component.ChooseCustomersForm;
import udpm.fpt.component.Notification;
import udpm.fpt.main.Main;
import udpm.fpt.model.BILL;
import udpm.fpt.model.BILLCHITIET;
import udpm.fpt.model.KhachHang;
import udpm.fpt.model.KieuDang;
import udpm.fpt.model.MAUSAC;
import udpm.fpt.model.SANPHAM;
import udpm.fpt.model.SANPHAMCHITIET;
import udpm.fpt.model.SIZE;
import udpm.fpt.model.User;
import udpm.fpt.model.Voucher;
import udpm.fpt.repository.IChiTietSanPham_Respository;
import udpm.fpt.repository.ISANPHAM_Respository;
import udpm.fpt.service.BillService;
import udpm.fpt.swing.table.TableCustom;
import udpm.fpt.repository.IBillDetails_Repository;
import udpm.fpt.repository.IBill_Repository;

/**
 *
 * @author PHONG PC
 */
public final class FormBill extends javax.swing.JPanel {

    NumberFormat numberFormat = NumberFormat.getInstance();
    private KhachHang kh = new KhachHang();
    private final BillService sv = new BillService();
    private List<SANPHAMCHITIET> SAN_PHAM_REPO = new ArrayList();
    private List<BILL> HOA_DON_REPO = new ArrayList();
    private List<BILLCHITIET> HDCT_REPO = new ArrayList();
    IBill_Repository bill = getBean(IBill_Repository.class);
    IBillDetails_Repository billDetail = getBean(IBillDetails_Repository.class);
    ISANPHAM_Respository itSanPham = getBean(ISANPHAM_Respository.class);
    IChiTietSanPham_Respository itChiTietSanPham = getBean(IChiTietSanPham_Respository.class);
    private List<SANPHAM> listSanPham = new ArrayList<>();
    private Vector selectedRows = new Vector();
    private Vector selectedColumns = new Vector();
    private List<Voucher> voucher = new ArrayList<>();
    User user;
    Main main;
    SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy");
    private JFrame jf;

    /**
     * Creates new form FromBill
     *
     * @param user
     */
    public FormBill(JFrame jf, User user) {
        initComponents();
        this.user = user;
        jf = this.jf;
        cbbHinhThucThanhToan();
        DataAndFillHoaDon();
        loadDataAndFillSanPham();
        loadDataAndFillHoaDonChiTiet();
        loadDataAndVoucher();
        loadDataAndFillKieuDang();
        loadDataAndFillMauSac();
        loadDataAndFillSize();
        DataAndFillSanPham();
        excessMoney();
        excessMoney2();
        setFormKhachHang();
        theAmountTheCustomerGives();
        TableCustom.apply(jScrollPane1, TableCustom.TableType.DEFAULT);
        TableCustom.apply(jScrollPane2, TableCustom.TableType.DEFAULT);
        TableCustom.apply(jScrollPane3, TableCustom.TableType.DEFAULT);
    }

    public void loadDataAndFillSanPham() {
        CompletableFuture<List<SANPHAMCHITIET>> future = this.sv.findMilkByName();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                loadSanPham(data);
                SAN_PHAM_REPO = data;
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }
    public void loadDataAndFillSize() {
        CompletableFuture<List<SIZE>> future = this.sv.findAllSize();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                cbbSize(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }
    public void loadDataAndFillMauSac() {
        CompletableFuture<List<MAUSAC>> future = this.sv.findAllMauSac();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                cbbMauSac(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }
    public void loadDataAndFillKieuDang() {
        CompletableFuture<List<KieuDang>> future = this.sv.findAllKieuDang();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                cbbKieuDang(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    public void loadDataAndFillHoaDonChiTiet() {
        CompletableFuture<List<BILLCHITIET>> future = this.sv.findBillDetail();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                HDCT_REPO = data;
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    public void loadDataAndVoucher() {
        CompletableFuture<List<Voucher>> future = this.sv.finAllVoucher();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                voucher = data;
                loadVoucher(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    public void loadVoucher(List<Voucher> list) {
        DefaultComboBoxModel cbbVoucher = (DefaultComboBoxModel) this.cbbPhieuGiamGia.getModel();
        cbbVoucher.removeAllElements();
        cbbVoucher.addElement("Chọn phiếu giảm giá");
        for (Voucher vc : list) {
            cbbVoucher.addElement(vc.getTenPhieu() + " " + vc.getDieuKienGiam());
        }
    }

    public void setFormKhachHang() {
        txtIDCustomer.setText("");
        txtTenKhachHang.setText("Khách lẻ");
    }

    public void setCustomer(KhachHang kh) {
        this.kh = kh;
        System.out.println(this.kh.getId() + "khách id");
        txtIDCustomer.setText(String.valueOf(kh.getId()));
        txtTenKhachHang.setText(kh.getTenKhachHang());
    }

    public void setForm(BILL b) {
        txtMaHoaDon.setText(b.getId());
        txtMaNhanVien.setText(String.valueOf(b.getId_NhanVien().getId()));
        txtNgayTao.setText(simple.format(b.getNgayTao()));
    }

    public void cbbHinhThucThanhToan() {
        DefaultComboBoxModel dd = (DefaultComboBoxModel) cbbHinhThucThanhToan.getModel();
        dd.removeAllElements();
        dd.addElement("Tiền mặt");
        dd.addElement("Chuyển khoản");
    }
    public void cbbMauSac(List<MAUSAC> ms) {
        DefaultComboBoxModel dd = (DefaultComboBoxModel) cbbMauSac.getModel();
        dd.removeAllElements();
        dd.addElement("");
        for(MAUSAC mss : ms){
            dd.addElement(mss.getTenMauSac());
        }
    }
    public void cbbSize(List<SIZE> s) {
        DefaultComboBoxModel dd = (DefaultComboBoxModel) cbbSize.getModel();
        dd.removeAllElements();
        dd.addElement("");
        for(SIZE mss : s){
            dd.addElement(mss.getTenSize());
        }
    }
    public void cbbKieuDang(List<KieuDang> kd) {
        DefaultComboBoxModel dd = (DefaultComboBoxModel) cbbKieuDang.getModel();
        dd.removeAllElements();
        dd.addElement("");
        for(KieuDang mss : kd){
            dd.addElement(mss.getTenKieuDang());
        }
    }

    public void DataAndFillSanPham() {
        CompletableFuture<List<SANPHAM>> future = this.sv.findSanPham();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                listSanPham = data;
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    public void DataAndFillHoaDon() {
        CompletableFuture<List<BILL>> future = this.sv.finAllBill();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                loadHoaDon(data);
                HOA_DON_REPO = data;
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    public void loadSanPham(List<SANPHAMCHITIET> sp) {
        DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
        model.setRowCount(0);
        for (SANPHAMCHITIET spct : sp) {
            Object[] rowData = {
                spct.getId(),
                spct.getId_SanPham().getTenSanPham(),
                spct.getId_MauSac().getTenMauSac(),
                spct.getId_Size().getTenSize(),
                spct.getId_KieuDang().getTenKieuDang(),
                spct.getSoLuong(),
                spct.getGiaSanPham()};
            model.addRow(rowData);
        }
    }

    public void setForm() {
        txtIDCustomer.setText("####");
        txtMaHoaDon.setText("####");
        txtMaNhanVien.setText("####");
        txtNgayTao.setText("####");
        txtSoTienKhachTra.setText("0");
        txtTenKhachHang.setText("####");
        txtTienThua.setText("0");
        txtTongTien.setText("0");
        txtTongTienEnd.setText("0");

    }
//    private void reloadTableData(JTable tb) {
//        DefaultTableModel tableModel = (DefaultTableModel) tb.getModel();
//        // Lưu trữ thông tin về lựa chọn
//        selectedRows.clear();
//        selectedColumns.clear();
//        int[] rows = tb.getSelectedRows();
//        int[] columns = tb.getSelectedColumns();
//        for (int row : rows) {
//            selectedRows.add(row);
//        }
//        for (int column : columns) {
//            selectedColumns.add(column);
//        }
//        // Khôi phục lựa chọn
//        for (Object selectedRow : selectedRows) {
//            tb.addRowSelectionInterval((Integer) selectedRow, (Integer) selectedRow);
//        }
//        for (Object selectedColumn : selectedColumns) {
//            tb.addColumnSelectionInterval((Integer) selectedColumn, (Integer) selectedColumn);
//        }
//    }

    public void searchSanPham(String nameProduct, String nameColor, String nameSize, String nameKieuDang) {
        CompletableFuture<List<SANPHAMCHITIET>> future = this.sv.searchSanPham(nameProduct, nameColor, nameSize, nameKieuDang);
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                loadSanPham(data);
                this.SAN_PHAM_REPO = data;
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    public void loadHoaDon(List<BILL> listBill) {
        DefaultTableModel model = (DefaultTableModel) tblBill.getModel();
        model.setRowCount(0);
        for (BILL b : listBill) {
            Object[] rowData = {
                b.getId(),
                simple.format(b.getNgayTao()),
                b.getId_NhanVien().getId(),
                b.getTongSanPham(),
                b.getTrangThai(),};
            model.addRow(rowData);
        }
    }

    public void loadHoaDonChiTiet(String maHoaDon) {
        DefaultTableModel model = (DefaultTableModel) tblShoppingCart.getModel();
        model.setRowCount(0);
        for (BILLCHITIET b : HDCT_REPO) {
            if (b.getIdBill().getId() == null ? maHoaDon == null : b.getIdBill().getId().equals(maHoaDon)) {
                Object[] rowData = {
                    b.getIdSanPhamChiTiet().getId(),
                    b.getIdSanPhamChiTiet().getId_SanPham().getTenSanPham(),
                    b.getIdSanPhamChiTiet().getId_MauSac().getTenMauSac(),
                    b.getIdSanPhamChiTiet().getId_Size().getTenSize(),
                    b.getIdSanPhamChiTiet().getGiaSanPham(),
                    b.getSoLuongDaMua(),
                    //                    b.getSoLuongDaMua() * b.getIdSanPhamChiTiet().getGiaSanPham()
                    b.getThanhTien(),};
                model.addRow(rowData);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBill = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblShoppingCart = new javax.swing.JTable();
        btnSuaSoLuong = new udpm.fpt.swing.ButtonMessage();
        btnXoaSanPham = new udpm.fpt.swing.ButtonMessage();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        txtSearchProduct = new udpm.fpt.swing.TextField();
        btnSearch = new udpm.fpt.swing.ButtonMessage();
        cbbKieuDang = new javax.swing.JComboBox<>();
        cbbSize = new javax.swing.JComboBox<>();
        cbbMauSac = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        txtIDCustomer = new udpm.fpt.swing.TextField();
        txtTenKhachHang = new udpm.fpt.swing.TextField();
        btnChooseCustomer = new udpm.fpt.swing.Button();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtMaHoaDon = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cbbPhieuGiamGia = new udpm.fpt.swing.Combobox();
        cbbHinhThucThanhToan = new udpm.fpt.swing.Combobox();
        txtSoTienKhachTra = new udpm.fpt.swing.TextField();
        jLabel13 = new javax.swing.JLabel();
        txtTienThua = new javax.swing.JLabel();
        btnThanhToan = new udpm.fpt.swing.ButtonMessage();
        jLabel15 = new javax.swing.JLabel();
        btnHuyHoaDon = new udpm.fpt.swing.ButtonMessage();
        btnTaoHoaDon = new udpm.fpt.swing.ButtonMessage();
        txtTongTienEnd = new javax.swing.JLabel();
        txtNgayTao = new javax.swing.JLabel();
        txtMaNhanVien = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtTienGiam = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtTienKhachChuyenKhoan = new udpm.fpt.swing.TextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jRadioButton1.setText("jRadioButton1");

        setPreferredSize(new java.awt.Dimension(1489, 880));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1527, 824));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblBill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Ngày tạo", "Nhân viên", "Tổng sản phẩm", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBill.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblBill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBillMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBill);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblShoppingCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SPCT", "Tên sản phẩm", "Màu sắc", "Size", "Gía bán ", "Số lượng", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblShoppingCart.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblShoppingCart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblShoppingCartMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblShoppingCart);

        btnSuaSoLuong.setBackground(new java.awt.Color(0, 204, 204));
        btnSuaSoLuong.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaSoLuong.setText("Chỉnh số lượng");
        btnSuaSoLuong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSoLuongActionPerformed(evt);
            }
        });

        btnXoaSanPham.setBackground(new java.awt.Color(0, 204, 204));
        btnXoaSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaSanPham.setText("Xóa sản phẩm");
        btnXoaSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSanPhamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnXoaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSuaSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel3.setText("Danh sách sản phẩm");

        jLabel1.setText("Giỏ hàng");

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SPCT", "Tên sản phẩm ", "Màu sắc", "Size", "Kiểu dáng", "Số lượng tồn", "Giá bán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProduct.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblProduct);

        txtSearchProduct.setLabelText("Search by name");
        txtSearchProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchProductActionPerformed(evt);
            }
        });

        btnSearch.setBackground(new java.awt.Color(0, 204, 204));
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("Tìm kiếm");
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        cbbKieuDang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbKieuDang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbKieuDangActionPerformed(evt);
            }
        });

        cbbSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbbMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("Màu sắc:");

        jLabel18.setText("Size:");

        jLabel19.setText("kiểu dáng");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbSize, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(cbbMauSac)
                                .addComponent(cbbSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel18)
                                .addComponent(jLabel19)
                                .addComponent(cbbKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(txtSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(920, Short.MAX_VALUE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin khách hàng"));

        txtIDCustomer.setEditable(false);
        txtIDCustomer.setText("######");
        txtIDCustomer.setLabelText("id customer");

        txtTenKhachHang.setEditable(false);
        txtTenKhachHang.setText("######");
        txtTenKhachHang.setLabelText("Customer name");

        btnChooseCustomer.setBackground(new java.awt.Color(0, 204, 204));
        btnChooseCustomer.setForeground(new java.awt.Color(255, 255, 255));
        btnChooseCustomer.setText("Chọn khách hàng");
        btnChooseCustomer.setToolTipText("");
        btnChooseCustomer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnChooseCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseCustomerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIDCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTenKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32)
                .addComponent(btnChooseCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtIDCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(btnChooseCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin đơn hàng"));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Mã hóa đơn");

        txtMaHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtMaHoaDon.setText("######");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Ngày tạo");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Mã nhân viên");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Tổng tiền");

        cbbPhieuGiamGia.setLabeText("Phiếu giảm giá");
        cbbPhieuGiamGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbbPhieuGiamGiaMouseClicked(evt);
            }
        });
        cbbPhieuGiamGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbPhieuGiamGiaActionPerformed(evt);
            }
        });

        cbbHinhThucThanhToan.setLabeText("Hình thức thanh toán");
        cbbHinhThucThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbHinhThucThanhToanActionPerformed(evt);
            }
        });

        txtSoTienKhachTra.setText("0");
        txtSoTienKhachTra.setLabelText("Số tiền khách trả");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Tiền thừa");

        txtTienThua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTienThua.setText("0");

        btnThanhToan.setBackground(new java.awt.Color(0, 204, 204));
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 0, 51));
        jLabel15.setText("Tổng:");

        btnHuyHoaDon.setBackground(new java.awt.Color(0, 204, 204));
        btnHuyHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnHuyHoaDon.setText("Hủy");
        btnHuyHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHuyHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyHoaDonActionPerformed(evt);
            }
        });

        btnTaoHoaDon.setBackground(new java.awt.Color(0, 204, 204));
        btnTaoHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnTaoHoaDon.setText("Tạo hóa đơn");
        btnTaoHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonActionPerformed(evt);
            }
        });

        txtTongTienEnd.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtTongTienEnd.setForeground(new java.awt.Color(255, 0, 51));
        txtTongTienEnd.setText("0");

        txtNgayTao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtNgayTao.setText("######");

        txtMaNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtMaNhanVien.setText("######");

        txtTongTien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTongTien.setForeground(new java.awt.Color(255, 0, 51));
        txtTongTien.setText("0");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("VNĐ");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("VNĐ");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("VNĐ");

        jLabel16.setText("Tiền giảm");

        txtTienGiam.setText("0");

        jLabel17.setText("VND");

        txtTienKhachChuyenKhoan.setText("0");
        txtTienKhachChuyenKhoan.setLabelText("Số tiền khách chuyển khoản");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbPhieuGiamGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbHinhThucThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSoTienKhachTra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(btnHuyHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(101, 101, 101)
                        .addComponent(txtMaHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(121, 121, 121)
                        .addComponent(txtNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12))
                        .addGap(90, 90, 90)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel14)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtMaNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(txtTienKhachChuyenKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTongTienEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel16))
                                .addGap(116, 116, 116)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTienThua, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                                    .addComponent(txtTienGiam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel17))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMaHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtNgayTao))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtMaNhanVien))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTongTien)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addComponent(cbbPhieuGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbbHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtSoTienKhachTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTienKhachChuyenKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTienThua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtTienGiam)
                    .addComponent(jLabel17))
                .addGap(21, 21, 21)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTongTienEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuyHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel2.setText("Hóa đơn chờ");

        jLabel4.setText("Đơn hàng");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addGap(422, 422, 422)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1483, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1489, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 865, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 865, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 15, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblBillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBillMouseClicked
        int selectedBill = tblBill.getSelectedRow();
        if (selectedBill == -1) {
            return;
        }
        String maHoaDon = tblBill.getValueAt(selectedBill, 0).toString();
        double total = 0;
        for (BILL b : HOA_DON_REPO) {
            if (b.getId().equals(maHoaDon)) {
                setForm(b);
            }
        }
        for (BILLCHITIET h : HDCT_REPO) {
            if (h.getIdBill().getId().equals(maHoaDon)) {
                total += h.getIdSanPhamChiTiet().getGiaSanPham() * h.getSoLuongDaMua();
            }
        }
        txtTongTien.setText(String.valueOf(numberFormat.format(total)));
        txtTongTienEnd.setText(String.valueOf(numberFormat.format(total)));
        int selectedRow = tblBill.getSelectedRow();

        loadHoaDonChiTiet(maHoaDon);
    }//GEN-LAST:event_tblBillMouseClicked

    private void tblShoppingCartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblShoppingCartMouseClicked

    }//GEN-LAST:event_tblShoppingCartMouseClicked

    private void tblProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductMouseClicked
        int selectedProduct = tblProduct.getSelectedRow();
        int selectedBill = tblBill.getSelectedRow();
        if (selectedProduct == -1 || selectedBill == -1) {
            return;
        }
        Integer maSanPham = Integer.valueOf(tblProduct.getValueAt(selectedProduct, 0).toString());
        String tenSanPham = tblProduct.getValueAt(selectedProduct, 1).toString();
        SANPHAMCHITIET spct = new SANPHAMCHITIET();
        for (SANPHAMCHITIET sp : SAN_PHAM_REPO) {
            if (Objects.equals(sp.getId(), maSanPham)) {
                spct = sp;
            }
        }
        System.out.println(spct.getId());
        String maHoaDon = tblBill.getValueAt(selectedBill, 0).toString();
        BILL bill = new BILL();
        bill.setId(maHoaDon);

        BILLCHITIET hdct = new BILLCHITIET();
        hdct.setIdBill(bill);
        hdct.setIdSanPhamChiTiet(spct);
        hdct.setGiaTienHienTai(spct.getGiaSanPham());
        SoLuongDaMua slMua = new SoLuongDaMua(jf, true);
        slMua.setVisible(true);
        int sl = slMua.soLuong;
        if (sl <= 0) {
            return;
        }
        hdct.setSoLuongDaMua(sl);
        hdct.setThanhTien(hdct.getSoLuongDaMua() * spct.getGiaSanPham());
        boolean isNotExisted = true;
        for (SANPHAMCHITIET sp : SAN_PHAM_REPO) {
            if (sp.getId().equals(maSanPham)) {
                if (sp.getSoLuong() <= 0) {
                    new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                            "Sản phẩm đã hết hàng!").showNotification();
                    return;
                } else {
                    if (sp.getSoLuong() - sl < 0) {
                        new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                                "Số lượng sản phẩm không đủ!").showNotification();
                        return;
                    }
                    sp.setSoLuong(sp.getSoLuong() - sl);
                    loadSanPham(SAN_PHAM_REPO);
                }
            }
        }
        for (BILLCHITIET h : HDCT_REPO) {
            if (h.getIdBill().getId().equals(maHoaDon)
                    && h.getIdSanPhamChiTiet().getId().equals(maSanPham)) {
                h.setSoLuongDaMua(h.getSoLuongDaMua() + hdct.getSoLuongDaMua());
                h.setThanhTien(h.getSoLuongDaMua() * spct.getGiaSanPham());
                this.billDetail.save(h);
                isNotExisted = false;
                break;
            }
        }

        // Lưu trữ thông tin về lựa chọn
        selectedRows.clear();
        selectedColumns.clear();
        int[] rows = tblBill.getSelectedRows();
        int[] columns = tblBill.getSelectedColumns();
        for (int row : rows) {
            selectedRows.add(row);
        }
        for (int column : columns) {
            selectedColumns.add(column);
        }
        for (BILL b : HOA_DON_REPO) {
            if (b.getId().equals(maHoaDon)) {
                b.setTongSanPham(b.getTongSanPham() + sl);
                break;
            }
        }
        loadHoaDon(HOA_DON_REPO);
        for (Object selectedRow : selectedRows) {
            tblBill.addRowSelectionInterval((Integer) selectedRow, (Integer) selectedRow);
        }
        for (Object selectedColumn : selectedColumns) {
            tblBill.addColumnSelectionInterval((Integer) selectedColumn, (Integer) selectedColumn);
        }
        if (isNotExisted) {
            HDCT_REPO.add(hdct);
            billDetail.save(hdct);
        }
        double total = 0;
        for (BILLCHITIET h : HDCT_REPO) {
            if (h.getIdBill().getId().equals(maHoaDon)) {
                total += h.getIdSanPhamChiTiet().getGiaSanPham() * h.getSoLuongDaMua();
            }
        }
        for (SANPHAM sp : listSanPham) {
            if (sp.getTenSanPham().equals(tenSanPham)) {
                itSanPham.ChinhSuaSoLuong(sl, sp.getId());
                break;
            }
        }
        for (SANPHAMCHITIET spcttt : SAN_PHAM_REPO) {
            if (spcttt.getId().equals(maSanPham)) {
                itChiTietSanPham.ChinhSuaSoLuong(sl, spcttt.getId());
                break;
            }
        }
        this.bill.ChinhSuaSoLuong(sl, maHoaDon);
        txtTongTien.setText(numberFormat.format(total));
        txtTongTienEnd.setText(numberFormat.format(total));
        loadHoaDonChiTiet(maHoaDon);
    }//GEN-LAST:event_tblProductMouseClicked

    private void btnHuyHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyHoaDonActionPerformed
        int check = JOptionPane.showConfirmDialog(main, "Bạn có chắc muốn hủy", "Cảnh báo", WIDTH);
        if (check == 0) {
            int selectBill = tblBill.getSelectedRow();
            if (selectBill == -1) {
                return;
            }
            String maHoaDon = tblBill.getValueAt(selectBill, 0).toString();
            for (BILLCHITIET sp : HDCT_REPO) {
                for (SANPHAM spcc : listSanPham) {
                    if (sp.getIdBill().getId().equals(maHoaDon)&&spcc.getTenSanPham().equals(sp.getIdSanPhamChiTiet().getId_SanPham().getTenSanPham())) {
                        itSanPham.updateQuantity(sp.getSoLuongDaMua(), spcc.getId());
                        spcc.setSoLuong(spcc.getSoLuong() + sp.getSoLuongDaMua());
                        break;
                    }
                }
                for (SANPHAMCHITIET spccc : SAN_PHAM_REPO) {
                    if (sp.getIdBill().getId().equals(maHoaDon)&&spccc.getId().equals(sp.getIdSanPhamChiTiet().getId())) {
                        spccc.setSoLuong(spccc.getSoLuong() + sp.getSoLuongDaMua());
                        itChiTietSanPham.updateQuantity(sp.getSoLuongDaMua(), spccc.getId());
                    }
                }
            }
            for (BILL b : HOA_DON_REPO) {
                if (b.getId().equals(maHoaDon)) {
                    b.setTrangThai("Bị hủy");
                    HOA_DON_REPO.remove(b);
                    bill.save(b);
                    break;
                }
            }
            DataAndFillHoaDon();
            loadSanPham(SAN_PHAM_REPO);
            loadHoaDonChiTiet(null);
            new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                                "Thành công!").showNotification();
                        return;
        }

    }//GEN-LAST:event_btnHuyHoaDonActionPerformed

    private void btnTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonActionPerformed
        if (HOA_DON_REPO.size() < 6) {
            BILL hoaDon = new BILL();
            hoaDon.setId(String.valueOf(sv.generateRandomNumber()));
            hoaDon.setId_NhanVien(user);
            hoaDon.setTongSanPham(0);
            hoaDon.setTongTien(Double.valueOf(0));
            hoaDon.setTongTienSauKhiGiam(Double.valueOf(0));
            KhachHang kh2 = new KhachHang();
            kh2.setId(1);
            System.out.println("kh 1");
            kh2.setTenKhachHang("Khách lẻ");

            hoaDon.setId_KhachHang(kh2);
            hoaDon.setTrangThai("Chưa thanh toán");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            hoaDon.setNgayTao(timestamp);
            if (sv.themHoaDon(hoaDon) != true) {
                return;
            }
            HOA_DON_REPO.add(0, hoaDon);
            loadHoaDon(HOA_DON_REPO);
        } else {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Hóa đơn đạt giới hạn!").showNotification();
        }
    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void btnChooseCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseCustomerActionPerformed
        kh = new KhachHang();
        ChooseCustomersForm choose = new ChooseCustomersForm(this);
        choose.setVisible(true);

    }//GEN-LAST:event_btnChooseCustomerActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        int selectedRow = tblBill.getSelectedRow();
        if (selectedRow == -1) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Chọn hóa đơn muốn thanh toán").showNotification();
            return;
        }
        if (tblShoppingCart.getRowCount() == 0) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Hãy thêm sản phẩm").showNotification();
            return;
        }
        if (txtSoTienKhachTra.getText().trim().isEmpty()) {
            return;
        }
        NumberFormat numberFormat = NumberFormat.getInstance();
        double tongTien = 0;
        double soTienKhachTra = 0;
        double tongTienEnd = 0;
        try {
            tongTien = numberFormat.parse(txtTongTien.getText()).doubleValue();
            tongTienEnd = numberFormat.parse(txtTongTienEnd.getText()).doubleValue();
            if(cbbHinhThucThanhToan.getSelectedIndex()==0){
                soTienKhachTra = numberFormat.parse(txtSoTienKhachTra.getText()).doubleValue();
            }else{
                soTienKhachTra = numberFormat.parse(txtTienKhachChuyenKhoan.getText()).doubleValue();
            }
        } catch (ParseException ex) {
            Logger.getLogger(FormBill.class.getName()).log(Level.SEVERE, null, ex);
        }
        BILL bill = new BILL();
        String maHoaDon = tblBill.getValueAt(selectedRow, 0).toString();
        for (BILL hd : HOA_DON_REPO) {
            if (hd.getId().equals(maHoaDon)) {
                System.out.println(maHoaDon + " jj");
                bill = hd;
                break;
            }
        }
        System.out.println(this.kh.getId() + "khách id bill");
        if (this.kh.getId() == null) {
            kh.setId(1);
            System.out.println("kh 1");
            kh.setTenKhachHang("Khách lẻ");
        }
        bill.setId_KhachHang(kh);
        bill.setTongTien(tongTien);
        bill.setTrangThai("Đã thanh toán");
        bill.setId_Voucher(null);
        bill.setTienGiam(txtTienGiam.getText().equals("") ? Double.valueOf(0) : Double.valueOf(txtTienGiam.getText()));
        bill.setTienKhachTra(soTienKhachTra);
        bill.setTongTienSauKhiGiam(tongTienEnd);
        bill.setTienTraLai(Double.valueOf(txtTienThua.getText()));
        double tienGiam = 0;
        if(!txtTienGiam.getText().trim().isEmpty()){
            tienGiam = Double.valueOf(txtTienGiam.getText());
        }
        bill.setTienGiam( tienGiam);
        if (tongTienEnd <= soTienKhachTra) {
            sv.suaTrangThaiHoaDon(bill);
            List<BILLCHITIET> list = new ArrayList<>();
            for (BILLCHITIET hd : HDCT_REPO) {
                if (hd.getIdBill().getId().equals(maHoaDon)) {
                    list.add(hd);
                }
            }
            int indexVoucher = cbbPhieuGiamGia.getSelectedIndex() - 1;
            if (indexVoucher >= 0) {
                Voucher getVoucher = voucher.get(indexVoucher);
                sv.setSLVoucher(getVoucher.getId());
            }
            if (list.size() > 0) {
                sv.themHoaDonChiTiet(list);
                new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                        "Thành công").showNotification();
            } else {
                return;
            }
            setForm();
            DataAndFillHoaDon();
            loadHoaDonChiTiet(null);
            PrintBill inHoaDon = new PrintBill(main, true, HOA_DON_REPO, HDCT_REPO, maHoaDon, String.valueOf(kh.getId()));
            inHoaDon.setVisible(true);
        } else {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Số tiền khách trả chưa đủ").showNotification();
            return;
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String name = txtSearchProduct.getText();
        if(name.trim().isEmpty()){
            name = "";
        }
        String nameColor = cbbMauSac.getSelectedItem().toString();
        String nameSize = cbbSize.getSelectedItem().toString();
        String nameKieuDang = cbbKieuDang.getSelectedItem().toString();
        searchSanPham(name, nameColor, nameSize,nameKieuDang);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnXoaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSanPhamActionPerformed
        int selectProduct = tblShoppingCart.getSelectedRow();
        int selectBill = tblBill.getSelectedRow();
        if (selectProduct == -1) {
            return;
        }
        Integer maSanPham = Integer.valueOf(tblShoppingCart.getValueAt(selectProduct, 0).toString());
        String tenSanPham = tblShoppingCart.getValueAt(0, 1).toString();
        Integer idSanPham = null;
        String maHoaDon = tblBill.getValueAt(selectBill, 0).toString();
        selectedRows.clear();
        selectedColumns.clear();
        int[] rows = tblBill.getSelectedRows();
        int[] columns = tblBill.getSelectedColumns();
        for (int row : rows) {
            selectedRows.add(row);
        }
        for (int column : columns) {
            selectedColumns.add(column);
        }
        for (SANPHAM sp : listSanPham) {
            if (sp.getTenSanPham().equals(tenSanPham)) {
                idSanPham = sp.getId();
                break;
            }
        }
        for (BILLCHITIET sp : HDCT_REPO) {
            if (sp.getIdSanPhamChiTiet().getId() == maSanPham && sp.getIdBill().getId().equals(maHoaDon)) {
                for (BILL b : HOA_DON_REPO) {
                    if (b.getId().equals(maHoaDon)) {
                        b.setTongSanPham(b.getTongSanPham() - sp.getSoLuongDaMua());
                        itSanPham.updateQuantity(sp.getSoLuongDaMua(), idSanPham);
                        itChiTietSanPham.updateQuantity(sp.getSoLuongDaMua(), maSanPham);
                    }
                    break;
                }
                for (SANPHAMCHITIET spct : SAN_PHAM_REPO) {
                    if (Objects.equals(spct.getId(), maSanPham)) {
                        spct.setSoLuong(spct.getSoLuong() + sp.getSoLuongDaMua());
                    }
                }
                HDCT_REPO.remove(sp);
                break;
            }
        }
        loadSanPham(SAN_PHAM_REPO);
        loadHoaDon(HOA_DON_REPO);
        for (Object selectedRow : selectedRows) {
            tblBill.addRowSelectionInterval((Integer) selectedRow, (Integer) selectedRow);
        }
        for (Object selectedColumn : selectedColumns) {
            tblBill.addColumnSelectionInterval((Integer) selectedColumn, (Integer) selectedColumn);
        }
        loadHoaDonChiTiet(maHoaDon);
        double total = 0;
        for (BILLCHITIET h : HDCT_REPO) {
            if (h.getIdBill().getId().equals(maHoaDon)) {
                total += h.getIdSanPhamChiTiet().getGiaSanPham() * h.getSoLuongDaMua();
            }
        }

        txtTongTien.setText(String.valueOf(total));
        txtTongTienEnd.setText(String.valueOf(total));
    }//GEN-LAST:event_btnXoaSanPhamActionPerformed
//Xóa tất cả sản phẩm
//int selectBill = tblBill.getSelectedRow();
//        if (selectBill == -1) {
//            return;
//        }
//        String maHoaDon = tblBill.getValueAt(selectBill, 0).toString();
//        List<BILLCHITIET> billl = new ArrayList<>();
//        for (BILLCHITIET sp : HDCT_REPO) {
//            if (sp.getIdBill().getId().equals(maHoaDon)) {
//                billl.add(sp);
//            }
//        }
//        if (billl.size() >= 0) {
//            HDCT_REPO.removeAll(billl);
//        }
//        for (BILL hd : HOA_DON_REPO) {
//            if (hd.getId().equals(maHoaDon)) {
//                hd.setTongSanPham(0);
//                break;
//            }
//        }
//        selectedRows.clear();
//        selectedColumns.clear();
//        int[] rows = tblBill.getSelectedRows();
//        int[] columns = tblBill.getSelectedColumns();
//        for (int row : rows) {
//            selectedRows.add(row);
//        }
//        for (int column : columns) {
//            selectedColumns.add(column);
//        }
//        txtTongTien.setText("0");
//        txtTongTienEnd.setText("0");
//        txtTienThua.setText("0");
//        loadHoaDon(HOA_DON_REPO);
//        loadHoaDonChiTiet(maHoaDon);
//        for (Object selectedRow : selectedRows) {
//            tblBill.addRowSelectionInterval((Integer) selectedRow, (Integer) selectedRow);
//        }
//        for (Object selectedColumn : selectedColumns) {
//            tblBill.addColumnSelectionInterval((Integer) selectedColumn, (Integer) selectedColumn);
//        }
    private void btnSuaSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSoLuongActionPerformed
        int selectGioHang = tblShoppingCart.getSelectedRow();
        int selectHoaDon = tblBill.getSelectedRow();
        if (selectGioHang == -1) {
            return;
        }
        String maSanPham = tblShoppingCart.getValueAt(selectGioHang, 0).toString();
        String maHoaDon = tblBill.getValueAt(selectHoaDon, 0).toString();
        SoLuongDaMua sl = new SoLuongDaMua(jf, true);
        sl.setVisible(true);
        int slSua = sl.soLuong;
        int soLuongDaMua = 0;
        for (BILLCHITIET b : HDCT_REPO) {
            if (Objects.equals(b.getIdSanPhamChiTiet().getId(), Integer.valueOf(maSanPham)) && b.getIdBill().getId().equals(maHoaDon)) {
                soLuongDaMua = b.getSoLuongDaMua();
                b.setSoLuongDaMua(slSua);
                break;
            }
        }
        for (BILL bill : HOA_DON_REPO) {
            if (bill.getId().equals(maHoaDon)) {
                bill.setTongSanPham(bill.getTongSanPham() - soLuongDaMua + slSua);
                break;
            }
        }
        selectedRows.clear();
        selectedColumns.clear();
        int[] rows = tblBill.getSelectedRows();
        int[] columns = tblBill.getSelectedColumns();
        for (int row : rows) {
            selectedRows.add(row);
        }
        for (int column : columns) {
            selectedColumns.add(column);
        }
        loadHoaDonChiTiet(maHoaDon);
        loadHoaDon(HOA_DON_REPO);
        for (Object selectedRow : selectedRows) {
            tblBill.addRowSelectionInterval((Integer) selectedRow, (Integer) selectedRow);
        }
        for (Object selectedColumn : selectedColumns) {
            tblBill.addColumnSelectionInterval((Integer) selectedColumn, (Integer) selectedColumn);
        }
    }//GEN-LAST:event_btnSuaSoLuongActionPerformed

    private void cbbHinhThucThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbHinhThucThanhToanActionPerformed
        if(cbbHinhThucThanhToan.getSelectedIndex()==1){
            txtSoTienKhachTra.setEditable(false);
            txtTienKhachChuyenKhoan.setEditable(true);
            txtSoTienKhachTra.setText("0");
            txtTienThua.setText("0");
        }
        if(cbbHinhThucThanhToan.getSelectedIndex()==0){
            txtSoTienKhachTra.setEditable(true);
            txtTienKhachChuyenKhoan.setText("0");
            txtTienKhachChuyenKhoan.setEditable(false);
            txtTienThua.setText("0");
        }
    }//GEN-LAST:event_cbbHinhThucThanhToanActionPerformed

    private void cbbPhieuGiamGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbbPhieuGiamGiaMouseClicked

    }//GEN-LAST:event_cbbPhieuGiamGiaMouseClicked

    private void cbbPhieuGiamGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbPhieuGiamGiaActionPerformed
        int voucher = cbbPhieuGiamGia.getSelectedIndex() - 1;
        double tongTien = 0;
        if (voucher < 0) {
            txtTongTienEnd.setText(txtTongTien.getText());
            txtTienGiam.setText("0");
        }
        if (voucher >= 0) {
            try {
                tongTien = numberFormat.parse(txtTongTien.getText()).doubleValue();
            } catch (ParseException ex) {
                Logger.getLogger(FormBill.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (txtTongTien.getText().isEmpty() || tongTien <= 0) {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Hãy thêm sản phẩm trước khi chọn phiếu giảm giá").showNotification();
                cbbPhieuGiamGia.setSelectedIndex(0);
                return;
            }
            Voucher vcc = this.voucher.get(voucher);
            if (tongTien < vcc.getDieuKienGiam()) {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Số tiền mua chưa đủ để áp dụng phiếu giảm giá này").showNotification();
                cbbPhieuGiamGia.setSelectedIndex(0);
                return;
            }
            txtTienGiam.setText(String.valueOf(tongTien * vcc.getPhamTramGiam() / 100));
            tongTien = tongTien - (tongTien * vcc.getPhamTramGiam() / 100);
            txtTongTienEnd.setText(numberFormat.format(tongTien));
        }

    }//GEN-LAST:event_cbbPhieuGiamGiaActionPerformed

    private void cbbKieuDangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbKieuDangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbKieuDangActionPerformed

    private void txtSearchProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchProductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchProductActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.Button btnChooseCustomer;
    private udpm.fpt.swing.ButtonMessage btnHuyHoaDon;
    private udpm.fpt.swing.ButtonMessage btnSearch;
    private udpm.fpt.swing.ButtonMessage btnSuaSoLuong;
    private udpm.fpt.swing.ButtonMessage btnTaoHoaDon;
    private udpm.fpt.swing.ButtonMessage btnThanhToan;
    private udpm.fpt.swing.ButtonMessage btnXoaSanPham;
    private udpm.fpt.swing.Combobox cbbHinhThucThanhToan;
    private javax.swing.JComboBox<String> cbbKieuDang;
    private javax.swing.JComboBox<String> cbbMauSac;
    private udpm.fpt.swing.Combobox cbbPhieuGiamGia;
    private javax.swing.JComboBox<String> cbbSize;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblBill;
    private javax.swing.JTable tblProduct;
    private javax.swing.JTable tblShoppingCart;
    private udpm.fpt.swing.TextField txtIDCustomer;
    private javax.swing.JLabel txtMaHoaDon;
    private javax.swing.JLabel txtMaNhanVien;
    private javax.swing.JLabel txtNgayTao;
    private udpm.fpt.swing.TextField txtSearchProduct;
    private udpm.fpt.swing.TextField txtSoTienKhachTra;
    private udpm.fpt.swing.TextField txtTenKhachHang;
    private javax.swing.JLabel txtTienGiam;
    private udpm.fpt.swing.TextField txtTienKhachChuyenKhoan;
    private javax.swing.JLabel txtTienThua;
    private javax.swing.JLabel txtTongTien;
    private javax.swing.JLabel txtTongTienEnd;
    // End of variables declaration//GEN-END:variables
    public void excessMoney() {
        txtSoTienKhachTra.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Gọi phương thức để tự động tính toán khi người dùng nhập
                calculateChange();
            }
        });

        // Phương thức để tự động tính toán tiền thừa
        // Được gọi từ sự kiện nhấn phím Enter trong ô txtKhachDua       
    }

    private void calculateChange() {
        if (txtSoTienKhachTra.getText() != null) {
            try {
                // Lấy giá trị từ các ô JTextField
                NumberFormat numberFormat = NumberFormat.getInstance();

                double tongTien;
                double khachDua;
                try {
                    tongTien = numberFormat.parse(txtTongTienEnd.getText()).doubleValue();
                    khachDua = numberFormat.parse(txtSoTienKhachTra.getText()).doubleValue();
                    double tienThua = khachDua - tongTien;
                    txtTienThua.setText(String.valueOf(tienThua));
                } catch (ParseException ex) {
                    Logger.getLogger(FormBill.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Tính toán và hiển thị tiền thừ
            } catch (NumberFormatException ex) {

            }
        }
    }
    
    public void excessMoney2() {
        txtTienKhachChuyenKhoan.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Gọi phương thức để tự động tính toán khi người dùng nhập
                calculateChange2();
            }
        });

        // Phương thức để tự động tính toán tiền thừa
        // Được gọi từ sự kiện nhấn phím Enter trong ô txtKhachDua       
    }

    private void calculateChange2() {
        if (txtTienKhachChuyenKhoan.getText() != null) {
            try {
                // Lấy giá trị từ các ô JTextField
                NumberFormat numberFormat = NumberFormat.getInstance();

                double tongTien;
                double khachDua;
                try {
                    tongTien = numberFormat.parse(txtTongTienEnd.getText()).doubleValue();
                    khachDua = numberFormat.parse(txtTienKhachChuyenKhoan.getText()).doubleValue();
                    double tienThua = khachDua - tongTien;
                    txtTienThua.setText(String.valueOf(tienThua));
                } catch (ParseException ex) {
                    Logger.getLogger(FormBill.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Tính toán và hiển thị tiền thừ
            } catch (NumberFormatException ex) {

            }
        }
    }
    public void theAmountTheCustomerGives() {
        txtSoTienKhachTra.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Khi nhận được focus, kiểm tra xem nếu là giá trị mặc định, thì xóa nó.
                if (txtSoTienKhachTra.getText().equals("")) {
                    txtSoTienKhachTra.setText("0");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Khi mất focus và trống rỗng, thiết lập giá trị mặc định.
                if (txtSoTienKhachTra.getText().isEmpty()) {
                    txtSoTienKhachTra.setText("0");
                }
            }
        });
    }
}
