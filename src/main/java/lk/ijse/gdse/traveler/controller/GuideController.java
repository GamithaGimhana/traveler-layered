package lk.ijse.gdse.traveler.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.traveler.bo.BOFactory;
import lk.ijse.gdse.traveler.bo.custom.impl.GuideBOImpl;
import lk.ijse.gdse.traveler.dto.GuideDTO;
import lk.ijse.gdse.traveler.view.tdm.GuideTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class GuideController implements Initializable {

    @FXML
    private AnchorPane ancGuide;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private CheckBox chBoxAvailable;

    @FXML
    private TableColumn<GuideTM, Boolean> colAvailability;

    @FXML
    private TableColumn<GuideTM, String> colContact;

    @FXML
    private TableColumn<GuideTM, String> colGuideId;

    @FXML
    private TableColumn<GuideTM, String> colLic;

    @FXML
    private TableColumn<GuideTM, String> colName;

    @FXML
    private Label lblGuideId;

    @FXML
    private TableView<GuideTM> tblGuide;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtLic;

    @FXML
    private TextField txtName;

//    GuideBOImpl guideBOImpl = new GuideBOImpl();
    GuideBOImpl guideBOImpl = (GuideBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.GUIDE);

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String guideId = lblGuideId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = guideBOImpl.delete(guideId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Guide deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Guide...!").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String guideId = lblGuideId.getText();
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
            GuideDTO guideDTO = new GuideDTO(
                    guideId,
                    name,
                    lic,
                    contact,
                    status
            );

            boolean isSaved = guideBOImpl.save(guideDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Guide saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Guide...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String guideId = lblGuideId.getText();
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
            GuideDTO guideDTO = new GuideDTO(
                    guideId,
                    name,
                    lic,
                    contact,
                    status
            );

            boolean isSaved = guideBOImpl.update(guideDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Guide updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update Guide...!").show();
            }
        }
    }

    @FXML
    void guideLanguageOnAction(ActionEvent event) {
        navigateTo("/view/guideLanguagesFx.fxml");
    }

    @FXML
    void langOnAction(ActionEvent event) {
        navigateTo("/view/languageFx.fxml");
    }

    @FXML
    void onClickTable(MouseEvent event) {
        GuideTM guideTM = tblGuide.getSelectionModel().getSelectedItem();
        if (guideTM != null) {
            lblGuideId.setText(guideTM.getGuideId());
            txtName.setText(guideTM.getName());
            txtLic.setText(guideTM.getLicenseNumber());
            txtContact.setText(guideTM.getContactNumber());
            chBoxAvailable.setSelected(guideTM.isAvailabilityStatus());

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
        colGuideId.setCellValueFactory(new PropertyValueFactory<>("guideId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLic.setCellValueFactory(new PropertyValueFactory<>("licenseNumber"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availabilityStatus"));

        // inside initialize method
        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Guide id").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextGuideId();
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
        ArrayList<GuideDTO> guideDTOS = guideBOImpl.getAll();

        ObservableList<GuideTM> guideTMS = FXCollections.observableArrayList();

        for (GuideDTO guideDTO : guideDTOS) {
            GuideTM guideTM = new GuideTM(
                    guideDTO.getGuideId(),
                    guideDTO.getName(),
                    guideDTO.getLicenseNumber(),
                    guideDTO.getContactNumber(),
                    guideDTO.isAvailabilityStatus()
            );
            guideTMS.add(guideTM);
        }

        tblGuide.setItems(guideTMS);
    }

    public void loadNextGuideId() throws SQLException, ClassNotFoundException {
        String nextGuideId = guideBOImpl.getNextId();
        lblGuideId.setText(nextGuideId);
    }

    public void navigateTo(String fxmlPath) {
        try {
            ancGuide.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

//          Bind the loaded FXML to all edges of the content anchorPane
            load.prefWidthProperty().bind(ancGuide.widthProperty());
            load.prefHeightProperty().bind(ancGuide.heightProperty());

            ancGuide.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }
}
