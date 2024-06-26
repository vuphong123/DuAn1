package udpm.fpt.form;

import com.raven.datechooser.DateChooser;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.awt.print.PrinterException;
import udpm.fpt.Utitlity.DiscountCalculator;
import udpm.fpt.component.IMG;
import udpm.fpt.component.MessagePanel;

import udpm.fpt.component.Replenishment;
import udpm.fpt.component.SearchBarcode;
//import udpm.fpt.component.UpdateProduct;
import udpm.fpt.main.Main;
import udpm.fpt.model.Flavor;
import udpm.fpt.model.SANPHAM;
import udpm.fpt.model.PackagingSpecification;
import udpm.fpt.model.ProductInfo;
import udpm.fpt.model.ProductInfoByCriteria;
import udpm.fpt.model.Unit;
import udpm.fpt.model.User;
import udpm.fpt.service.ProductService;
import udpm.fpt.swing.NumberOnlyFilter;
import udpm.fpt.swing.table.TableCustom;

/**
 * @author NONG HOANG VU
 */
public class ProductForm extends javax.swing.JPanel {

    private DefaultTableModel tblModel;
    private final ProductService list;
    private List<ProductInfo> temp;
    private final User user;
    private final Main main;

    public ProductForm(User user, Main main) {
        initComponents();
        initComponents();
        this.user = user;
        this.list = new ProductService();
        this.main = main;
        initProduct();
        rdoAll.requestFocus();
    }

    public void initProduct() {
        TableCustom.apply(jScrollPane1, TableCustom.TableType.MULTI_LINE);
        this.temp = new ArrayList<>();
        setTxtEntryToDate();
        setNumberTexfield();
        loadComboBox();
        loadDataAndFillTable(loadTableType.ALL);
        setRoleControl();
    }

    /*-------------------Set role------------------*/
    private void setRoleControl() {
        if (this.user.getRole().equalsIgnoreCase("Admin")) {
            btnNew.setEnabled(true);
            btnHidden.setEnabled(true);
            btnUpdate.setEnabled(true);
            btnReplenishment.setEnabled(true);
        } else {
            btnNew.setEnabled(false);
            btnHidden.setEnabled(false);
            btnUpdate.setEnabled(false);
            btnReplenishment.setEnabled(false);
        }
    }

    /*-------------------Format data------------------*/
//    private Integer priceUpdate(Long id, Integer price) {
//        for (SaleMilk sm : this.list.getPercentSale()) {
//            if (Objects.equals(id, sm.getMilk().getId())) {
//                return new DiscountCalculator().calculateDiscountedPrice(price, sm.getPercent_decrease());
//            }
//        }
//        return price;
//    }

    private String setSelectedIndex(int number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(number);
    }

    public String removeTimeUsingDateTimeFormatter(String inputDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime dateTime = LocalDateTime.parse(inputDate, inputFormatter);
        return dateTime.format(outputFormatter);
    }

    public Date getDateFormatSQL(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date utilDate = dateFormat.parse(date);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            return sqlDate;
        } catch (ParseException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    /*---------------Set format------------------*/
    private void setTxtEntryToDate() {
        DateChooser dateChooser = new DateChooser();
        dateChooser.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        dateChooser.setLabelCurrentDayVisible(false);
        dateChooser.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
        dateChooser.setTextField(txtEntryDate);
    }

    private void setNumberTexfield() {
        ((AbstractDocument) txtQuantityMin.getDocument()).setDocumentFilter(new NumberOnlyFilter());
        ((AbstractDocument) txtQuantityMax.getDocument()).setDocumentFilter(new NumberOnlyFilter());
        ((AbstractDocument) txtPriceMin.getDocument()).setDocumentFilter(new NumberOnlyFilter());
        ((AbstractDocument) txtPriceMax.getDocument()).setDocumentFilter(new NumberOnlyFilter());
        txtEntryDate.setText("00-00-0000");
    }

    /*---------------------------------Set Data------------------------------------------------*/
    private void loadComboBox() {
//        loadDataAndFillFlavor();
//        loadDataAndFillPackagingSpecification();
//        loadDataAndFillUnit();
        loadTypeCheckExpiry();
        loadTypeSearch();
    }

    public void setData(String data) {
        lbId.setText(data);
        loadDataAndFillTable(loadTableType.ALL);
//        loadDataAndFillFlavor();
//        loadDataAndFillPackagingSpecification();
    }

    private void setImange(String url) {
        lbproductgallery.setText(null);
        try {
            int labelWidth = lbproductgallery.getWidth();
            int labelHeight = lbproductgallery.getHeight();
            ImageIcon originalIcon = new javax.swing.ImageIcon(
                    Objects.requireNonNull(getClass().getResource("/ProductGallery/" + url)));
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            lbproductgallery.setIcon(scaledIcon);
        } catch (Exception e) {
            lbproductgallery.setIcon(null);
            lbproductgallery.setText("Image not found");
        }
    }

    private void setLabel() {
     
    }

    private void clearCatelogy() {
        cbbSearchType.setSelectedIndex(0);
        txtSearch.setText("");
        cbbTaste.setSelectedIndex(0);
        cbbPackagingSpecification.setSelectedIndex(0);
        txtVolume.setText("");
        cbbUnit.setSelectedIndex(0);
        txtEntryDate.setText("00-00-000");
        cbbCheckExpiry.setSelectedIndex(0);
        if (!txtQuantityMin.getText().isBlank()) {
            txtQuantityMin.setText("0");
        }
        if (!txtQuantityMax.getText().isBlank()) {
            txtQuantityMax.setText("0");
        }
        if (!txtPriceMin.getText().isBlank()) {
            txtPriceMin.setText("0");
        }
        if (!txtPriceMax.getText().isBlank()) {
            txtPriceMax.setText("0");
        }
        cbbCheckExpiry.setSelectedIndex(0);
    }

    public void setBarcodeSearch(String barcode) {
        txtSearch.setText(barcode);
    }

    /*-------------------------------------------Run stream processing-------------------------------------------*/
    public enum loadTableType {
        ALL, ACTIVE, OUTOFSTOCK, HIDDEN
    }

    /* 1. Load to table */
    public void loadDataAndFillTable(loadTableType type) {
//        CompletableFuture<List<ProductInfo>> future = this.list.loadAsync();
//        future.thenAcceptAsync((var data) -> {
//            SwingUtilities.invokeLater(() -> {
//                switch (type) {
//                    case ALL -> updateTableAll(data);
//                    case ACTIVE -> updateTableActive(data);
//                    case OUTOFSTOCK -> updateTableOutOfStock(data);
//                    case HIDDEN -> updateTableHidden(data);
//                    default -> throw new AssertionError();
//                }
//            });
//        }).exceptionally(throwable -> {
//            throwable.printStackTrace(System.out);
//            return null;
//        });
    }

//    private void updateTableAll(List<ProductInfo> data) {
//        this.temp.clear();
//        clearCatelogy();
//        rdoAll.setSelected(true);
//        tblModel = (DefaultTableModel) tblProduct.getModel();
//        tblModel.setRowCount(0);
//        for (ProductInfo prd : data) {
//            if (!prd.getMilk().getIsDelete()) {
//                this.temp.add(prd);
//                Object[] rowData = {
//                        prd.getMilk().getId(),
//                        prd.getMilk().getProduct_name(),
//                        prd.getFlavor().getTaste(),
//                        prd.getVolume() + " " + prd.getUnit().getMeasurement_unit(),
//                        prd.getMilk().getAmount() == 0 ? "Out of stock" : prd.getMilk().getAmount(),
//                        prd.getCreate_at(),
//                        setSelectedIndex(priceUpdate(prd.getMilk().getId(), prd.getMilk().getPrice())) + " VND"
//                };
//                tblModel.addRow(rowData);
//            }
//        }
//        lbCountPorduct.setText(String.valueOf(this.temp.size()));
//    }

    private void updateTableActive(List<ProductInfo> data) {
        
    }

    private void updateTableOutOfStock(List<ProductInfo> data) {
        
    }

    private void updateTableHidden(List<ProductInfo> data) {
        
    }

    /* 2. Load to flavor */
//    public void loadDataAndFillFlavor() {
//        CompletableFuture<List<Flavor>> future = this.list.loadFlavor();
//        future.thenAcceptAsync(data -> {
//            SwingUtilities.invokeLater(() -> {
//                updateFlavor(data);
//            });
//        }).exceptionally(throwable -> {
//            throwable.printStackTrace(System.out);
//            return null;
//        });
//    }

    private void updateFlavor(List<Flavor> data) {
        DefaultComboBoxModel<Flavor> cbbModel = new DefaultComboBoxModel<>();
        cbbTaste.removeAll();
        cbbTaste.setModel(cbbModel);
        Flavor flavorAll = new Flavor();
        flavorAll.setTaste("All");
        cbbModel.addElement(flavorAll);
        for (Flavor flavor : data) {
            cbbModel.addElement(flavor);
        }
    }

    /* 3. Load to packaging specification */
//    public void loadDataAndFillPackagingSpecification() {
//        CompletableFuture<List<PackagingSpecification>> future = this.list.loadPackagingSpecification();
//        future.thenAcceptAsync(data -> {
//            SwingUtilities.invokeLater(() -> {
//                updatePackagingSpecification(data);
//            });
//        }).exceptionally(throwable -> {
//            throwable.printStackTrace(System.out);
//            return null;
//        });
//    }

    private void updatePackagingSpecification(List<PackagingSpecification> data) {
        DefaultComboBoxModel<PackagingSpecification> cbbModel = new DefaultComboBoxModel<>();
        cbbPackagingSpecification.removeAll();
        cbbPackagingSpecification.setModel(cbbModel);
        PackagingSpecification packagingSpecificationAll = new PackagingSpecification();
        packagingSpecificationAll.setPackaging_type("All");
        cbbModel.addElement(packagingSpecificationAll);
        for (PackagingSpecification specification : data) {
            cbbModel.addElement(specification);
        }
    }/* 4. Load to unit */

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
        cbbUnit.setModel(cbbModel);
        Unit unitAll = new Unit();
        unitAll.setMeasurement_unit("All");
        cbbModel.addElement(unitAll);
        for (Unit unit : data) {
            cbbModel.addElement(unit);
        }
    }

    private void loadTypeCheckExpiry() {
        String[] months = {
                "All",
                "Valid",
                "Expired"
        };
        for (String s : months) {
            cbbCheckExpiry.addItem(s);
        }
    }

    private void loadTypeSearch() {
        String[] type = {
                "Product's name",
                "Product code"
        };
        for (String s : type) {
            cbbSearchType.addItem(s);
        }
    }

    public void loadDataAndFillSearch() {
        
    }

    private void updateSearch(List<ProductInfo> data) {
        
    }

    public void loadDataAndFillSearchByBarcode() {
       
    }

    private void updateSearchByBarcode(List<ProductInfo> data) {
        
    }

    /*-------------------------------------------Control-------------------------------------------*/
    public void delete() {
        
    }
    private void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    /*-------------------------------------------Validate-------------------------------------------*/
    private Boolean validateSearch() {
        if (!txtQuantityMax.getText().isBlank()) {
            if ((!txtQuantityMin.getText().isBlank()) && Integer.parseInt(txtQuantityMax.getText()) < Integer.parseInt(txtQuantityMin.getText())) {
                this.main.notificationShowWARNING("One side should exceed the other.");
                txtQuantityMax.requestFocus();
                return true;
            }
        } else if (!txtPriceMax.getText().isBlank()) {
            if ((!txtPriceMin.getText().isBlank()) && Integer.parseInt(txtPriceMax.getText()) < Integer.parseInt(txtPriceMin.getText())) {
                this.main.notificationShowWARNING("One side should exceed the other.");
                txtPriceMax.requestFocus();
                return true;
            }
        }else if(!txtVolume.getText().isBlank()){
            try {
                Double.valueOf(txtVolume.getText().trim());
            }catch (NumberFormatException numberFormatException){
                txtVolume.requestFocus();
                this.main.notificationShowWARNING("The volume is not in the correct format.");
                return true;
            }
        }
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        txtSearch = new udpm.fpt.swing.TextField();
        txtVolume = new udpm.fpt.swing.TextField();
        txtEntryDate = new udpm.fpt.swing.TextField();
        jPanel4 = new javax.swing.JPanel();
        txtQuantityMin = new udpm.fpt.swing.TextField();
        txtQuantityMax = new udpm.fpt.swing.TextField();
        jPanel5 = new javax.swing.JPanel();
        txtPriceMin = new udpm.fpt.swing.TextField();
        txtPriceMax = new udpm.fpt.swing.TextField();
        button2 = new udpm.fpt.swing.Button();
        button5 = new udpm.fpt.swing.Button();
        cbbSearchType = new udpm.fpt.swing.Combobox();
        cbbTaste = new udpm.fpt.swing.Combobox();
        cbbPackagingSpecification = new udpm.fpt.swing.Combobox();
        cbbUnit = new udpm.fpt.swing.Combobox();
        cbbCheckExpiry = new udpm.fpt.swing.Combobox();
        btnScanBarcode = new udpm.fpt.swing.Button();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        lbproductgallery = new javax.swing.JLabel();
        rdoAll = new javax.swing.JRadioButton();
        rdoActive = new javax.swing.JRadioButton();
        rdoOutOfStock = new javax.swing.JRadioButton();
        rdoHidden = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbId = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbName = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbPrice = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbAmount = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbExpirationDate = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbProductionDate = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lbProvider = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        lbTaste = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lbPackagingSpecification = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lbVolume = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lbBrand = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        lbOrigin = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lbComposition = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        lbDescrription = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbCreateBy = new javax.swing.JLabel();
        lbBarcode = new javax.swing.JTextField();
        btnNew = new udpm.fpt.swing.Button();
        btnUpdate = new udpm.fpt.swing.Button();
        btnReplenishment = new udpm.fpt.swing.Button();
        lbCountPorduct = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnHidden = new udpm.fpt.swing.Button();
        imageAvatar1 = new udpm.fpt.swing.ImageAvatar();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Catelogy", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(179, 179, 179))); // NOI18N

        txtSearch.setLabelText("Please input at least first 2 characters of word");

        txtVolume.setLabelText("Volume");

        txtEntryDate.setLabelText("Entry date");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Stock Quantity", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(178, 178, 178))); // NOI18N

        txtQuantityMin.setLabelText("Minimum");

        txtQuantityMax.setLabelText("Max");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtQuantityMin, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtQuantityMax, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtQuantityMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantityMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Price range", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(178, 178, 178))); // NOI18N

        txtPriceMin.setLabelText("Minimum");

        txtPriceMax.setLabelText("Max");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtPriceMin, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addComponent(txtPriceMax, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPriceMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPriceMax, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        button2.setBackground(new java.awt.Color(102, 204, 255));
        button2.setForeground(new java.awt.Color(255, 255, 255));
        button2.setText("Apply Filters");
        button2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        button5.setBackground(new java.awt.Color(255, 102, 102));
        button5.setForeground(new java.awt.Color(255, 255, 255));
        button5.setText("Retype");
        button5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button5ActionPerformed(evt);
            }
        });

        cbbSearchType.setBorder(null);
        cbbSearchType.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cbbSearchType.setLabeText("");
        cbbSearchType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbSearchTypeItemStateChanged(evt);
            }
        });

        cbbTaste.setLabeText("Taste");

        cbbPackagingSpecification.setLabeText("Packaging Specification");

        cbbUnit.setLabeText("Unit");

        cbbCheckExpiry.setLabeText("Check Entry ");

        btnScanBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/barcode-scanner.png"))); // NOI18N
        btnScanBarcode.setToolTipText("");
        btnScanBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScanBarcodeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbbSearchType, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnScanBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbbTaste, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtVolume, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbPackagingSpecification, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(cbbUnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtEntryDate, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbCheckExpiry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnScanBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbSearchType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbTaste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbPackagingSpecification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEntryDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbCheckExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Product's name", "Taste", "Volume", "Stock quantity", "Entry date", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProduct);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Image", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(179, 177, 177))); // NOI18N

        lbproductgallery.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbproductgallery.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbproductgallery.setText("Image not found");
        lbproductgallery.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbproductgalleryMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbproductgallery, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(lbproductgallery, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        rdoAll.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoAll);
        rdoAll.setSelected(true);
        rdoAll.setText("All");
        rdoAll.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rdoAllItemStateChanged(evt);
            }
        });

        rdoActive.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoActive);
        rdoActive.setText("Active");
        rdoActive.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rdoActiveItemStateChanged(evt);
            }
        });

        rdoOutOfStock.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoOutOfStock);
        rdoOutOfStock.setText("Out Of Stock");
        rdoOutOfStock.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rdoOutOfStockItemStateChanged(evt);
            }
        });

        rdoHidden.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoHidden);
        rdoHidden.setText("Hidden");
        rdoHidden.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rdoHiddenItemStateChanged(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Info", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(178, 178, 178))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("ID:");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        lbId.setText("0000000");
        jPanel3.add(lbId, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, -1, -1));

        jLabel5.setText("Name:");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        lbName.setText("Product's name");
        jPanel3.add(lbName, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 150, -1));

        jLabel7.setText("Price:");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, -1));

        lbPrice.setText("000,000 VND");
        jPanel3.add(lbPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, -1, -1));

        jLabel9.setText("Amount:");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        lbAmount.setText("0");
        jPanel3.add(lbAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 60, -1));

        jLabel11.setText("Production Date:");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        lbExpirationDate.setText("00-00-0000");
        jPanel3.add(lbExpirationDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 70, -1));

        jLabel13.setText("Expiration Date:");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, -1, -1));

        lbProductionDate.setText("00-00-0000");
        jPanel3.add(lbProductionDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 70, -1));

        jLabel15.setText("Provider:");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, -1));

        lbProvider.setText("Null");
        jPanel3.add(lbProvider, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 110, -1));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel3.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 10, 150));

        jLabel17.setText("Taste:");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, -1, -1));

        lbTaste.setText("Null");
        jPanel3.add(lbTaste, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, -1, -1));

        jLabel19.setText("Packaging Type:");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, -1, -1));

        lbPackagingSpecification.setText("Null");
        jPanel3.add(lbPackagingSpecification, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, 80, -1));

        jLabel21.setText("Volume:");
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, -1, -1));

        lbVolume.setText("Null Null");
        jPanel3.add(lbVolume, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, 130, -1));

        jLabel23.setText("Brand:");
        jPanel3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, -1, -1));

        lbBrand.setText("Null");
        jPanel3.add(lbBrand, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 80, 130, -1));

        jLabel25.setText("Origin:");
        jPanel3.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, -1, -1));

        lbOrigin.setText("Null");
        jPanel3.add(lbOrigin, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, 150, -1));

        jLabel27.setText("Composition:");
        jPanel3.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 120, -1, -1));

        lbComposition.setText("Null");
        jPanel3.add(lbComposition, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 120, 140, -1));

        jLabel29.setText("Description:");
        jPanel3.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 140, -1, -1));

        lbDescrription.setText("Null");
        jPanel3.add(lbDescrription, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 140, 150, -1));

        jLabel1.setText("Barcode:");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        jLabel6.setText("Create by:");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 160, -1, -1));

        lbCreateBy.setText("Null");
        jPanel3.add(lbCreateBy, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 160, 100, -1));

        lbBarcode.setEditable(false);
        lbBarcode.setText("Null");
        lbBarcode.setBorder(null);
        lbBarcode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbBarcodeMouseClicked(evt);
            }
        });
        jPanel3.add(lbBarcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 150, -1));

        btnNew.setBackground(new java.awt.Color(102, 204, 255));
        btnNew.setForeground(new java.awt.Color(255, 255, 255));
        btnNew.setText("New");
        btnNew.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(102, 204, 255));
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnReplenishment.setBackground(new java.awt.Color(102, 204, 255));
        btnReplenishment.setForeground(new java.awt.Color(255, 255, 255));
        btnReplenishment.setText("Replenishment");
        btnReplenishment.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReplenishment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReplenishmentActionPerformed(evt);
            }
        });

        lbCountPorduct.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbCountPorduct.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbCountPorduct.setText("0");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Products");

        btnHidden.setBackground(new java.awt.Color(255, 102, 102));
        btnHidden.setForeground(new java.awt.Color(255, 255, 255));
        btnHidden.setText("Hidden");
        btnHidden.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHidden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHiddenActionPerformed(evt);
            }
        });

        imageAvatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/cowGif.gif"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rdoAll)
                                .addGap(18, 18, 18)
                                .addComponent(rdoActive)
                                .addGap(18, 18, 18)
                                .addComponent(rdoOutOfStock)
                                .addGap(18, 18, 18)
                                .addComponent(rdoHidden))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbCountPorduct, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel4)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnReplenishment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnHidden, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                    .addComponent(imageAvatar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(31, 31, 31)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbCountPorduct, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4))
                                    .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnReplenishment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnHidden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rdoAll)
                                    .addComponent(rdoActive)
                                    .addComponent(rdoOutOfStock)
                                    .addComponent(rdoHidden)))
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnReplenishmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReplenishmentActionPerformed
        ProductInfo pi = this.temp.get(tblProduct.getSelectedRow());
        new Replenishment(this, pi, this.user).setVisible(true);
    }//GEN-LAST:event_btnReplenishmentActionPerformed

    private void btnScanBarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScanBarcodeActionPerformed
        SearchBarcode searchBarcode = new SearchBarcode(this);
        searchBarcode.setVisible(true);
    }//GEN-LAST:event_btnScanBarcodeActionPerformed

    private void cbbSearchTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbSearchTypeItemStateChanged
        if (cbbSearchType.getSelectedIndex() == 1)
            btnScanBarcode.setEnabled(true);
        else
            btnScanBarcode.setEnabled(false);
    }//GEN-LAST:event_cbbSearchTypeItemStateChanged

    private void lbBarcodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbBarcodeMouseClicked
        copyToClipboard(lbBarcode.getText());
        this.main.notificationShowSUCCESS("Barcode has been copied to clipboard");
    }//GEN-LAST:event_lbBarcodeMouseClicked

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNewActionPerformed
//        NewProduct prd = new NewProduct(this, this.user);
//        prd.setVisible(true);
    }// GEN-LAST:event_btnNewActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnUpdateActionPerformed
        if (tblProduct.getSelectedRow() < 0) {
            return;
        }
        ProductInfo pi = temp.get(tblProduct.getSelectedRow());
//        UpdateProduct prd = new UpdateProduct(this, pi, pi.getUser(), this.user);
//        prd.setVisible(true);
    }// GEN-LAST:event_btnUpdateActionPerformed

    private void lbproductgalleryMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_lbproductgalleryMouseClicked
        try {
            if (lbproductgallery.getIcon() == null) {
                new IMG("ImageNull.png").setVisible(true);
            } else {
//                ProductInfo pi = this.temp.get(tblProduct.getSelectedRow());
//                new IMG(pi.getMilk().getImg()).setVisible(true);
            }
        } catch (Exception e) {
        }
    }// GEN-LAST:event_lbproductgalleryMouseClicked

    private void tblProductMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblProductMouseClicked
        try {
            setLabel();
        } catch (Exception e) {
        }
    }// GEN-LAST:event_tblProductMouseClicked

    private void btnHiddenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHiddenActionPerformed
        if (tblProduct.getSelectedRow() < 0) {
            return;
        }
        MessagePanel msg = new MessagePanel();
        msg.setTitle("Are you sure you want to delete this?");
        msg.setMessage("If you hidden this product, the product will be moved to the storage and can be restored.");
        msg.setResultCallback((Boolean result) -> {
            if (result) {
                delete();
            }
        });
        msg.setVisible(true);
    }// GEN-LAST:event_btnHiddenActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_button2ActionPerformed
        tblModel = (DefaultTableModel) tblProduct.getModel();
        if (!validateSearch()) {
            tblModel.setRowCount(0);
            if (cbbSearchType.getSelectedIndex() == 0) {
                loadDataAndFillSearch();
            } else {
                loadDataAndFillSearchByBarcode();
            }
        }
    }// GEN-LAST:event_button2ActionPerformed

    private void button5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_button5ActionPerformed
        clearCatelogy();
        loadDataAndFillTable(loadTableType.ALL);
        rdoAll.setSelected(true);
        
    }// GEN-LAST:event_button5ActionPerformed

    private void rdoAllItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_rdoAllItemStateChanged
        if (rdoAll.isSelected()) {
            clearCatelogy();
            loadDataAndFillTable(loadTableType.ALL);
        }
    }// GEN-LAST:event_rdoAllItemStateChanged

    private void rdoActiveItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_rdoActiveItemStateChanged
        if (rdoActive.isSelected()) {
            clearCatelogy();
            loadDataAndFillTable(loadTableType.ACTIVE);
        }
    }// GEN-LAST:event_rdoActiveItemStateChanged

    private void rdoOutOfStockItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_rdoOutOfStockItemStateChanged
        if (rdoOutOfStock.isSelected()) {
            clearCatelogy();
            loadDataAndFillTable(loadTableType.OUTOFSTOCK);
        }
    }// GEN-LAST:event_rdoOutOfStockItemStateChanged

    private void rdoHiddenItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_rdoHiddenItemStateChanged
        if (rdoHidden.isSelected()) {
            clearCatelogy();
            loadDataAndFillTable(loadTableType.HIDDEN);
        }
    }// GEN-LAST:event_rdoHiddenItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.Button btnHidden;
    private udpm.fpt.swing.Button btnNew;
    private udpm.fpt.swing.Button btnReplenishment;
    private udpm.fpt.swing.Button btnScanBarcode;
    private udpm.fpt.swing.Button btnUpdate;
    private udpm.fpt.swing.Button button2;
    private udpm.fpt.swing.Button button5;
    private javax.swing.ButtonGroup buttonGroup1;
    private udpm.fpt.swing.Combobox cbbCheckExpiry;
    private udpm.fpt.swing.Combobox cbbPackagingSpecification;
    private udpm.fpt.swing.Combobox cbbSearchType;
    private udpm.fpt.swing.Combobox cbbTaste;
    private udpm.fpt.swing.Combobox cbbUnit;
    private udpm.fpt.swing.ImageAvatar imageAvatar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbAmount;
    private javax.swing.JTextField lbBarcode;
    private javax.swing.JLabel lbBrand;
    private javax.swing.JLabel lbComposition;
    private javax.swing.JLabel lbCountPorduct;
    private javax.swing.JLabel lbCreateBy;
    private javax.swing.JLabel lbDescrription;
    private javax.swing.JLabel lbExpirationDate;
    private javax.swing.JLabel lbId;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbOrigin;
    private javax.swing.JLabel lbPackagingSpecification;
    private javax.swing.JLabel lbPrice;
    private javax.swing.JLabel lbProductionDate;
    private javax.swing.JLabel lbProvider;
    private javax.swing.JLabel lbTaste;
    private javax.swing.JLabel lbVolume;
    private javax.swing.JLabel lbproductgallery;
    private javax.swing.JRadioButton rdoActive;
    private javax.swing.JRadioButton rdoAll;
    private javax.swing.JRadioButton rdoHidden;
    private javax.swing.JRadioButton rdoOutOfStock;
    private javax.swing.JTable tblProduct;
    private udpm.fpt.swing.TextField txtEntryDate;
    private udpm.fpt.swing.TextField txtPriceMax;
    private udpm.fpt.swing.TextField txtPriceMin;
    private udpm.fpt.swing.TextField txtQuantityMax;
    private udpm.fpt.swing.TextField txtQuantityMin;
    private udpm.fpt.swing.TextField txtSearch;
    private udpm.fpt.swing.TextField txtVolume;
    // End of variables declaration//GEN-END:variables
}
