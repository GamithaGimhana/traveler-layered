package lk.ijse.gdse.traveler.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.traveler.bo.BOFactory;
import lk.ijse.gdse.traveler.bo.custom.impl.GuideBOImpl;
import lk.ijse.gdse.traveler.bo.custom.impl.HealthcareBOImpl;
import lk.ijse.gdse.traveler.dto.HealthcareDTO;
import lk.ijse.gdse.traveler.view.tdm.HealthcareTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class HealthcareController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private CheckBox chBoxAvailable;

    @FXML
    private TableColumn<HealthcareTM, String> colContact;

    @FXML
    private TableColumn<HealthcareTM, Boolean> colEmergencyServices;

    @FXML
    private TableColumn<HealthcareTM, String> colHealthcareId;

    @FXML
    private TableColumn<HealthcareTM, String> colName;

    @FXML
    private Label lblHealthcareId;

    @FXML
    private TableView<HealthcareTM> tblHealthcare;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

//    HealthcareBOImpl healthcareBOImpl = new HealthcareBOImpl();
    HealthcareBOImpl healthcareBOImpl = (HealthcareBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.HEALTHCARE);

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String healthcareId = lblHealthcareId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = healthcareBOImpl.delete(healthcareId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Healthcare deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Healthcare...!").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String healthcareId = lblHealthcareId.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        boolean isAvailable = chBoxAvailable.isSelected();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidContact = contact.matches(contactPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
        }

        if (!isValidContact) {
            txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidContact) {
            HealthcareDTO healthcareDTO = new HealthcareDTO(
                    healthcareId,
                    name,
                    contact,
                    isAvailable
            );

            boolean isSaved = healthcareBOImpl.save(healthcareDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Healthcare saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Healthcare...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String healthcareId = lblHealthcareId.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        boolean isAvailable = chBoxAvailable.isSelected();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidContact = contact.matches(contactPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
        }

        if (!isValidContact) {
            txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidContact) {
            HealthcareDTO healthcareDTO = new HealthcareDTO(
                    healthcareId,
                    name,
                    contact,
                    isAvailable
            );

            boolean isSaved = healthcareBOImpl.update(healthcareDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Healthcare updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update Healthcare...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        HealthcareTM healthcareTM = tblHealthcare.getSelectionModel().getSelectedItem();
        if (healthcareTM != null) {
            lblHealthcareId.setText(healthcareTM.getHealthcareId());
            txtName.setText(healthcareTM.getName());
            txtContact.setText(healthcareTM.getContact());
            chBoxAvailable.setSelected(healthcareTM.isEmergency());

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
        colHealthcareId.setCellValueFactory(new PropertyValueFactory<>("healthcareId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmergencyServices.setCellValueFactory(new PropertyValueFactory<>("emergency"));

        // inside initialize method
        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load id").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextHealthcareId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtContact.setText("");
        chBoxAvailable.setSelected(false);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<HealthcareDTO> healthcareDTOS = healthcareBOImpl.getAll();

        ObservableList<HealthcareTM> healthcareTMS = FXCollections.observableArrayList();

        for (HealthcareDTO healthcareDTO : healthcareDTOS) {
            HealthcareTM healthcareTM = new HealthcareTM(
                    healthcareDTO.getHealthcareId(),
                    healthcareDTO.getName(),
                    healthcareDTO.getContact(),
                    healthcareDTO.isEmergency()
            );
            healthcareTMS.add(healthcareTM);
        }

        tblHealthcare.setItems(healthcareTMS);
    }

    public void loadNextHealthcareId() throws SQLException, ClassNotFoundException {
        String nextHealthcareId = healthcareBOImpl.getNextId();
        lblHealthcareId.setText(nextHealthcareId);
    }
}
