/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package udpm.fpt.form;

import com.raven.datechooser.DateBetween;
import com.raven.datechooser.DateChooser;
import com.raven.datechooser.listener.DateChooserAction;
import com.raven.datechooser.listener.DateChooserAdapter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import static udpm.fpt.Application.getBean;
import udpm.fpt.config.DBConnect1;
import udpm.fpt.service.ThongKeService;
import udpm.serviceDelivery.ThongKeModel;
import udpm.fpt.repository.IBill_Repository;

/**
 *
 * @author PHONG PC
 */
public class ThongKeForm extends javax.swing.JPanel {

    IBill_Repository itBill = getBean(IBill_Repository.class);
    NumberFormat numberFormat = NumberFormat.getInstance();
    private ThongKeService tksv = new ThongKeService();
    DBConnect1 db = new DBConnect1();
    private DateChooser dcNgayBatDau = new DateChooser();
    SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat simple2 = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates new form ThongKeForm
     */
    public ThongKeForm() {
        initComponents();
        tableSanPham(tksv.getALL());
        bieuDo(2024);
        System.out.println(itBill.tongTien7Ngay(new Date(), new Date(), "Đã thanh toán") == null ? "0" : itBill.tongTien7Ngay(new Date(), new Date(), "Đã thanh toán"));
        txtDoanhThuTheoNgay.setText(String.valueOf(numberFormat.format(itBill.tongTien7Ngay(new Date(), new Date(), "Đã thanh toán") == null ? 0 : itBill.tongTien7Ngay(new Date(), new Date(), "Đã thanh toán"))) + " VND");
        txtDoanhThuThang.setText(String.valueOf(numberFormat.format(itBill.tongTienTheoThang() == null ? 0 : itBill.tongTienTheoThang())) + " VND");
        txtTongHoaDon.setText(String.valueOf(numberFormat.format(itBill.tongHoaDon(new Date(), new Date()))));
        txtHoaDonThanhCong.setText(numberFormat.format(itBill.tongHoaDonThanhCong(new Date(), new Date(), "Đã thanh toán")));
        //txtTongHoaDon.setText(String.valueOf(simple2.format(new Date())));
        dateChooser();
    }

    public void dateChooser() {
        dcNgayBatDau.setTextField(txtLocNgay);
        dcNgayBatDau.setDateSelectionMode(DateChooser.DateSelectionMode.BETWEEN_DATE_SELECTED);
        dcNgayBatDau.setLabelCurrentDayVisible(false);
        dcNgayBatDau.setDateFormat(simple);
        dcNgayBatDau.addActionDateChooserListener(new DateChooserAdapter() {
            @Override
            public void dateBetweenChanged(DateBetween date, DateChooserAction action) {
                txtTongHoaDon.setText(String.valueOf(numberFormat.format(itBill.tongHoaDon(date.getFromDate(), date.getToDate()))));
                txtHoaDonThanhCong.setText(numberFormat.format(itBill.tongHoaDonThanhCong(date.getFromDate(), date.getToDate(), "Đã thanh toán")));
                txtDoanhThuTheoNgay.setText(String.valueOf(numberFormat.format(itBill.tongTien7Ngay(date.getFromDate(), date.getToDate(), "Đã thanh toán") == null ? 0 : itBill.tongTien7Ngay(date.getFromDate(), date.getToDate(), "Đã thanh toán"))) + " VND");
                System.out.println(itBill.tongTien7Ngay(date.getFromDate(), date.getToDate(), "Đã thanh toán") + "dd");
                //loadDataBetWeenAndFillTableDeli(date.getFromDate(), date.getToDate());
            }
        });
    }

    public void tableSanPham(List<ThongKeModel> list) {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        for (ThongKeModel bill : list) {
            Object[] rowData = {
                bill.getId(),
                bill.getTenSanPham(),
                bill.getSoLuong(),
                bill.getSoLuongDaMua(),
                bill.getThanhTien(),};
            model.addRow(rowData);
        }
    }

    public void bieuDo(int nam) {
        // Tạo dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        txtDoanhThuCaNam.setText(String.valueOf(numberFormat.format(itBill.tongTienTheoNam(nam) == null ? 0 : itBill.tongTienTheoNam(nam))) + " VND");
        dataset.addValue(itBill.tongTienTheoThang1(nam), "Sales", "T1");
        dataset.addValue(itBill.tongTienTheoThang2(nam), "Sales", "T2");
        dataset.addValue(itBill.tongTienTheoThang3(nam), "Sales", "T3");
        dataset.addValue(itBill.tongTienTheoThang4(nam), "Sales", "T4");
        dataset.addValue(itBill.tongTienTheoThang5(nam), "Sales", "T5");
        dataset.addValue(itBill.tongTienTheoThang6(nam), "Sales", "T6");
        dataset.addValue(itBill.tongTienTheoThang7(nam), "Sales", "T7");
        dataset.addValue(itBill.tongTienTheoThang8(nam), "Sales", "T8");
        dataset.addValue(itBill.tongTienTheoThang9(nam), "Sales", "T9");
        dataset.addValue(itBill.tongTienTheoThang10(nam), "Sales", "T10");
        dataset.addValue(itBill.tongTienTheoThang11(nam), "Sales", "T11");
        dataset.addValue(itBill.tongTienTheoThang12(nam), "Sales", "T12");

        // Tạo biểu đồ
        JFreeChart chart = ChartFactory.createBarChart(
                "Doanh thu năm " + nam, // Tiêu đề biểu đồ
                "Month", // Nhãn trục X
                "Doanh thu năm ($)", // Nhãn trục Y
                dataset, // Dataset
                PlotOrientation.VERTICAL,
                false, // Không có legend
                true, // Hiển thị tooltips
                false // Không có URLs
        );

        // Tạo ChartPanel từ biểu đồ
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 300));
        // Tạo JFrame và JPanel
        // Thêm ChartPanel vào JPanel
        jPanelBieuDo.add(chartPanel);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        kkdd = new javax.swing.JLabel();
        txtTongHoaDon = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtHoaDonThanhCong = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDoanhThuTheoNgay = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtDoanhThuCaNam = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtDoanhThuThang = new javax.swing.JLabel();
        cbbNam = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        txtLocNgay = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jPanelBieuDo = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel2.setText("Hóa đơn");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/invoice.png"))); // NOI18N

        kkdd.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        kkdd.setText("Tổng hóa đơn :");

        txtTongHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtTongHoaDon.setText("jLabel1");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Hóa đơn thành công :");

        txtHoaDonThanhCong.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtHoaDonThanhCong.setText("jLabel7");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtHoaDonThanhCong)
                        .addGap(0, 37, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(kkdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTongHoaDon)
                        .addGap(49, 49, 49))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel2)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kkdd)
                    .addComponent(txtTongHoaDon))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtHoaDonThanhCong))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel3.setText("Doanh thu ngày");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/money.png"))); // NOI18N

        txtDoanhThuTheoNgay.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtDoanhThuTheoNgay.setText("jLabel10");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addGap(47, 47, 47))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(txtDoanhThuTheoNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel6))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel3)))
                .addGap(32, 32, 32)
                .addComponent(txtDoanhThuTheoNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(97, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 255, 255));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel14.setText("Doanh thu cả năm");

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1Year.png"))); // NOI18N

        txtDoanhThuCaNam.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtDoanhThuCaNam.setText("jLabel11");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addContainerGap(32, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtDoanhThuCaNam, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel14)))
                .addGap(34, 34, 34)
                .addComponent(txtDoanhThuCaNam, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(204, 255, 255));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel16.setText("Doanh thu tháng");

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1Month.png"))); // NOI18N

        txtDoanhThuThang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtDoanhThuThang.setText("jLabel11");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addContainerGap(48, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtDoanhThuThang, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel16)))
                .addGap(31, 31, 31)
                .addComponent(txtDoanhThuThang, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cbbNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2022", "2023", "2024" }));
        cbbNam.setSelectedIndex(2);
        cbbNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbNamActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        txtLocNgay.setText("jTextField1");
        txtLocNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLocNgayActionPerformed(evt);
            }
        });

        jPanelBieuDo.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBieuDo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBieuDo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Biểu đồ doanh thu", jPanel11);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng còn", "Số lượng đã bán", "Số tiền bán"
            }
        ));
        jScrollPane1.setViewportView(tblSanPham);

        jLabel1.setText("Số sản phẩm bán được trong năm");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1476, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 23, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Số sản phẩm bán được", jPanel10);

        jLabel7.setText("Ngày thống kê:");

        jLabel8.setText("Năm thống kê");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtLocNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbbNam, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1486, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 8, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbbNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtLocNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbbNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbNamActionPerformed
        int namv = Integer.parseInt(cbbNam.getSelectedItem().toString());
        txtDoanhThuCaNam.setText(String.valueOf(numberFormat.format((itBill.tongTienTheoNam(namv) == null ? 0 : itBill.tongTienTheoNam(namv)))) + " VND");
    }//GEN-LAST:event_cbbNamActionPerformed

    private void txtLocNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLocNgayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLocNgayActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbbNam;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelBieuDo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel kkdd;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JLabel txtDoanhThuCaNam;
    private javax.swing.JLabel txtDoanhThuThang;
    private javax.swing.JLabel txtDoanhThuTheoNgay;
    private javax.swing.JLabel txtHoaDonThanhCong;
    private javax.swing.JTextField txtLocNgay;
    private javax.swing.JLabel txtTongHoaDon;
    // End of variables declaration//GEN-END:variables
}
