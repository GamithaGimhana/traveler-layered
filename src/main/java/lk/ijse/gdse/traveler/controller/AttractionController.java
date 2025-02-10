package lk.ijse.gdse.traveler.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.traveler.bo.custom.impl.AttractionBOImpl;
import lk.ijse.gdse.traveler.dto.AttractionDTO;
import lk.ijse.gdse.traveler.view.tdm.AttractionTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AttractionController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<AttractionTM, String> colAttractionId;

    @FXML
    private TableColumn<AttractionTM, String> colDescription;

    @FXML
    private TableColumn<AttractionTM, String> colName;

    @FXML
    private TableColumn<AttractionTM, String> colOperatingHours;

    @FXML
    private TableColumn<AttractionTM, String> colType;

    @FXML
    private Label lblAttractionId;

    @FXML
    private TableView<AttractionTM> tblAttraction;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtOperatingHours;

    @FXML
    private TextField txtType;

    AttractionBOImpl attractionBOImpl = new AttractionBOImpl();

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String attractionId = lblAttractionId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = attractionBOImpl.delete(attractionId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Attraction deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Attraction...!").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String attractionId = lblAttractionId.getText();
        String name = txtName.getText();
        String type = txtType.getText();
        String hour = txtOperatingHours.getText();
        String desc = txtDesc.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtType.setStyle(txtType.getStyle() + ";-fx-border-color: #7367F0;");
        txtOperatingHours.setStyle(txtOperatingHours.getStyle() + ";-fx-border-color: #7367F0;");
        txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";

        boolean isValidName = name.matches(namePattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
        }

        if (isValidName) {
            AttractionDTO attractionDTO = new AttractionDTO(
                    attractionId,
                    name,
                    type,
                    hour,
                    desc
            );

            boolean isSaved = attractionBOImpl.save(attractionDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Attraction saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Attraction...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String attractionId = lblAttractionId.getText();
        String name = txtName.getText();
        String type = txtType.getText();
        String hour = txtOperatingHours.getText();
        String desc = txtDesc.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtType.setStyle(txtType.getStyle() + ";-fx-border-color: #7367F0;");
        txtOperatingHours.setStyle(txtOperatingHours.getStyle() + ";-fx-border-color: #7367F0;");
        txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";

        boolean isValidName = name.matches(namePattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
        }

        if (isValidName) {
            AttractionDTO attractionDTO = new AttractionDTO(
                    attractionId,
                    name,
                    type,
                    hour,
                    desc
            );

            boolean isSaved = attractionBOImpl.update(attractionDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Attraction update...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update Attraction...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        AttractionTM attractionTM = tblAttraction.getSelectionModel().getSelectedItem();
        if (attractionTM != null) {
            lblAttractionId.setText(attractionTM.getAttractionId());
            txtName.setText(attractionTM.getName());
            txtType.setText(attractionTM.getType());
            txtOperatingHours.setText(attractionTM.getOperatingHours());
            txtDesc.setText(attractionTM.getDescription());

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
        colAttractionId.setCellValueFactory(new PropertyValueFactory<>("attractionId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colOperatingHours.setCellValueFactory(new PropertyValueFactory<>("operatingHours"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        // inside initialize method
        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load id").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextAttractionId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtType.setText("");
        txtOperatingHours.setText("");
        txtDesc.setText("");
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<AttractionDTO> attractionDTOS = attractionBOImpl.getAll();

        ObservableList<AttractionTM> attractionTMS = FXCollections.observableArrayList();

        for (AttractionDTO attractionDTO : attractionDTOS) {
            AttractionTM attractionTM = new AttractionTM(
                    attractionDTO.getAttractionId(),
                    attractionDTO.getName(),
                    attractionDTO.getType(),
                    attractionDTO.getOperatingHours(),
                    attractionDTO.getDescription()
            );
            attractionTMS.add(attractionTM);
        }

        tblAttraction.setItems(attractionTMS);
    }

    public void loadNextAttractionId() throws SQLException, ClassNotFoundException {
        String nextAttractionId = attractionBOImpl.getNextId();
        lblAttractionId.setText(nextAttractionId);
    }
}
