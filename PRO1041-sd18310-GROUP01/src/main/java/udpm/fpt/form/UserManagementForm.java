package udpm.fpt.form;

import com.raven.datechooser.DateChooser;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
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
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import udpm.fpt.Utitlity.BcryptHash;
import udpm.fpt.main.Main;
import udpm.fpt.model.Salary;
import udpm.fpt.model.User;
import udpm.fpt.model.UserDetails;
import udpm.fpt.service.HistoryService;
import udpm.fpt.service.SalaryService;
import udpm.fpt.service.UserService;
import udpm.fpt.swing.NumberOnlyFilter;
import udpm.fpt.swing.table.TableCustom;

/**
 *
 * @author BinhQuoc
 */
public class UserManagementForm extends javax.swing.JPanel {

    DefaultTableModel tblModel = new DefaultTableModel();
    DefaultTableModel tblModelSalary = new DefaultTableModel();

    UserService userService = new UserService();
    SalaryService salaryService = new SalaryService();
    HistoryService historyService = new HistoryService();

    private final Main main;
    private String imgName = null;
    private User user;
    private List<UserDetails> tempList;
    private UserDetails _USER_DETAILS;

    public UserManagementForm(User user, Main main) {
        initComponents();
        this.user = user;
        this.main = main;
        this.tempList = new ArrayList<>();
        TableCustom.apply(jScrollPane1, TableCustom.TableType.MULTI_LINE);
        TableCustom.apply(jScrollPane4, TableCustom.TableType.MULTI_LINE);
        ((AbstractDocument) txtPhonenumber.getDocument()).setDocumentFilter(new NumberOnlyFilter());
        ((AbstractDocument) txtCitizenID.getDocument()).setDocumentFilter(new NumberOnlyFilter());
        ((AbstractDocument) txtAmount.getDocument()).setDocumentFilter(new NumberOnlyFilter());
        initTable();
        loadDataToTable();
        loadSalaryToTable();
        loadDataAndFillSalary();
        if (txtUsername.getText().isBlank()) {
            txtUsername.setEditable(true);
        }
        setProductionDate();
    }

    private String fmMoney(int number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(number);
    }

    //Initialize UI
    public void initTable() {
        tblUser.setModel(tblModel);
        String[] title = new String[]{"", "Username", "Role", "Fullname", "Job position"};
        tblModel.setColumnIdentifiers(title);

        tblSalary.setModel(tblModelSalary);
        String[] anotherTitle = new String[]{"", "Type of salary", "Amount"};
        tblModelSalary.setColumnIdentifiers(anotherTitle);
    }

    public void loadDataToTable() {
        tblModel.setRowCount(0);
        this.tempList.clear();
        int i = 1;
        for (UserDetails per : userService.getList()) {
            this.tempList.add(per);
            tblModel.addRow(new Object[]{i++, per.getUser().getUsername(), per.getUser().getRole(), per.getFullname(), per.getJobPosition()});
        }

        lblCountEmployee.setText(((i - 1) == 0 || (i - 1) == 1) ? ((i - 1) + " result") : ((i - 1) + " results"));
    }

    private void setProductionDate() {
        DateChooser dateChooser = new DateChooser();
        dateChooser.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        dateChooser.setLabelCurrentDayVisible(false);
        dateChooser.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        dateChooser.setTextField(txtBirthdate);
    }

//    public String removeTimeUsingDateTimeFormatter(String inputDate) {
//        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
//        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDateTime dateTime = LocalDateTime.parse(inputDate, inputFormatter);
//        return dateTime.format(outputFormatter);
//    }
    public void loadSalaryToTable() {
        tblModelSalary.setRowCount(0);
        int i = 1;
        for (Salary per : salaryService.getSalaryList()) {
            tblModelSalary.addRow(new Object[]{i++, per.getSalaryType(), fmMoney(per.getSalaryAmount())});
        }
        lblCountSalary.setText(((i - 1) == 0 || (i - 1) == 1) ? ((i - 1) + " result") : ((i - 1) + " results"));
    }

    public void loadSpecificList(List<UserDetails> list) {
        tblModel.setRowCount(0);
//        this.tempList.clear();
        int i = 1;
        for (UserDetails per : list) {
//            this.tempList.add(per);
            tblModel.addRow(new Object[]{i++, per.getUser().getUsername(), per.getUser().getRole(), per.getFullname(), per.getJobPosition()});
        }

        lblCountEmployee.setText(((i - 1) == 0 || (i - 1) == 1) ? ((i - 1) + " result") : ((i - 1) + " results"));
    }

    public void handleSearching() {
        String query = txtQuery.getText().trim();
        System.out.println(query);
        this.tempList.clear();
        this.tempList = userService.filterEmployee(query);
        loadSpecificList(this.tempList);
        System.out.println(this.tempList);
    }

    private void updateSalary(List<Salary> data) {
        DefaultComboBoxModel<Salary> cbbModel = new DefaultComboBoxModel<>();
        cboSalary.removeAll();
        cboSalary.setModel((DefaultComboBoxModel) cbbModel);
        for (Salary salary : data) {
            cbbModel.addElement(salary);
        }
    }

    public void loadDataAndFillSalary() {
        CompletableFuture<List<Salary>> future = this.salaryService.getList();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                updateSalary(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    //Covert between date & String
    public String convertDateToString(Date date) {
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String format = formatter.format(date);
            return format;
        } else {
            return "";
        }
    }

    public Date dateFM(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date utilDate = dateFormat.parse(date);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            return sqlDate;
        } catch (ParseException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    private void setImange(String url) {
        lblAva.setText(null);
        try {
            int labelWidth = lblAva.getWidth();
            int labelHeight = lblAva.getHeight();
            ImageIcon originalIcon = new javax.swing.ImageIcon(
                    Objects.requireNonNull(getClass().getResource("/Ava/" + url)));
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(labelWidth, labelHeight,
                    Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            lblAva.setIcon(scaledIcon);
        } catch (Exception e) {
            lblAva.setIcon(null);
            lblAva.setText("Image not found");
        }
    }

    public String urlImage(Boolean get_set) {
        try {
            String currentDirectory = System.getProperty("user.dir")
                    + "/src/main/java/Ava/";
            JFileChooser fileChooser = new JFileChooser(currentDirectory);
            fileChooser.showOpenDialog(null);
            File selectedFile = fileChooser.getSelectedFile();

            if (selectedFile != null) {
                if (get_set) {
                    return selectedFile.getName();
                }
                Image img = ImageIO.read(selectedFile);
                lblAva.setText(null);
                lblAva.setIcon(new ImageIcon(
                        img.getScaledInstance(lblAva.getWidth(),
                                lblAva.getHeight(),
                                lblAva.getWidth())));
                return selectedFile.getName();
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        return null;
    }

    public void fillDataToFields(UserDetails userDetails) {
        lblFullname.setText(userDetails.getFullname());
        lblJobPosition.setText(userDetails.getJobPosition());
        txtUsername.setText(userDetails.getUser().getUsername());
        if (userDetails.getUser().getRole().equalsIgnoreCase("admin")) {
            rdoAdmin.setSelected(true);
        } else {
            rdoUser.setSelected(true);
        }
        if (userDetails.getGender().equalsIgnoreCase("male")) {
            rdoMale.setSelected(true);
        } else {
            rdoFemale.setSelected(true);
        }
        txtFullname.setText(userDetails.getFullname());
        txtJobPosition.setText(userDetails.getJobPosition());
        cboSalary.setSelectedItem(userDetails.getSalary());
        txtPhonenumber.setText(userDetails.getTel());
        txtEmail.setText(userDetails.getEmail());
        txtBirthdate.setText(convertDateToString(userDetails.getBirthdate()));
        txtCitizenID.setText(userDetails.getCitizenId());
        txtAddress.setText(userDetails.getAddress());
        txtNote.setText(userDetails.getNote());
        lblCreatedDate.setText("Created at: " + convertDateToString(userDetails.getCreatedAt()));

        this.imgName = userDetails.getPhoto();
        setImange(this.imgName);
        System.out.println(this.imgName);
    }

    //Handle event
    public void handleOnClickTable() {
        this._USER_DETAILS = this.tempList.get(tblUser.getSelectedRow());
        fillDataToFields(this._USER_DETAILS);
    }

    public void fillSalaryToFields(Salary obj) {
        txtTypeOfSalary.setText(obj.getSalaryType());
        txtAmount.setText(String.valueOf(obj.getSalaryAmount()));
    }

    //Add & updating process:
    //valiadate:
    public boolean validateUserWhenAdding() {
        if (txtUsername.getText().trim().isBlank() || txtUsername.getText().length() < 5) {
            this.main.notificationShowWARNING("Username is a required field and must contain at least 5 characters !!!");
            txtUsername.requestFocus();
            return false;
        } else if (userService.uniquedUsername(txtUsername.getText().trim())) {
            this.main.notificationShowWARNING("This username has exist. Change another username pls !!!");
            txtUsername.requestFocus();
            return false;
        } else if (txtPassword.getPassword().length < 5) {
            this.main.notificationShowWARNING("Password is a required field and must contain at least 5 characters !!!");
            txtPassword.requestFocus();
            return false;
        } else if (txtFullname.getText().trim().isBlank()) {
            this.main.notificationShowWARNING("Fullname is a required field !!!");
            txtFullname.requestFocus();
            return false;
        } else if (dateFM(txtBirthdate.getText().trim()) == null) {
            this.main.notificationShowWARNING("Birthdate is a required field and it should following the convention: yyyy-MM-dd !!!");
            txtBirthdate.requestFocus();
            return false;
        } else if (!txtPhonenumber.getText().trim().matches("^(0[0-9]{9}|84[0-9]{9})$")) {
            this.main.notificationShowWARNING("Phone number is invalid !!!");
            txtPhonenumber.requestFocus();
            return false;
        } else if (!txtEmail.getText().trim().isBlank()) {
            if (!txtEmail.getText().trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                this.main.notificationShowWARNING("Email is invalid!!!");
                txtPhonenumber.requestFocus();
                return false;
            }
        }
        if (dateFM(txtBirthdate.getText()).compareTo(new Date()) > 0) {
            this.main.notificationShowWARNING("Birthdate is invalid!!!");
            txtBirthdate.requestFocus();
            return false;
        }
        return true;
    }

    public User createNewUser() {
        User newuser = new User();
        newuser.setUsername(txtUsername.getText().trim());
        newuser.setPassword(new BcryptHash().hashPassword(String.valueOf(txtPassword.getPassword()).trim()));
        newuser.setRole((rdoAdmin.isSelected() ? "Admin" : "User"));
        return newuser;
    }

    public UserDetails createNewUserDetails() {
        UserDetails userDetails = new UserDetails();
        userDetails.setUser(createNewUser());
        userDetails.setSalary((Salary) cboSalary.getSelectedItem());
        userDetails.setGender(rdoFemale.isSelected() ? "Female" : "Male");
        userDetails.setFullname(txtFullname.getText().trim());
        userDetails.setTel(txtPhonenumber.getText().trim());
        userDetails.setPhoto(this.imgName);
        userDetails.setEmail(txtEmail.getText().trim());
        userDetails.setJobPosition(txtJobPosition.getText().trim());
        userDetails.setCitizenId(txtCitizenID.getText().trim());
        userDetails.setAddress(txtAddress.getText().trim());
        userDetails.setNote(txtNote.getText().trim());
        userDetails.setStatus("Active");
        userDetails.setBirthdate(dateFM(txtBirthdate.getText()));
        userDetails.setCreatedAt(new Date());

        return userDetails;
    }

    public User updateUser(UserDetails preDetails) {
        User newuser = new User();

        System.out.println("user id: " + preDetails.getUser().getId());
        newuser.setId(preDetails.getUser().getId());
        newuser.setUsername(txtUsername.getText());
        newuser.setPassword(String.valueOf(txtPassword.getPassword()).isBlank()
                ? this._USER_DETAILS.getUser().getPassword()
                : new BcryptHash().hashPassword(String.valueOf(txtPassword.getPassword()))
        );
        newuser.setRole((rdoAdmin.isSelected() ? "Admin" : "User"));
        return newuser;

    }

    public UserDetails updateUserDetails(UserDetails preDetails) {
        UserDetails userDetails = new UserDetails();
        userDetails.setId(preDetails.getId());
        userDetails.setUser(updateUser(preDetails));
        userDetails.setSalary((Salary) cboSalary.getSelectedItem());
        userDetails.setGender(rdoFemale.isSelected() ? "Female" : "Male");
        userDetails.setFullname(txtFullname.getText().trim());
        userDetails.setTel(txtPhonenumber.getText().trim());
        userDetails.setPhoto(this.imgName);
        userDetails.setEmail(txtEmail.getText().trim());
        userDetails.setJobPosition(txtJobPosition.getText().trim());
        userDetails.setCitizenId(txtCitizenID.getText().trim());
        userDetails.setAddress(txtAddress.getText().trim());
        userDetails.setNote(txtNote.getText().trim());
        userDetails.setStatus("Active");
        userDetails.setBirthdate(dateFM(txtBirthdate.getText()));
        userDetails.setCreatedAt(new Date());

        return userDetails;
    }

    public boolean validateUserWhenUpdate(int index) {
        //Username and passw can't change
        if (txtUsername.getText().trim().isBlank() || txtUsername.getText().trim().length() < 5) {
            this.main.notificationShowWARNING("Username can't blank and must have more than 5 char");
            txtUsername.requestFocus();
            return false;
        }
        if (!txtUsername.getText().trim().isBlank()) {
            List<UserDetails> list = userService.getList();
            for (UserDetails per : list) {
                if (!per.getUser().getUsername().equalsIgnoreCase(txtUsername.getText().trim())
                        || txtUsername.getText().trim().equalsIgnoreCase(list.get(index).getUser().getUsername())) {
                } else {
                    this.main.notificationShowWARNING("Username has contained");
                    return false;
                }
            }
        }

        if (txtFullname.getText().trim().isBlank()) {
            this.main.notificationShowWARNING("Fullname is a required field !!!");
            txtFullname.requestFocus();
            return false;
        } else if (!txtPhonenumber.getText().trim().matches("^(0[0-9]{9}|84[0-9]{9})$")) {
            this.main.notificationShowWARNING("Phone number is invalid !!!");
            txtPhonenumber.requestFocus();
            return false;
        } else if (!txtEmail.getText().trim().isBlank()) {
            if (!txtEmail.getText().trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                this.main.notificationShowWARNING("Email is invalid!!!");
                txtPhonenumber.requestFocus();
                return false;
            }
        }
        if (dateFM(txtBirthdate.getText()).compareTo(new Date()) > 0) {
            this.main.notificationShowWARNING("Birthdate is invalid!!!");
            txtBirthdate.requestFocus();
            return false;
        }

        return true;
    }

    public void addNewUser() {
        if (validateUserWhenAdding()) {
            if (this.userService.addNewUser(createNewUserDetails())) {
                this.main.notificationShowSUCCESS("Added a new employee !!!");
                loadDataToTable();

            } else {
                this.main.notificationShowWARNING("Failed !!!");
            }
        }
    }

    public void handleDelete() {
        int index = tblUser.getSelectedRow();
        if (index < 0) {
            return;
        }
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "If you delete this user, the relative activities of this user will be saved", "Warning !!!", dialogButton);
        if (dialogResult == 0) {
            UserDetails obj = userService.getList().get(index); //Con cũ
            obj.setStatus("Inactive");
            if (this.userService.deleteNewUser(obj)) {
                this.main.notificationShowSUCCESS("Deleted");
                loadDataToTable();
//                historyService.trackHistory(
//                        "Deleted an user named: " + createNewUserDetails().getUser().getUsername(),
//                        this.user.getUsername(),
//                        HistoryProductService.ChangeType.REMOVE
//                );
            } else {
                this.main.notificationShowWARNING("Failed");
            }
        }
    }

    public void handleUpdate() {
        int index = tblUser.getSelectedRow();
        if (index < 0) {
            this.main.notificationShowSUCCESS("Choose an employee to update !!!");
            return;
        }
        if (validateUserWhenUpdate(index)) {
            if (this.userService.addNewUser(updateUserDetails(this.tempList.get(index)))) {
                this.main.notificationShowSUCCESS("Updated !!!");
                loadDataToTable();
//                historyService.trackHistory(
//                        "Updated an user named: " + updateUserDetails(this.tempList.get(index)).getUser().getUsername(),
//                        this.user.getUsername(),
//                        HistoryProductService.ChangeType.UPDATE
//                );
            } else {
                this.main.notificationShowWARNING("Failed !!!");
            }
        }
    }

    public boolean validateSalary() {
        if (txtTypeOfSalary.getText().trim().isBlank()) {
            this.main.notificationShowWARNING("Type of salary is a required field !!!");
            txtFullname.requestFocus();
            return false;
        } else if (txtAmount.getText().trim().isBlank()) {
            this.main.notificationShowWARNING("The amount of salary/wage is a required field !!!");
            txtAmount.requestFocus();
            return false;
        } else if (!txtAmount.getText().trim().isBlank()) {
            try {
                int amount = Integer.parseInt(txtAmount.getText().trim());
                if (amount < 21000) {
                    this.main.notificationShowWARNING("The amount of wage is too low, must be more than 21.000d/h");
                    txtAmount.requestFocus();
                    return false;
                }
            } catch (Exception e) {
                this.main.notificationShowWARNING("Invaliding amount of wage");
                txtAmount.requestFocus();
                return false;
            }
        }
        return true;
    }

    public Integer convertStrToNum(String str) {
        Integer num = null;
        try {
            num = Integer.parseInt(str);
        } catch (Exception e) {
            System.err.println(e);
        }

        return num;
    }

    public Salary createSalary() {
        Salary salary = new Salary();
        salary.setSalaryAmount(convertStrToNum(txtAmount.getText().trim()));
        salary.setSalaryType(txtTypeOfSalary.getText().trim());
        salary.setStatus("Active");
        return salary;
    }

    public Salary createSalary4Update(Salary pre) {
        Salary salary = new Salary();
        salary.setId(pre.getId());
        salary.setSalaryAmount(convertStrToNum(txtAmount.getText().trim()));
        salary.setSalaryType(txtTypeOfSalary.getText().trim());
        salary.setStatus("Active");
        return salary;
    }

    public void addNewSalary() {
        if (validateSalary()) {
            if (this.salaryService.addNewSalary(createSalary())) {
                this.main.notificationShowSUCCESS("Added a new salary type !!!");
//                historyService.trackHistory("Added new salary type named: " + createSalary().getSalaryType(),
//                        this.user.getUsername(),
//                        HistoryService.ChangeType.NEW
//                );
                loadSalaryToTable();
                loadDataAndFillSalary();
            } else {
                this.main.notificationShowWARNING("Failed !!!");
            }
        }
    }

    public void handleUpdateSalary() {
        if (validateSalary()) {
            if (this.salaryService.addNewSalary(createSalary4Update(salaryService.getSalaryList().get(tblSalary.getSelectedRow())))) {
                this.main.notificationShowSUCCESS("Updated !!!");
                loadSalaryToTable();
                loadDataAndFillSalary();
//                historyService.trackHistory(
//                        "Updated an salary titled: " + createSalary4Update(salaryService.getSalaryList().get(tblSalary.getSelectedRow())).getSalaryType(),
//                        this.user.getUsername(),
//                        HistoryProductService.ChangeType.UPDATE
//                );
            } else {
                this.main.notificationShowWARNING("Failed !!!");
            }
        }
    }

    public void handleDeleteSalary() {
        boolean isContain = false;
        int index = tblSalary.getSelectedRow();
        if (index >= 0) {
            Salary targetDelete = salaryService.getSalaryList().get(tblSalary.getSelectedRow());
            List<UserDetails> userList = userService.getList();
            for (UserDetails per : userList) {
                if (per.getSalary().equals(targetDelete)) {
                    this.main.notificationShowWARNING("Must change all employee are using this salary before delete it!!!");
                    isContain = true;
                }
            }
            if (!isContain) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure to delete this type of salary", "Warning !!!", dialogButton);
                if (dialogResult == 0) {
                    salaryService.deleteSalary(targetDelete);
                    loadDataAndFillSalary();
                    loadSalaryToTable();
//                    historyService.trackHistory(
//                            "Deleted the salary type named: " + createSalary().getSalaryType() + " " + fmMoney(createSalary().getSalaryAmount()),
//                            this.user.getUsername(),
//                            HistoryProductService.ChangeType.REMOVE
//                    );

                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        genderGroup = new javax.swing.ButtonGroup();
        roleGroup = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        btnSearch = new udpm.fpt.swing.ButtonMessage();
        jLabel1 = new javax.swing.JLabel();
        lblCountEmployee = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblFullname = new javax.swing.JLabel();
        lblJobPosition = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtJobPosition = new udpm.fpt.swing.TextField();
        jLabel5 = new javax.swing.JLabel();
        rdoUser = new javax.swing.JRadioButton();
        rdoMale = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        rdoAdmin = new javax.swing.JRadioButton();
        rdoFemale = new javax.swing.JRadioButton();
        txtPhonenumber = new udpm.fpt.swing.TextField();
        jSeparator1 = new javax.swing.JSeparator();
        cboSalary = new udpm.fpt.swing.Combobox();
        jLabel7 = new javax.swing.JLabel();
        txtEmail = new udpm.fpt.swing.TextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        txtBirthdate = new udpm.fpt.swing.TextField();
        txtCitizenID = new udpm.fpt.swing.TextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();
        btnAdd = new udpm.fpt.swing.ButtonMessage();
        buttonMessage3 = new udpm.fpt.swing.ButtonMessage();
        buttonMessage4 = new udpm.fpt.swing.ButtonMessage();
        lblCreatedDate = new javax.swing.JLabel();
        txtFullname = new udpm.fpt.swing.TextField();
        txtPassword = new udpm.fpt.swing.PasswordField();
        lblAva = new javax.swing.JLabel();
        txtUsername = new udpm.fpt.swing.TextField();
        txtQuery = new udpm.fpt.swing.TextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSalary = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        lblCountSalary = new javax.swing.JLabel();
        txtAmount = new udpm.fpt.swing.TextField();
        txtTypeOfSalary = new udpm.fpt.swing.TextField();
        btnUpdateUser = new udpm.fpt.swing.ButtonMessage();
        btnAddSalary = new udpm.fpt.swing.ButtonMessage();
        buttonMessage5 = new udpm.fpt.swing.ButtonMessage();

        setBackground(new java.awt.Color(255, 255, 255));

        tblUser.setModel(new javax.swing.table.DefaultTableModel(
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
        tblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUser);

        btnSearch.setBackground(new java.awt.Color(0, 84, 166));
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/search.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Employee");

        lblCountEmployee.setForeground(new java.awt.Color(102, 102, 102));
        lblCountEmployee.setText(" ");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        lblFullname.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblFullname.setText("Fullname");

        lblJobPosition.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblJobPosition.setText("Job Position");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Info");

        txtJobPosition.setLabelText("Job Position");
        txtJobPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJobPositionActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Role");

        rdoUser.setBackground(new java.awt.Color(255, 255, 255));
        roleGroup.add(rdoUser);
        rdoUser.setText("User");
        rdoUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoUserActionPerformed(evt);
            }
        });

        rdoMale.setBackground(new java.awt.Color(255, 255, 255));
        genderGroup.add(rdoMale);
        rdoMale.setText("Male");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Gender");

        rdoAdmin.setBackground(new java.awt.Color(255, 255, 255));
        roleGroup.add(rdoAdmin);
        rdoAdmin.setText("Admin");

        rdoFemale.setBackground(new java.awt.Color(255, 255, 255));
        genderGroup.add(rdoFemale);
        rdoFemale.setText("Female");
        rdoFemale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoFemaleActionPerformed(evt);
            }
        });

        txtPhonenumber.setLabelText("Phone number");
        txtPhonenumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhonenumberActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        cboSalary.setLabeText("Type of Salary");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Contact info");

        txtEmail.setLabelText("Email");
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        txtAddress.setColumns(20);
        txtAddress.setRows(5);
        jScrollPane2.setViewportView(txtAddress);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Note");

        txtBirthdate.setLabelText("Birthdate");
        txtBirthdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBirthdateActionPerformed(evt);
            }
        });

        txtCitizenID.setLabelText("Citizenid");
        txtCitizenID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCitizenIDActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Address");

        txtNote.setColumns(20);
        txtNote.setRows(5);
        jScrollPane3.setViewportView(txtNote);

        btnAdd.setBackground(new java.awt.Color(0, 84, 166));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/plus.png"))); // NOI18N
        btnAdd.setText("Add new User");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        buttonMessage3.setBackground(new java.awt.Color(0, 84, 168));
        buttonMessage3.setForeground(new java.awt.Color(255, 255, 255));
        buttonMessage3.setText("Update this user");
        buttonMessage3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMessage3ActionPerformed(evt);
            }
        });

        buttonMessage4.setBackground(new java.awt.Color(204, 51, 0));
        buttonMessage4.setForeground(new java.awt.Color(255, 255, 255));
        buttonMessage4.setText("Delete");
        buttonMessage4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMessage4ActionPerformed(evt);
            }
        });

        lblCreatedDate.setText("jLabel2");

        txtFullname.setLabelText("Fullname");
        txtFullname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFullnameActionPerformed(evt);
            }
        });

        txtPassword.setLabelText("Password");
        txtPassword.setShowAndHide(true);

        lblAva.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblAva.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAva.setText("Image not found");
        lblAva.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAvaMouseClicked(evt);
            }
        });

        txtUsername.setLabelText("Username");
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 20, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtFullname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtCitizenID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtBirthdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cboSalary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(rdoAdmin)
                                            .addGap(32, 32, 32)
                                            .addComponent(rdoUser)
                                            .addGap(39, 39, 39))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addGap(151, 151, 151)))
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(42, 42, 42)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(rdoMale)
                                            .addGap(18, 18, 18)
                                            .addComponent(rdoFemale))))
                                .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPhonenumber, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                                .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                                .addComponent(jScrollPane2)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtJobPosition, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblAva, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lblJobPosition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblFullname, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                                            .addComponent(lblCreatedDate, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(buttonMessage3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonMessage4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(17, 17, 17))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(lblFullname)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblJobPosition)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCreatedDate))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(lblAva, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtJobPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPhonenumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rdoUser)
                                    .addComponent(rdoAdmin)
                                    .addComponent(rdoFemale)
                                    .addComponent(rdoMale)))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(cboSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtBirthdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtCitizenID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonMessage3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonMessage4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        txtQuery.setLabelText("Search by username or fullname");
        txtQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQueryActionPerformed(evt);
            }
        });

        tblSalary.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSalary.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSalaryMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblSalary);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Type of Salary");

        lblCountSalary.setForeground(new java.awt.Color(102, 102, 102));
        lblCountSalary.setText(" ");

        txtAmount.setLabelText("Amount");
        txtAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAmountActionPerformed(evt);
            }
        });

        txtTypeOfSalary.setLabelText("Type of salary");
        txtTypeOfSalary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTypeOfSalaryActionPerformed(evt);
            }
        });

        btnUpdateUser.setBackground(new java.awt.Color(0, 84, 166));
        btnUpdateUser.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/plus.png"))); // NOI18N
        btnUpdateUser.setText("Update salary");
        btnUpdateUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateUserActionPerformed(evt);
            }
        });

        btnAddSalary.setBackground(new java.awt.Color(0, 84, 166));
        btnAddSalary.setForeground(new java.awt.Color(255, 255, 255));
        btnAddSalary.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/plus.png"))); // NOI18N
        btnAddSalary.setText("Add new type");
        btnAddSalary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSalaryActionPerformed(evt);
            }
        });

        buttonMessage5.setBackground(new java.awt.Color(204, 51, 0));
        buttonMessage5.setForeground(new java.awt.Color(255, 255, 255));
        buttonMessage5.setText("Delete this salary");
        buttonMessage5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMessage5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtTypeOfSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAddSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnUpdateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(buttonMessage5, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblCountSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(26, 26, 26)
                                        .addComponent(lblCountEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtQuery, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(42, 42, 42)
                                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(26, 26, 26)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtQuery, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lblCountEmployee))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lblCountSalary))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTypeOfSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnUpdateUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonMessage5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 140, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQueryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQueryActionPerformed

    private void rdoUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoUserActionPerformed

    private void rdoFemaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoFemaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoFemaleActionPerformed

    private void txtPhonenumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhonenumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhonenumberActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtBirthdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBirthdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBirthdateActionPerformed

    private void txtCitizenIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCitizenIDActionPerformed
        // TODO add your handling code here:
//        teen moi != tat ca ten khac trong list || ten moi == ten cu
    }//GEN-LAST:event_txtCitizenIDActionPerformed

    private void buttonMessage3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMessage3ActionPerformed
        // TODO add your handling code here:
        handleUpdate();
    }//GEN-LAST:event_buttonMessage3ActionPerformed

    private void buttonMessage4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMessage4ActionPerformed
        // TODO add your handling code here:
        handleDelete();
    }//GEN-LAST:event_buttonMessage4ActionPerformed

    private void tblUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMouseClicked

        handleOnClickTable();

    }//GEN-LAST:event_tblUserMouseClicked

    private void txtFullnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFullnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFullnameActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        addNewUser();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        handleSearching();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAmountActionPerformed

    private void txtTypeOfSalaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTypeOfSalaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTypeOfSalaryActionPerformed

    private void btnUpdateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateUserActionPerformed
        // TODO add your handling code here:
        handleUpdateSalary();
    }//GEN-LAST:event_btnUpdateUserActionPerformed

    private void btnAddSalaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSalaryActionPerformed
        // TODO add your handling code here:
        addNewSalary();
    }//GEN-LAST:event_btnAddSalaryActionPerformed

    private void buttonMessage5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMessage5ActionPerformed
        // TODO add your handling code here:
        handleDeleteSalary();

    }//GEN-LAST:event_buttonMessage5ActionPerformed

    private void tblSalaryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSalaryMouseClicked
        // TODO add your handling code here:
        List<Salary> list = salaryService.getSalaryList();
        fillSalaryToFields(list.get(tblSalary.getSelectedRow()));
    }//GEN-LAST:event_tblSalaryMouseClicked

    private void txtJobPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJobPositionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJobPositionActionPerformed

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void lblAvaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAvaMouseClicked
        this.imgName = urlImage(false);
    }//GEN-LAST:event_lblAvaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.ButtonMessage btnAdd;
    private udpm.fpt.swing.ButtonMessage btnAddSalary;
    private udpm.fpt.swing.ButtonMessage btnSearch;
    private udpm.fpt.swing.ButtonMessage btnUpdateUser;
    private udpm.fpt.swing.ButtonMessage buttonMessage3;
    private udpm.fpt.swing.ButtonMessage buttonMessage4;
    private udpm.fpt.swing.ButtonMessage buttonMessage5;
    private udpm.fpt.swing.Combobox cboSalary;
    private javax.swing.ButtonGroup genderGroup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAva;
    private javax.swing.JLabel lblCountEmployee;
    private javax.swing.JLabel lblCountSalary;
    private javax.swing.JLabel lblCreatedDate;
    private javax.swing.JLabel lblFullname;
    private javax.swing.JLabel lblJobPosition;
    private javax.swing.JRadioButton rdoAdmin;
    private javax.swing.JRadioButton rdoFemale;
    private javax.swing.JRadioButton rdoMale;
    private javax.swing.JRadioButton rdoUser;
    private javax.swing.ButtonGroup roleGroup;
    private javax.swing.JTable tblSalary;
    private javax.swing.JTable tblUser;
    private javax.swing.JTextArea txtAddress;
    private udpm.fpt.swing.TextField txtAmount;
    private udpm.fpt.swing.TextField txtBirthdate;
    private udpm.fpt.swing.TextField txtCitizenID;
    private udpm.fpt.swing.TextField txtEmail;
    private udpm.fpt.swing.TextField txtFullname;
    private udpm.fpt.swing.TextField txtJobPosition;
    private javax.swing.JTextArea txtNote;
    private udpm.fpt.swing.PasswordField txtPassword;
    private udpm.fpt.swing.TextField txtPhonenumber;
    private udpm.fpt.swing.TextField txtQuery;
    private udpm.fpt.swing.TextField txtTypeOfSalary;
    private udpm.fpt.swing.TextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
