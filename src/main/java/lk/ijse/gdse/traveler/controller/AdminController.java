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
import lk.ijse.gdse.traveler.dto.AdminDTO;
import lk.ijse.gdse.traveler.dto.tm.AdminTM;
import lk.ijse.gdse.traveler.model.AdminModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set table column to cell factory value
        colAdminId.setCellValueFactory(new PropertyValueFactory<>("adminId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));

        // inside initialize method
        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Admin id").show();
        }
    }

    @FXML
    private AnchorPane ancAdmin;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<AdminTM, String> colAdminId;

    @FXML
    private TableColumn<AdminTM, String> colContact;

    @FXML
    private TableColumn<AdminTM, String> colEmail;

    @FXML
    private TableColumn<AdminTM, String> colName;

    @FXML
    private TableColumn<AdminTM, String> colUsername;

    @FXML
    private Label lblAdminId;

    @FXML
    private Label lblUsername;

    @FXML
    private TableView<AdminTM> tblAdmin;

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

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String adminId = lblAdminId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = adminModel.deleteAdmin(adminId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Admin deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Admin...!").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String adminId = lblAdminId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String username = lblUsername.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

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
            AdminDTO adminDTO = new AdminDTO(
                    adminId,
                    name,
                    email,
                    contact,
                    username,
                    password
            );

            boolean isSaved = adminModel.saveAdmin(adminDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Admin saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save admin...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String adminId = lblAdminId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String username = lblUsername.getText();
        String password = txtPassword.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: #7367F0;");
        txtPassword.setStyle(txtPassword.getStyle() + ";-fx-border-color: #7367F0;");

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
            AdminDTO adminDTO = new AdminDTO(
                    adminId,
                    name,
                    email,
                    contact,
                    username,
                    password
            );

            boolean isSaved = adminModel.updateAdmin(adminDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Admin updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update admin...!").show();
            }
        }
    }

    @FXML
    void btnSendMailOnAction(ActionEvent event) {
        AdminTM selectedItem = tblAdmin.getSelectionModel().getSelectedItem();
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
    void onClickTable(MouseEvent event) {
        AdminTM adminTM = tblAdmin.getSelectionModel().getSelectedItem();
        if (adminTM != null) {
            lblAdminId.setText(adminTM.getAdminId());
            txtName.setText(adminTM.getName());
            txtEmail.setText(adminTM.getEmail());
            txtContact.setText(adminTM.getContactNumber());
            lblUsername.setText(adminTM.getUsername());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    private void refreshPage() throws SQLException {
        loadNextAdminId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtEmail.setText("");
        txtContact.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
    }

    AdminModel adminModel = new AdminModel();

    private void loadTableData() throws SQLException {
        ArrayList<AdminDTO> adminDTOS = adminModel.getAllAdmin();

        ObservableList<AdminTM> adminTMS = FXCollections.observableArrayList();

        for (AdminDTO adminDTO : adminDTOS) {
            AdminTM adminTM = new AdminTM(
                    adminDTO.getAdminId(),
                    adminDTO.getName(),
                    adminDTO.getEmail(),
                    adminDTO.getContactNumber(),
                    adminDTO.getUsername()
            );
            adminTMS.add(adminTM);
        }

        tblAdmin.setItems(adminTMS);
    }

    public void loadNextAdminId() throws SQLException {
        String nextAdminId = adminModel.getNextAdminId();
        lblAdminId.setText(nextAdminId);
        lblUsername.setText(nextAdminId);
    }
}
