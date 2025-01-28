package lk.ijse.gdse.traveler.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.traveler.dto.GuideDTO;
import lk.ijse.gdse.traveler.dto.GuideLanguagesDTO;
import lk.ijse.gdse.traveler.dto.LanguageDTO;
import lk.ijse.gdse.traveler.dto.tm.GuideLanguagesTM;
import lk.ijse.gdse.traveler.model.GuideLanguagesModel;
import lk.ijse.gdse.traveler.model.GuideModel;
import lk.ijse.gdse.traveler.model.LanguageModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class GuideLanguagesController implements Initializable {

    @FXML
    private AnchorPane ancGuideLanguage;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbGuideId;

    @FXML
    private ComboBox<String> cmbLanguageId;

    @FXML
    private TableColumn<GuideLanguagesTM, String> colGuideId;

    @FXML
    private TableColumn<GuideLanguagesTM, String> colLangId;

    @FXML
    private Label lblGuideName;

    @FXML
    private Label lblLanguage;

    @FXML
    private TableView<GuideLanguagesTM> tblGuideLanguage;

    private final LanguageModel languageModel = new LanguageModel();
    private final GuideModel guideModel = new GuideModel();

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String guideId = cmbGuideId.getValue();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = guideLanguagesModel.deleteGLang(guideId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Guide Language deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Guide Language...!").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String guideId = cmbGuideId.getValue();
        String langId = cmbLanguageId.getValue();

        if (guideId != null && langId != null ) {
            GuideLanguagesDTO guideLanguagesDTO = new GuideLanguagesDTO(
                    langId,
                    guideId
            );

            boolean isSaved = guideLanguagesModel.saveGLang(guideLanguagesDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Guide Language saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Guide Language...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String guideId = cmbGuideId.getValue();
        String langId = cmbLanguageId.getValue();

        if (guideId != null && langId != null ) {
            GuideLanguagesDTO guideLanguagesDTO = new GuideLanguagesDTO(
                    langId,
                    guideId
            );

            boolean isSaved = guideLanguagesModel.updateGLang(guideLanguagesDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Guide Language saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Guide Language...!").show();
            }
        }
    }

    @FXML
    void cmbGuideOnAction(ActionEvent event) throws SQLException {
        String selectedGuideId = cmbGuideId.getSelectionModel().getSelectedItem();
        GuideDTO guideDTO = guideModel.findById(selectedGuideId);
        if (guideDTO != null) {
            lblGuideName.setText(guideDTO.getName());
        }
    }

    @FXML
    void cmbLangOnAction(ActionEvent event) throws SQLException {
        String selectedLanguageId = cmbLanguageId.getSelectionModel().getSelectedItem();
        LanguageDTO languageDTO = languageModel.findById(selectedLanguageId);
        if (languageDTO != null) {
            lblLanguage.setText(languageDTO.getLanguage());
        }
        cmbGuideId.setDisable(false);
        loadGuideIds();
    }

    @FXML
    void onClickTable(MouseEvent event) {
        GuideLanguagesTM guideLanguagesTM = tblGuideLanguage.getSelectionModel().getSelectedItem();
        if (guideLanguagesTM != null) {
            cmbLanguageId.setValue(guideLanguagesTM.getLangId());
            cmbGuideId.setValue(guideLanguagesTM.getGuideId());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set table column to cell factory value
        colLangId.setCellValueFactory(new PropertyValueFactory<>("langId"));
        colGuideId.setCellValueFactory(new PropertyValueFactory<>("guideId"));

        cmbGuideId.setDisable(true);
        // inside initialize method
        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Guide id").show();
        }
    }

    private void refreshPage() throws SQLException {
        loadLanguageIds();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        lblLanguage.setText("");
    }

    GuideLanguagesModel guideLanguagesModel = new GuideLanguagesModel();

    private void loadTableData() throws SQLException {
        ArrayList<GuideLanguagesDTO> guideLanguagesDTOS = guideLanguagesModel.getAllGLanguages();

        ObservableList<GuideLanguagesTM> guideLanguagesTMS = FXCollections.observableArrayList();

        for (GuideLanguagesDTO guideLanguagesDTO : guideLanguagesDTOS) {
            GuideLanguagesTM guideLanguagesTM = new GuideLanguagesTM(
                    guideLanguagesDTO.getLangId(),
                    guideLanguagesDTO.getGuideId()
            );
            guideLanguagesTMS.add(guideLanguagesTM);
        }

        tblGuideLanguage.setItems(guideLanguagesTMS);
    }

    private void loadLanguageIds() throws SQLException {
        System.out.println("Loading language IDs...");

        try {
            ArrayList<String> langIds = languageModel.getAllLangIds();
            ObservableList<String> langIdsObservableList = FXCollections.observableArrayList(langIds);
            cmbLanguageId.setItems(langIdsObservableList);

            System.out.println("Language IDs loaded: " + langIdsObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading languages IDs: " + e.getMessage()).show();
        }
    }

    private void loadGuideIds(String selectedLanguageId) throws SQLException {
        System.out.println("Loading guide IDs...");

        try {
            ArrayList<String> guideIds = guideModel.getAllGuideIds(selectedLanguageId);
            ObservableList<String> guideIdsObservableList = FXCollections.observableArrayList(guideIds);
            cmbGuideId.setItems(guideIdsObservableList);

            System.out.println("Guide IDs loaded: " + guideIdsObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading guide IDs: " + e.getMessage()).show();
        }
    }

    private void loadGuideIds() throws SQLException {
        System.out.println("Loading guide IDs...");

        try {
            ArrayList<String> guideIds = guideModel.getAllGuideIds();
            ObservableList<String> guideIdsObservableList = FXCollections.observableArrayList(guideIds);
            cmbGuideId.setItems(guideIdsObservableList);

            System.out.println("Guide IDs loaded: " + guideIdsObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading guide IDs: " + e.getMessage()).show();
        }
    }

}
