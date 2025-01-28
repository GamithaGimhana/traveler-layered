package lk.ijse.gdse.traveler.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.traveler.db.DBConnection;
import lombok.Setter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class MenuController {

    @FXML
    private AnchorPane ancContent;

    @FXML
    private AnchorPane ancMenu;

    @FXML
    private AnchorPane ancMenuPage;

    @FXML
    private Button btnAbout;

    @FXML
    private Button btnDetails;

    @FXML
    private Button btnTravelers;

    @FXML
    private Button btnManagement;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnRequest;

    @FXML
    private Button btnLogOut;

    @Setter
    private String userType; // Store if admin or cashier

    @FXML
    void btnAboutOnAction(ActionEvent event) {
        navigateTo("/view/aboutFx.fxml");
    }

    @FXML
    void btnDetailsOnAction(ActionEvent event) {
        navigateTo("/view/detailFx.fxml");
    }

    @FXML
    void btnTravelersOnAction(ActionEvent event) {
        navigateTo("/view/travelerFx.fxml");
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) {
        try {
            ancMenuPage.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/menuFx.fxml"));

//          Bind the loaded FXML to all edges of the content anchorPane
            load.prefWidthProperty().bind(ancMenuPage.widthProperty());
            load.prefHeightProperty().bind(ancMenuPage.heightProperty());

            ancMenuPage.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    @FXML
    void btnManagementOnAction(ActionEvent event) throws IOException {
        ancContent.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/managementFx.fxml"));
        AnchorPane load = loader.load();

        // Passing user type
        ManagementController controller = loader.getController();
        controller.setUserType(userType);

        load.prefWidthProperty().bind(ancContent.widthProperty());
        load.prefHeightProperty().bind(ancContent.heightProperty());

        ancContent.getChildren().add(load);
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        navigateTo("/view/paymentFx.fxml");
    }

    @FXML
    void btnRequestOnAction(ActionEvent event) {
        navigateTo("/view/requestFx.fxml");
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            try {
                // Close the database connection if it's still open
                DBConnection dbConnection = DBConnection.getInstance();
                if (dbConnection != null && dbConnection.getConnection() != null && !dbConnection.getConnection().isClosed()) {
                    dbConnection.getConnection().close();
                    System.out.println("Database connection closed on logout.");
                }

                // Clear the current menu page and load the login page
                ancMenuPage.getChildren().clear();
                AnchorPane load = FXMLLoader.load(getClass().getResource("/view/loginFx.fxml"));
                load.prefWidthProperty().bind(ancMenuPage.widthProperty());
                load.prefHeightProperty().bind(ancMenuPage.heightProperty());

                ancMenuPage.getChildren().add(load);

            } catch (IOException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to load the login page!").show();
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Database error during logout!").show();
            }
        }
    }

    public void navigateTo(String fxmlPath) {
        try {
            ancContent.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

//          Bind the loaded FXML to all edges of the content anchorPane
            load.prefWidthProperty().bind(ancContent.widthProperty());
            load.prefHeightProperty().bind(ancContent.heightProperty());

            ancContent.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

}
