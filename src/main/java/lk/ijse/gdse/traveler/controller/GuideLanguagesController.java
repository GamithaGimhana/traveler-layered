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
import lk.ijse.gdse.traveler.bo.BOFactory;
import lk.ijse.gdse.traveler.bo.custom.impl.GuideBOImpl;
import lk.ijse.gdse.traveler.bo.custom.impl.GuideLanguagesBOImpl;
import lk.ijse.gdse.traveler.bo.custom.impl.LanguageBOImpl;
import lk.ijse.gdse.traveler.dto.GuideDTO;
import lk.ijse.gdse.traveler.dto.GuideLanguagesDTO;
import lk.ijse.gdse.traveler.dto.LanguageDTO;
import lk.ijse.gdse.traveler.view.tdm.GuideLanguagesTM;

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

//    private final LanguageBOImpl languageBOImpl = new LanguageBOImpl();
//    private final GuideBOImpl guideBOImpl = new GuideBOImpl();
//    GuideLanguagesBOImpl guideLanguagesBOImpl = new GuideLanguagesBOImpl();

    GuideBOImpl guideBOImpl = (GuideBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.GUIDE);
    LanguageBOImpl languageBOImpl = (LanguageBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.LANGUAGE);
    GuideLanguagesBOImpl guideLanguagesBOImpl = (GuideLanguagesBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.GUIDELANGUAGE);

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String guideId = cmbGuideId.getValue();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = guideLanguagesBOImpl.delete(guideId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Guide Language deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Guide Language...!").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String guideId = cmbGuideId.getValue();
        String langId = cmbLanguageId.getValue();

        if (guideId != null && langId != null ) {
            GuideLanguagesDTO guideLanguagesDTO = new GuideLanguagesDTO(
                    langId,
                    guideId
            );

            boolean isSaved = guideLanguagesBOImpl.save(guideLanguagesDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Guide Language saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Guide Language...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String guideId = cmbGuideId.getValue();
        String langId = cmbLanguageId.getValue();

        if (guideId != null && langId != null ) {
            GuideLanguagesDTO guideLanguagesDTO = new GuideLanguagesDTO(
                    langId,
                    guideId
            );

            boolean isSaved = guideLanguagesBOImpl.update(guideLanguagesDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Guide Language saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Guide Language...!").show();
            }
        }
    }

    @FXML
    void cmbGuideOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedGuideId = cmbGuideId.getSelectionModel().getSelectedItem();
        GuideDTO guideDTO = guideBOImpl.findById(selectedGuideId);
        if (guideDTO != null) {
            lblGuideName.setText(guideDTO.getName());
        }
    }

    @FXML
    void cmbLangOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedLanguageId = cmbLanguageId.getSelectionModel().getSelectedItem();
        LanguageDTO languageDTO = languageBOImpl.findById(selectedLanguageId);
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
    void resetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
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

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadLanguageIds();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        lblLanguage.setText("");
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<GuideLanguagesDTO> guideLanguagesDTOS = guideLanguagesBOImpl.getAll();

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
            ArrayList<String> langIds = languageBOImpl.getAllIds();
            ObservableList<String> langIdsObservableList = FXCollections.observableArrayList(langIds);
            cmbLanguageId.setItems(langIdsObservableList);

            System.out.println("Language IDs loaded: " + langIdsObservableList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading languages IDs: " + e.getMessage()).show();
        }
    }

    private void loadGuideIds(String selectedLanguageId) throws SQLException {
        System.out.println("Loading guide IDs...");

        try {
            ArrayList<String> guideIds = guideBOImpl.getAllIds(selectedLanguageId);
            ObservableList<String> guideIdsObservableList = FXCollections.observableArrayList(guideIds);
            cmbGuideId.setItems(guideIdsObservableList);

            System.out.println("Guide IDs loaded: " + guideIdsObservableList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading guide IDs: " + e.getMessage()).show();
        }
    }

    private void loadGuideIds() throws SQLException {
        System.out.println("Loading guide IDs...");

        try {
            ArrayList<String> guideIds = guideBOImpl.getAllIds();
            ObservableList<String> guideIdsObservableList = FXCollections.observableArrayList(guideIds);
            cmbGuideId.setItems(guideIdsObservableList);

            System.out.println("Guide IDs loaded: " + guideIdsObservableList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading guide IDs: " + e.getMessage()).show();
        }
    }

}
