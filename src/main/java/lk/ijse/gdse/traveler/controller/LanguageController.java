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
import lk.ijse.gdse.traveler.bo.custom.impl.LanguageBOImpl;
import lk.ijse.gdse.traveler.dto.LanguageDTO;
import lk.ijse.gdse.traveler.view.tdm.LanguagesTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class LanguageController implements Initializable {

    @FXML
    private AnchorPane ancLanguage;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<LanguagesTM, String> colLang;

    @FXML
    private TableColumn<LanguagesTM, String> colLangId;

    @FXML
    private Label lblLangId;

    @FXML
    private TableView<LanguagesTM> tblLanguage;

    @FXML
    private TextField txtLanguage;

    LanguageBOImpl languageBOImpl = new LanguageBOImpl();

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String langId = lblLangId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = languageBOImpl.delete(langId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Language deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Language...!").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String langId = lblLangId.getText();
        String language = txtLanguage.getText();

        txtLanguage.setStyle(txtLanguage.getStyle() + ";-fx-border-color: #7367F0;");

        String langPattern = "^[A-Za-z ]+$";

        boolean isValidLanguage = language.matches(langPattern);

        if (!isValidLanguage) {
            System.out.println(txtLanguage.getStyle());
            txtLanguage.setStyle(txtLanguage.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid Language.............");
        }

        if (isValidLanguage) {
            LanguageDTO languageDTO = new LanguageDTO(
                    langId,
                    language
            );

            boolean isSaved = languageBOImpl.save(languageDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Language saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Language...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String langId = lblLangId.getText();
        String language = txtLanguage.getText();

        txtLanguage.setStyle(txtLanguage.getStyle() + ";-fx-border-color: #7367F0;");

        String langPattern = "^[A-Za-z ]+$";

        boolean isValidLanguage = language.matches(langPattern);

        if (!isValidLanguage) {
            System.out.println(txtLanguage.getStyle());
            txtLanguage.setStyle(txtLanguage.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid Language.............");
        }

        if (isValidLanguage) {
            LanguageDTO languageDTO = new LanguageDTO(
                    langId,
                    language
            );

            boolean isSaved = languageBOImpl.update(languageDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Language updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update Language...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        LanguagesTM languagesTM = tblLanguage.getSelectionModel().getSelectedItem();
        if (languagesTM != null) {
            lblLangId.setText(languagesTM.getLangId());
            txtLanguage.setText(languagesTM.getLanguage());

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
        colLang.setCellValueFactory(new PropertyValueFactory<>("language"));

        // inside initialize method
        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Language id").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextLangId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtLanguage.setText("");
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<LanguageDTO> languageDTOS = languageBOImpl.getAll();

        ObservableList<LanguagesTM> languagesTMS = FXCollections.observableArrayList();

        for (LanguageDTO languageDTO : languageDTOS) {
            LanguagesTM languagesTM = new LanguagesTM(
                    languageDTO.getLangId(),
                    languageDTO.getLanguage()
            );
            languagesTMS.add(languagesTM);
        }

        tblLanguage.setItems(languagesTMS);
    }

    public void loadNextLangId() throws SQLException, ClassNotFoundException {
        String nextLangId = languageBOImpl.getNextId();
        lblLangId.setText(nextLangId);
    }
}
