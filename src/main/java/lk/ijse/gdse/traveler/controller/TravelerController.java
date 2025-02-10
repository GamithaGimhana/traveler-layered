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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse.traveler.bo.custom.impl.TravelerBOImpl;
import lk.ijse.gdse.traveler.dao.custom.TravelerDAO;
import lk.ijse.gdse.traveler.dao.custom.impl.TravelerDAOImpl;
import lk.ijse.gdse.traveler.dto.TravelerDTO;
import lk.ijse.gdse.traveler.view.tdm.TravelerTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TravelerController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set table column to cell factory value
        colTravelerId.setCellValueFactory(new PropertyValueFactory<>("travelerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("idNumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colNationality.setCellValueFactory(new PropertyValueFactory<>("nationality"));

        // inside initialize method
        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Traveler id").show();
        }
    }

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<TravelerTM, String> colContact;

    @FXML
    private TableColumn<TravelerTM, String> colEmail;

    @FXML
    private TableColumn<TravelerTM, String> colName;

    @FXML
    private TableColumn<TravelerTM, String> colNationality;

    @FXML
    private TableColumn<TravelerTM, String> colNic;

    @FXML
    private TableColumn<TravelerTM, String> colTravelerId;

    @FXML
    private Label lblTravelerId;

    @FXML
    private TableView<TravelerTM> tblTraveler;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNationality;

    @FXML
    private TextField txtNic;

//    TravelerModel travelerModel = new TravelerModel();
//    TravelerDAO travelerDAO = new TravelerDAOImpl();
    TravelerBOImpl travelerBOImpl = new TravelerBOImpl();

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String travelerId = lblTravelerId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = travelerBOImpl.delete(travelerId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Traveler deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Traveler...!").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String travelerId = lblTravelerId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String nationality = txtNationality.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: #7367F0;");
        txtNationality.setStyle(txtNationality.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
        String nationalityPattern = "^[A-Za-z ]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidContact = contact.matches(contactPattern);
        boolean isValidNationality = nationality.matches(nationalityPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
        }

        if (!isValidNic) {
            txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidContact) {
            txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidNationality) {
            txtNationality.setStyle(txtNationality.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidNic && isValidEmail && isValidContact) {
            TravelerDTO travelerDTO = new TravelerDTO(
                    travelerId,
                    name,
                    email,
                    contact,
                    nationality,
                    nic
            );

            boolean isSaved = travelerBOImpl.save(travelerDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Traveler saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save traveler...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String travelerId = lblTravelerId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String nationality = txtNationality.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: #7367F0;");
        txtNationality.setStyle(txtNationality.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
        String nationalityPattern = "^[A-Za-z ]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidContact = contact.matches(contactPattern);
        boolean isValidNationality = nationality.matches(nationalityPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
        }

        if (!isValidNic) {
            txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidContact) {
            txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidNationality) {
            txtNationality.setStyle(txtNationality.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidNic && isValidEmail && isValidContact) {
            TravelerDTO travelerDTO = new TravelerDTO(
                    travelerId,
                    name,
                    email,
                    contact,
                    nationality,
                    nic
            );

            boolean isSaved = travelerBOImpl.update(travelerDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Traveler updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update traveler...!").show();
            }
        }
    }

    @FXML
    void btnSendMailOnAction(ActionEvent event) {
        TravelerTM selectedItem = tblTraveler.getSelectionModel().getSelectedItem();
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
        TravelerTM travelerTM = tblTraveler.getSelectionModel().getSelectedItem();
        if (travelerTM != null) {
            lblTravelerId.setText(travelerTM.getTravelerId());
            txtName.setText(travelerTM.getName());
            txtEmail.setText(travelerTM.getEmail());
            txtContact.setText(travelerTM.getContactNumber());
            txtNationality.setText(travelerTM.getNationality());
            txtNic.setText(travelerTM.getIdNumber());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextTravelerId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtNic.setText("");
        txtEmail.setText("");
        txtContact.setText("");
        txtNationality.setText("");
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<TravelerDTO> travelerDTOS = travelerBOImpl.getAll();

        ObservableList<TravelerTM> travelerTMS = FXCollections.observableArrayList();

        for (TravelerDTO travelerDTO : travelerDTOS) {
            TravelerTM travelerTM = new TravelerTM(
                    travelerDTO.getTravelerId(),
                    travelerDTO.getName(),
                    travelerDTO.getEmail(),
                    travelerDTO.getContactNumber(),
                    travelerDTO.getNationality(),
                    travelerDTO.getIdNumber()
            );
            travelerTMS.add(travelerTM);
        }

        tblTraveler.setItems(travelerTMS);
    }

    public void loadNextTravelerId() throws SQLException, ClassNotFoundException {
        String nextTravelerId = travelerBOImpl.getNextId();
        lblTravelerId.setText(nextTravelerId);
    }

}
