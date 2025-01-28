package lk.ijse.gdse.traveler.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.traveler.dto.VehicleDTO;
import lk.ijse.gdse.traveler.dto.tm.VehicleTM;
import lk.ijse.gdse.traveler.model.VehicleModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class VehicleController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set table column to cell factory value
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colLicPlate.setCellValueFactory(new PropertyValueFactory<>("licensePlateNumber"));
        colDailyPrice.setCellValueFactory(new PropertyValueFactory<>("dailyPrice"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availabilityStatus"));

        // inside initialize method
        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Vehicle id").show();
        }
    }

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private CheckBox chBoxAvailable;

    @FXML
    private TableColumn<VehicleTM, Boolean> colAvailability;

    @FXML
    private TableColumn<VehicleTM, Double> colDailyPrice;

    @FXML
    private TableColumn<VehicleTM, String> colLicPlate;

    @FXML
    private TableColumn<VehicleTM, String> colModel;

    @FXML
    private TableColumn<VehicleTM, String> colType;

    @FXML
    private TableColumn<VehicleTM, String> colVehicleId;

    @FXML
    private Label lblVehicleId;

    @FXML
    private TableView<VehicleTM> tblVehicle;

    @FXML
    private TextField txtLicPlate;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtType;

    @FXML
    private TextField txtDailyPrice;

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String vehicleId = lblVehicleId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = vehicleModel.deleteVehicle(vehicleId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Vehicle deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Vehicle...!").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String vehicleId = lblVehicleId.getText();
        String type = txtType.getText();
        String model = txtModel.getText();
        String licPlate = txtLicPlate.getText();
        double dailyPrice = Double.parseDouble(txtDailyPrice.getText());
        boolean status = chBoxAvailable.isSelected();

        txtType.setStyle(txtType.getStyle() + ";-fx-border-color: #7367F0;");
        txtModel.setStyle(txtModel.getStyle() + ";-fx-border-color: #7367F0;");
        txtLicPlate.setStyle(txtLicPlate.getStyle() + ";-fx-border-color: #7367F0;");

        String typePattern = "^[A-Za-z ]+$";
        String modelPattern = "^[A-Za-z ]+$";
        String licPlatePattern = "^[A-Za-z ][0-9]{4}+$";

        boolean isValidType = type.matches(typePattern);
        boolean isValidModel = model.matches(modelPattern);
        boolean isValidLicPlate = licPlate.matches(licPlatePattern);

        if (!isValidType) {
            System.out.println(txtType.getStyle());
            txtType.setStyle(txtType.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid type.............");
        }

        if (!isValidModel) {
            txtModel.setStyle(txtModel.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidLicPlate) {
            txtLicPlate.setStyle(txtLicPlate.getStyle() + ";-fx-border-color: red;");
        }


        if (isValidType && isValidModel && isValidLicPlate) {
            VehicleDTO vehicleDTO = new VehicleDTO(
                    vehicleId,
                    type,
                    model,
                    licPlate,
                    dailyPrice,
                    status
            );

            boolean isSaved = vehicleModel.saveVehicle(vehicleDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Vehicle saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Vehicle...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String vehicleId = lblVehicleId.getText();
        String type = txtType.getText();
        String model = txtModel.getText();
        String licPlate = txtLicPlate.getText();
        double dailyPrice = Double.parseDouble(txtDailyPrice.getText());
        boolean status = chBoxAvailable.isSelected();

        txtType.setStyle(txtType.getStyle() + ";-fx-border-color: #7367F0;");
        txtModel.setStyle(txtModel.getStyle() + ";-fx-border-color: #7367F0;");
        txtLicPlate.setStyle(txtLicPlate.getStyle() + ";-fx-border-color: #7367F0;");

        String typePattern = "^[A-Za-z ]+$";
        String modelPattern = "^[A-Za-z ]+$";
        String licPlatePattern = "^[A-Za-z ][0-9]{4}+$";

        boolean isValidType = type.matches(typePattern);
        boolean isValidModel = model.matches(modelPattern);
        boolean isValidLicPlate = licPlate.matches(licPlatePattern);

        if (!isValidType) {
            System.out.println(txtType.getStyle());
            txtType.setStyle(txtType.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid type.............");
        }

        if (!isValidModel) {
            txtModel.setStyle(txtModel.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidLicPlate) {
            txtLicPlate.setStyle(txtLicPlate.getStyle() + ";-fx-border-color: red;");
        }


        if (isValidType && isValidModel && isValidLicPlate) {
            VehicleDTO vehicleDTO = new VehicleDTO(
                    vehicleId,
                    type,
                    model,
                    licPlate,
                    dailyPrice,
                    status
            );

            boolean isSaved = vehicleModel.updateVehicle(vehicleDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Vehicle saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Vehicle...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        VehicleTM vehicleTM = tblVehicle.getSelectionModel().getSelectedItem();
        if (vehicleTM != null) {
            lblVehicleId.setText(vehicleTM.getVehicleId());
            txtType.setText(vehicleTM.getVehicleType());
            txtModel.setText(vehicleTM.getModel());
            txtLicPlate.setText(vehicleTM.getLicensePlateNumber());
            txtDailyPrice.setText(String.valueOf(vehicleTM.getDailyPrice()));
            chBoxAvailable.setSelected(vehicleTM.isAvailabilityStatus());

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
        loadNextVehicleId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtType.setText("");
        txtModel.setText("");
        txtLicPlate.setText("");
        txtDailyPrice.setText("");
        chBoxAvailable.setSelected(false);
    }

    VehicleModel vehicleModel = new VehicleModel();

    private void loadTableData() throws SQLException {
        ArrayList<VehicleDTO> vehicleDTOS = vehicleModel.getAllVehicles();

        ObservableList<VehicleTM> vehicleTMS = FXCollections.observableArrayList();

        for (VehicleDTO vehicleDTO : vehicleDTOS) {
            VehicleTM vehicleTM = new VehicleTM(
                    vehicleDTO.getVehicleId(),
                    vehicleDTO.getVehicleType(),
                    vehicleDTO.getModel(),
                    vehicleDTO.getLicensePlateNumber(),
                    vehicleDTO.getDailyPrice(),
                    vehicleDTO.isAvailabilityStatus()
            );
            vehicleTMS.add(vehicleTM);
        }

        tblVehicle.setItems(vehicleTMS);
    }

    public void loadNextVehicleId() throws SQLException {
        String nextVehicleId = vehicleModel.getNextVehicleId();
        lblVehicleId.setText(nextVehicleId);
    }

}
