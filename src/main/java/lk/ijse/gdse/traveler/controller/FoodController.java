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
import lk.ijse.gdse.traveler.bo.custom.impl.AdminBOImpl;
import lk.ijse.gdse.traveler.bo.custom.impl.FoodBOImpl;
import lk.ijse.gdse.traveler.dto.FoodDTO;
import lk.ijse.gdse.traveler.view.tdm.FoodTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class FoodController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<FoodTM, String> colContact;

    @FXML
    private TableColumn<FoodTM, String> colFoodnId;

    @FXML
    private TableColumn<FoodTM, String> colName;

    @FXML
    private TableColumn<FoodTM, String> colType;

    @FXML
    private Label lblFoodId;

    @FXML
    private TableView<FoodTM> tblFood;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtType;

//    FoodBOImpl foodBOImpl = new FoodBOImpl();
    FoodBOImpl foodBOImpl = (FoodBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.FOOD);

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String foodId = lblFoodId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = foodBOImpl.delete(foodId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Food deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Food...!").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String foodId = lblFoodId.getText();
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
            FoodDTO foodDTO = new FoodDTO(
                    foodId,
                    name,
                    type,
                    contact
            );

            boolean isSaved = foodBOImpl.save(foodDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Food saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Food...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String foodId = lblFoodId.getText();
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
            FoodDTO foodDTO = new FoodDTO(
                    foodId,
                    name,
                    type,
                    contact
            );

            boolean isSaved = foodBOImpl.update(foodDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Food updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update Food...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        FoodTM foodTM = tblFood.getSelectionModel().getSelectedItem();
        if (foodTM != null) {
            lblFoodId.setText(foodTM.getFoodServiceId());
            txtName.setText(foodTM.getName());
            txtType.setText(foodTM.getType());
            txtContact.setText(foodTM.getContact());

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
        colFoodnId.setCellValueFactory(new PropertyValueFactory<>("foodServiceId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        // inside initialize method
        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load id").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextFoodId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtType.setText("");
        txtContact.setText("");
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<FoodDTO> foodDTOS = foodBOImpl.getAll();

        ObservableList<FoodTM> foodTMS = FXCollections.observableArrayList();

        for (FoodDTO foodDTO : foodDTOS) {
            FoodTM foodTM = new FoodTM(
                    foodDTO.getFoodServiceId(),
                    foodDTO.getName(),
                    foodDTO.getType(),
                    foodDTO.getContact()
            );
            foodTMS.add(foodTM);
        }

        tblFood.setItems(foodTMS);
    }

    public void loadNextFoodId() throws SQLException, ClassNotFoundException {
        String nextFoodId = foodBOImpl.getNextId();
        lblFoodId.setText(nextFoodId);
    }
}
