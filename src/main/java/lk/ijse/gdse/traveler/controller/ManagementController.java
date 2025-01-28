package lk.ijse.gdse.traveler.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ManagementController {

    @FXML
    private AnchorPane ancManagement;

    @FXML
    private Button btnAdmins;

    @FXML
    private Button btnCashiers;

    @FXML
    private Button btnDrivers;

    @FXML
    private Button btnGiudes;

    @FXML
    private Button btnVehicles;

    @FXML
    private Label lblManagement;

    private String userType; // Store if admin or cashier

    @FXML
    void btnAdminsOnAction(ActionEvent event) {
        navigateTo("/view/adminFx.fxml");
    }

    @FXML
    void btnCashiersOnAction(ActionEvent event) {
        navigateTo("/view/cashierFx.fxml");
    }

    @FXML
    void btnDriversOnAction(ActionEvent event) {
        navigateTo("/view/driverFx.fxml");
    }

    @FXML
    void btnGiudesOnAction(ActionEvent event) {
        navigateTo("/view/guideFx.fxml");
    }

    @FXML
    void btnVehiclesOnAction(ActionEvent event) {
        navigateTo("/view/vehicleFx.fxml");
    }

    public void navigateTo(String fxmlPath) {
        try {
            ancManagement.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

//  -------- Loaded anchor edges are bound to the content anchor --------
//      (1) Bind the loaded FXML to all edges of the content anchorPane
            load.prefWidthProperty().bind(ancManagement.widthProperty());
            load.prefHeightProperty().bind(ancManagement.heightProperty());

            ancManagement.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    public void setUserType(String userType) {
        this.userType = userType;
        configureAccess();
    }

    private void configureAccess() {
        if ("cashier".equals(userType)) {
            // Admin features disabling to cashier
            btnAdmins.setDisable(true);
            btnDrivers.setDisable(true);
            btnGiudes.setDisable(true);
            btnVehicles.setDisable(true);
        }
    }

}
