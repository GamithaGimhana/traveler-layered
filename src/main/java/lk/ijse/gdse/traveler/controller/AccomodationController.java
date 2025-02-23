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
import lk.ijse.gdse.traveler.bo.custom.AccomodationBO;
import lk.ijse.gdse.traveler.bo.custom.impl.AccomodationBOImpl;
import lk.ijse.gdse.traveler.bo.custom.impl.AdminBOImpl;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;
import lk.ijse.gdse.traveler.view.tdm.AccomodationTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AccomodationController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<AccomodationTM, String> colAccomodationId;

    @FXML
    private TableColumn<AccomodationTM, String> colContact;

    @FXML
    private TableColumn<AccomodationTM, String> colName;

    @FXML
    private TableColumn<AccomodationTM, String> colType;

    @FXML
    private Label lblAccomodationId;

    @FXML
    private TableView<AccomodationTM> tblAccomodation;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtType;

//    AccomodationBOImpl accomodationBOImpl = new AccomodationBOImpl();
    AccomodationBOImpl accomodationBOImpl = (AccomodationBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.ACCOMODATION);

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String accommodationId = lblAccomodationId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = accomodationBOImpl.delete(accommodationId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Accomodation deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Accomodation...!").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String accomodationId = lblAccomodationId.getText();
        String name = txtName.getText();
        String type = txtType.getText();
        String contact = txtContact.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtType.setStyle(txtType.getStyle() + ";-fx-border-color: #7367F0;");
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
            AccomodationDTO accomodationDTO = new AccomodationDTO(
                    accomodationId,
                    name,
                    type,
                    contact
            );

            boolean isSaved = accomodationBOImpl.save(accomodationDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Accommodation saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Accommodation...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String accomodationId = lblAccomodationId.getText();
        String name = txtName.getText();
        String type = txtType.getText();
        String contact = txtContact.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtType.setStyle(txtType.getStyle() + ";-fx-border-color: #7367F0;");
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
            AccomodationDTO accomodationDTO = new AccomodationDTO(
                    accomodationId,
                    name,
                    type,
                    contact
            );

            boolean isSaved = accomodationBOImpl.update(accomodationDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Accommodation updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update Accommodation...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        AccomodationTM accomodationTM = tblAccomodation.getSelectionModel().getSelectedItem();
        if (accomodationTM != null) {
            lblAccomodationId.setText(accomodationTM.getAccomodationId());
            txtName.setText(accomodationTM.getName());
            txtType.setText(accomodationTM.getType());
            txtContact.setText(accomodationTM.getContactNumber());

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
        colAccomodationId.setCellValueFactory(new PropertyValueFactory<>("accomodationId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));

        // inside initialize method
        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load id").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextAccomodationId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtType.setText("");
        txtContact.setText("");
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<AccomodationDTO> accomodationDTOS = accomodationBOImpl.getAll();

        ObservableList<AccomodationTM> accomodationTMS = FXCollections.observableArrayList();

        for (AccomodationDTO accomodationDTO : accomodationDTOS) {
            AccomodationTM accomodationTM = new AccomodationTM(
                    accomodationDTO.getAccomodationId(),
                    accomodationDTO.getName(),
                    accomodationDTO.getType(),
                    accomodationDTO.getContactNumber()
            );
            accomodationTMS.add(accomodationTM);
        }

        tblAccomodation.setItems(accomodationTMS);
    }

    public void loadNextAccomodationId() throws SQLException, ClassNotFoundException {
        String nextAccomodationId = accomodationBOImpl.getNextId();
        lblAccomodationId.setText(nextAccomodationId);
    }
}
