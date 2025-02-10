package lk.ijse.gdse.traveler.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.traveler.bo.custom.impl.DriverBOImpl;
import lk.ijse.gdse.traveler.dto.DriverDTO;
import lk.ijse.gdse.traveler.view.tdm.DriverTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class DriverController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private CheckBox chBoxAvailable;

    @FXML
    private TableColumn<DriverTM, String> colContact;

    @FXML
    private TableColumn<DriverTM, String> colDriverId;

    @FXML
    private TableColumn<DriverTM, String> colLic;

    @FXML
    private TableColumn<DriverTM, String> colName;

    @FXML
    private TableColumn<DriverTM, Boolean> colStatus;

    @FXML
    private Label lblDriverId;

    @FXML
    private TableView<DriverTM> tblDriver;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtLic;

    @FXML
    private TextField txtName;

    DriverBOImpl driverBOImpl = new DriverBOImpl();

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String driverId = lblDriverId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = driverBOImpl.delete(driverId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Driver deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Driver...!").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String driverId = lblDriverId.getText();
        String name = txtName.getText();
        String lic = txtLic.getText();
        String contact = txtContact.getText();
        boolean status = chBoxAvailable.isSelected();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtLic.setStyle(txtLic.getStyle() + ";-fx-border-color: #7367F0;");
        txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String licPattern = "^[A-Za-z ][0-9]+$";
        String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidLic = lic.matches(licPattern);
        boolean isValidContact = contact.matches(contactPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
        }

        if (!isValidLic) {
            txtLic.setStyle(txtLic.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidContact) {
            txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: red;");
        }


        if (isValidName && isValidLic && isValidContact) {
            DriverDTO driverDTO = new DriverDTO(
                    driverId,
                    name,
                    lic,
                    contact,
                    status
            );

            boolean isSaved = driverBOImpl.save(driverDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Driver saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Driver...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String driverId = lblDriverId.getText();
        String name = txtName.getText();
        String lic = txtLic.getText();
        String contact = txtContact.getText();
        boolean status = chBoxAvailable.isSelected();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtLic.setStyle(txtLic.getStyle() + ";-fx-border-color: #7367F0;");
        txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String licPattern = "^[A-Za-z ][0-9]+$";
        String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidLic = lic.matches(licPattern);
        boolean isValidContact = contact.matches(contactPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
        }

        if (!isValidLic) {
            txtLic.setStyle(txtLic.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidContact) {
            txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: red;");
        }


        if (isValidName && isValidLic && isValidContact) {
            DriverDTO driverDTO = new DriverDTO(
                    driverId,
                    name,
                    lic,
                    contact,
                    status
            );

            boolean isSaved = driverBOImpl.update(driverDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Driver saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Driver...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        DriverTM driverTM = tblDriver.getSelectionModel().getSelectedItem();
        if (driverTM != null) {
            lblDriverId.setText(driverTM.getDriverId());
            txtName.setText(driverTM.getName());
            txtLic.setText(driverTM.getLicenseNumber());
            txtContact.setText(driverTM.getContactNumber());
            chBoxAvailable.setSelected(driverTM.isAvailabilityStatus());

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
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLic.setCellValueFactory(new PropertyValueFactory<>("licenseNumber"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("availabilityStatus"));

        // inside initialize method
        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Driver id").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextDriverId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtLic.setText("");
        txtContact.setText("");
        chBoxAvailable.setSelected(false);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<DriverDTO> driverDTOS = driverBOImpl.getAll();

        ObservableList<DriverTM> driverTMS = FXCollections.observableArrayList();

        for (DriverDTO driverDTO : driverDTOS) {
            DriverTM driverTM = new DriverTM(
                    driverDTO.getDriverId(),
                    driverDTO.getName(),
                    driverDTO.getLicenseNumber(),
                    driverDTO.getContactNumber(),
                    driverDTO.isAvailabilityStatus()
            );
            driverTMS.add(driverTM);
        }

        tblDriver.setItems(driverTMS);
    }

    public void loadNextDriverId() throws SQLException, ClassNotFoundException {
        String nextDriverId = driverBOImpl.getNextId();
        lblDriverId.setText(nextDriverId);
    }
}
