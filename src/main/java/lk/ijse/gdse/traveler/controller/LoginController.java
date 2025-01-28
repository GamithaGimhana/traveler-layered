package lk.ijse.gdse.traveler.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.traveler.db.DBConnection;

import java.io.IOException;
import java.sql.*;

public class LoginController {

    @FXML
    private AnchorPane ancLogin;

    @FXML
    private AnchorPane ancLogin1;

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnLoginClicked(ActionEvent event) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance();

        // Ensure connection is open, reconnect if necessary
        if (dbConnection.getConnection() == null || dbConnection.getConnection().isClosed()) {
            dbConnection.reconnect(); // Re-initialize the connection if it's closed
        }

        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        txtUserName.setStyle(txtUserName.getStyle() + ";-fx-border-color: #7367F0;");
        txtPassword.setStyle(txtPassword.getStyle() + ";-fx-border-color: #7367F0;");

        if (userName.isEmpty() || password.isEmpty()) {
            txtUserName.setStyle(txtUserName.getStyle() + ";-fx-border-color: red;");
            txtPassword.setStyle(txtPassword.getStyle() + ";-fx-border-color: red;");

            new Alert(Alert.AlertType.WARNING, "Username or Password cannot be empty!").show();
            return;
        }

        try {
            String userType = validateLogin(userName, password, dbConnection.getConnection());
            if (userType != null) {
                navigateToMenu(userType);
            } else {
                txtUserName.setStyle(txtUserName.getStyle() + ";-fx-border-color: red;");
                txtPassword.setStyle(txtPassword.getStyle() + ";-fx-border-color: red;");
                new Alert(Alert.AlertType.ERROR, "Invalid Username or Password!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Error loading the menu: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private String validateLogin(String userName, String password, Connection connection) throws SQLException {
        String adminQuery = "select username from admin where username = ? and password = ?";
        String cashierQuery = "select username from cashier where username = ? and password = ?";

        // Check for admin login
        if (checkCredentials(connection, adminQuery, userName, password)) {
            return "admin";
        }

        // Check for cashier login
        if (checkCredentials(connection, cashierQuery, userName, password)) {
            return "cashier";
        }

        // Return null if no match
        return null;
    }


    private boolean checkCredentials(Connection connection, String query, String userName, String password) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userName);
            stmt.setString(2, password);

            try (ResultSet resultSet = stmt.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private void navigateToMenu(String userType) throws IOException {
        ancLogin.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menuFx.fxml"));
        AnchorPane load = loader.load();

        // Pass the user type to the MenuController
        MenuController controller = loader.getController();
        controller.setUserType(userType);

        // Bind the loaded FXML to all edges of the content AnchorPane
        load.prefWidthProperty().bind(ancLogin.widthProperty());
        load.prefHeightProperty().bind(ancLogin.heightProperty());
        ancLogin.getChildren().add(load);
    }
}
