package lk.ijse.gdse.traveler.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DetailController {

    @FXML
    private AnchorPane ancDetails;

    @FXML
    private Button btnAccomodation;

    @FXML
    private Button btnAttraction;

    @FXML
    private Button btnFood;

    @FXML
    private Button btnHealthcare;

    @FXML
    private Label lblDetails;

    @FXML
    void btnAccomodationOnAction(ActionEvent event) {
        showPopup("/view/accomodationFx.fxml");
    }

    @FXML
    void btnAttractionOnAction(ActionEvent event) {
        showPopup("/view/attractionFx.fxml");
    }

    @FXML
    void btnFoodOnAction(ActionEvent event) {
        showPopup("/view/foodFx.fxml");
    }

    @FXML
    void btnHealthcareOnAction(ActionEvent event) {
        showPopup("/view/healthcareFx.fxml");
    }

    public void showPopup(String fxmlPath) {
        try {
            // Load the FXML for the popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane popupContent = loader.load();

            // Create a new Stage (window)
            Stage popupStage = new Stage();
            popupStage.setTitle("Details");

            // Set the popup content as the scene
            Scene popupScene = new Scene(popupContent);
            popupStage.setScene(popupScene);

            // Optionally set properties for the popup window
            popupStage.initModality(Modality.WINDOW_MODAL); // Block interaction with other windows
//            popupStage.setResizable(false); // Make the popup not resizable
            popupStage.centerOnScreen(); // Center the popup on the screen

            // Show the popup window
            popupStage.showAndWait(); // Wait until the popup is closed
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load popup!").show();
        }
    }

}
