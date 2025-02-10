package lk.ijse.gdse.traveler.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse.traveler.bo.custom.impl.AdminBOImpl;
import lk.ijse.gdse.traveler.bo.custom.impl.CashierBOImpl;
import lk.ijse.gdse.traveler.dto.AdminDTO;
import lk.ijse.gdse.traveler.dto.CashierDTO;
import lk.ijse.gdse.traveler.view.tdm.CashierTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CashierController implements Initializable {

    @FXML
    private AnchorPane ancCashier;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbAdminId;

    @FXML
    private TableColumn<CashierTM, String> colAdminId;

    @FXML
    private TableColumn<CashierTM, String> colCashierId;

    @FXML
    private TableColumn<CashierTM, String> colContact;

    @FXML
    private TableColumn<CashierTM, String> colEmail;

    @FXML
    private TableColumn<CashierTM, String> colName;

    @FXML
    private TableColumn<CashierTM, String> colUsername;

    @FXML
    private Label lblAdminName;

    @FXML
    private Label lblCashierId;

    @FXML
    private Label lblUsername;

    @FXML
    private TableView<CashierTM> tblCashier;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPassword;

    private final AdminBOImpl adminBOImpl = new AdminBOImpl();
    CashierBOImpl cashierBOImpl = new CashierBOImpl();

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String cashierId = lblCashierId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = cashierBOImpl.delete(cashierId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Cashier deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to cashier Admin...!").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String cashierId = lblCashierId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String username = lblUsername.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String adminId = cmbAdminId.getValue();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: #7367F0;");
        txtPassword.setStyle(txtPassword.getStyle() + ";-fx-border-color: #7367F0;");
        txtConfirmPassword.setStyle(txtConfirmPassword.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
        String passwordPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidContact = contact.matches(contactPattern);
        boolean isValidPassword = password.matches(passwordPattern);
        boolean isValidConfirmPassword = confirmPassword.matches(passwordPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidContact) {
            txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: red;");
        }

        if (password.equals(confirmPassword)) {
            if (!isValidPassword) {
                txtPassword.setStyle(txtPassword.getStyle() + ";-fx-border-color: red;");
            }

            if (!isValidConfirmPassword) {
                txtConfirmPassword.setStyle(txtConfirmPassword.getStyle() + ";-fx-border-color: red;");
            }
        } else {
            txtPassword.setStyle(txtPassword.getStyle() + ";-fx-border-color: red;");
            txtConfirmPassword.setStyle(txtConfirmPassword.getStyle() + ";-fx-border-color: red;");
            new Alert(Alert.AlertType.ERROR, "Confirm password must same as given Password...!").show();
        }

        if (isValidName && isValidEmail && isValidContact && isValidPassword && isValidConfirmPassword) {
            CashierDTO cashierDTO = new CashierDTO(
                    cashierId,
                    name,
                    email,
                    contact,
                    username,
                    password,
                    adminId
            );

            boolean isSaved = cashierBOImpl.save(cashierDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Cashier saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save cashier...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String cashierId = lblCashierId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String username = lblUsername.getText();
        String password = txtPassword.getText();
        String adminId = cmbAdminId.getValue();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: #7367F0;");
        txtPassword.setStyle(txtPassword.getStyle() + ";-fx-border-color: #7367F0;");
        txtConfirmPassword.setStyle(txtConfirmPassword.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
        String passwordPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidContact = contact.matches(contactPattern);
        boolean isValidPassword = password.matches(passwordPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidContact) {
            txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPassword) {
            txtPassword.setStyle(txtPassword.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidEmail && isValidContact && isValidPassword) {
            CashierDTO cashierDTO = new CashierDTO(
                    cashierId,
                    name,
                    email,
                    contact,
                    username,
                    password,
                    adminId
            );

            boolean isSaved = cashierBOImpl.update(cashierDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Cashier updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update cashier...!").show();
            }
        }
    }

    @FXML
    void btnSendMailOnAction(ActionEvent event) {
        CashierTM selectedItem = tblCashier.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "Please select customer..!");
            return;
        }

        try {
            // Load the mail dialog from FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sendMailFx.fxml"));
            Parent load = loader.load();

            SendMailController sendMailController = loader.getController();

            String email = selectedItem.getEmail();
            sendMailController.setCustomerEmail(email);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Send email");

            // Set window as modal
            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btnUpdate.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load ui..!");
            e.printStackTrace();
        }
    }

    @FXML
//    void cmbAdminOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
//        String selectedAdminId = cmbAdminId.getSelectionModel().getSelectedItem();
//        AdminDTO adminDTO = adminBOImpl.findById(selectedAdminId);
//
//        // If admin found (adminDTO not null)
//        if (adminDTO != null) {
//
//            // FIll admin related labels
//            lblAdminName.setText(adminDTO.getName());
//        }
//    }
    void cmbAdminOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedAdminId = cmbAdminId.getSelectionModel().getSelectedItem();

        System.out.println(selectedAdminId);
        // Prevent null selection from causing an error
        if (selectedAdminId == null || selectedAdminId.isEmpty()) {
            lblAdminName.setText(""); // Clear label if nothing is selected
            return;
        }

        AdminDTO adminDTO = adminBOImpl.findById(selectedAdminId);

        if (adminDTO != null) {
            lblAdminName.setText(adminDTO.getName());
        } else {
            System.out.println("Admin not found in the database!");
            lblAdminName.setText(""); // Clear label if admin is not found
        }
    }



    @FXML
    void onClickTable(MouseEvent event) {
        CashierTM cashierTM = tblCashier.getSelectionModel().getSelectedItem();
        if (cashierTM != null) {
            lblCashierId.setText(cashierTM.getCashierId());
            txtName.setText(cashierTM.getName());
            txtEmail.setText(cashierTM.getEmail());
            txtContact.setText(cashierTM.getContactNumber());
            lblUsername.setText(cashierTM.getUsername());
            cmbAdminId.setPromptText(cashierTM.getAdminId());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set table column to cell factory value
        colCashierId.setCellValueFactory(new PropertyValueFactory<>("cashierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colAdminId.setCellValueFactory(new PropertyValueFactory<>("adminId"));

        // inside initialize method
        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Cashier id").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextCashierId();
        loadTableData();
        loadAdminIds();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtEmail.setText("");
        txtContact.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
        cmbAdminId.getSelectionModel().clearSelection();;
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<CashierDTO> cashierDTOS = cashierBOImpl.getAll();

        ObservableList<CashierTM> cashierTMS = FXCollections.observableArrayList();

        for (CashierDTO cashierDTO : cashierDTOS) {
            CashierTM cashierTM = new CashierTM(
                    cashierDTO.getCashierId(),
                    cashierDTO.getName(),
                    cashierDTO.getEmail(),
                    cashierDTO.getContactNumber(),
                    cashierDTO.getUsername(),
                    cashierDTO.getAdminId()
            );
            cashierTMS.add(cashierTM);
        }
        tblCashier.setItems(cashierTMS);
    }

    public void loadNextCashierId() throws SQLException, ClassNotFoundException {
        String nextCashierId = cashierBOImpl.getNextId();
        lblCashierId.setText(nextCashierId);
        lblUsername.setText(nextCashierId);
    }

    private void loadAdminIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> adminIds = adminBOImpl.getAllIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(adminIds);
        cmbAdminId.setItems(observableList);
    }
}
