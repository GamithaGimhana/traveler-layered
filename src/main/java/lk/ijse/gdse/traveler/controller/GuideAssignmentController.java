package lk.ijse.gdse.traveler.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import lk.ijse.gdse.traveler.bo.custom.impl.GuideAssignmentBOImpl;
import lk.ijse.gdse.traveler.bo.custom.impl.GuideBOImpl;
import lk.ijse.gdse.traveler.bo.custom.impl.LanguageBOImpl;
import lk.ijse.gdse.traveler.bo.custom.impl.TravelerBOImpl;
import lk.ijse.gdse.traveler.dto.*;
import lk.ijse.gdse.traveler.view.tdm.BookGuideTM;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GuideAssignmentController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("Initializing the page...");

            dateSet(dateStart);
            dateSet(dateEnd);

            // Disable components initially
            dateEnd.setDisable(true);
            cmbGuideId.setDisable(true);

            setCellValues();

            refreshPage();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load data during initialization: " + e.getMessage()).show();
        }
    }

    @FXML
    private AnchorPane ancGuideAssignment;

    @FXML
    private Button btnAddToBooking;

    @FXML
    private ComboBox<String> cmbGuideId;

    @FXML
    private ComboBox<String> cmbLangId;

    @FXML
    private ComboBox<String> cmbTravelerId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<BookGuideTM, LocalDate> colEndDate;

    @FXML
    private TableColumn<BookGuideTM, String> colGuideId;

    @FXML
    private TableColumn<BookGuideTM, String> colRequestId;

    @FXML
    private TableColumn<BookGuideTM, LocalDate> colStartDate;

    @FXML
    private TableColumn<BookGuideTM, String> colTravelerId;

    @FXML
    private DatePicker dateEnd;

    @FXML
    private DatePicker dateStart;

    @FXML
    private Label lblGuideName;

    @FXML
    private Label lblRequestId;

    @FXML
    private Label lblTravelerName;

    @FXML
    private Label requestDate;

    @FXML
    private Label lblLanguage;

    @FXML
    private TableView<BookGuideTM> tblBooking;

    private String traveler;

    private String requestId;

    private final TravelerBOImpl travelerBOImpl = new TravelerBOImpl();
    private final GuideBOImpl guideBOImpl = new GuideBOImpl();
    private final LanguageBOImpl languageBOImpl = new LanguageBOImpl();
    private final GuideAssignmentBOImpl guideAssignmentBOImpl = new GuideAssignmentBOImpl();

    private final ObservableList<BookGuideTM> bookGuideTMS = FXCollections.observableArrayList();


    @FXML
    void btnAddToBookingOnAction(ActionEvent event) {
        String rId = lblRequestId.getText();
        String tId = cmbTravelerId.getValue();
        String selectedLanguageId = cmbLangId.getValue();
        String gId = cmbGuideId.getValue();
        LocalDate startDate = dateStart.getValue();
        LocalDate endDate = dateEnd.getValue();

        if (selectedLanguageId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a language!").show();
            return;
        }

        if (startDate == null || endDate == null) {
            new Alert(Alert.AlertType.ERROR, "Please select both start and end dates.").show();
            return;
        }

        if (!startDate.isBefore(endDate)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Date Selection. The start date must be before the end date.").show();
            return;
        }

        Button btn = new Button("Remove");
        BookGuideTM newBookGuideTM = new BookGuideTM(
                rId,
                gId,
                tId,
                startDate,
                endDate,
                btn
        );

        btn.setOnAction(actionEvent -> {
            bookGuideTMS.remove(newBookGuideTM);
            tblBooking.refresh();
        });

        bookGuideTMS.add(newBookGuideTM);
        tblBooking.refresh();

        dateStart.setValue(null);
        dateEnd.setValue(null);
        cmbTravelerId.getSelectionModel().clearSelection();
        cmbLangId.getSelectionModel().clearSelection();
        cmbGuideId.getSelectionModel().clearSelection();
        lblTravelerName.setText("");
        lblLanguage.setText("");
        lblGuideName.setText("");
    }

    @FXML
    void btnBookGuideOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (tblBooking.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add guides to the cart!").show();
            return;
        }

        ArrayList<GuideAssignmentDTO> guideAssignmentDTOS = new ArrayList<>();
        for (BookGuideTM bookGuideTM : bookGuideTMS) {
            String requestId = bookGuideTM.getRequestId();

            // Check if request_id exists in the request table
            if (!guideAssignmentBOImpl.checkRequestIdExists(requestId)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Request ID: " + requestId).show();
                return;
            }

            GuideAssignmentDTO guideAssignmentDTO = new GuideAssignmentDTO(
                    requestId,
                    bookGuideTM.getGuideId(),
                    bookGuideTM.getTravelerId(),
                    bookGuideTM.getStartDate(),
                    bookGuideTM.getEndDate(),
                    false
            );
            guideAssignmentDTOS.add(guideAssignmentDTO);
        }

        boolean isSaved = true;
        for (GuideAssignmentDTO guideAssignmentDTO : guideAssignmentDTOS) {
            isSaved &= guideAssignmentBOImpl.save(guideAssignmentDTO);
        }

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Booking saved!").show();
            bookGuideTMS.clear();
            tblBooking.refresh();
        } else {
            new Alert(Alert.AlertType.ERROR, "Booking failed!").show();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
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
    void cmbLangIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedLanguageId = cmbLangId.getSelectionModel().getSelectedItem();
        LanguageDTO languageDTO = languageBOImpl.findById(selectedLanguageId);
        if (languageDTO != null) {
            lblLanguage.setText(languageDTO.getLanguage());
        }
        cmbGuideId.setDisable(false);
        loadGuideIds(selectedLanguageId);
    }

    @FXML
    void cmbTravelerOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedTravelerId = cmbTravelerId.getSelectionModel().getSelectedItem();
        TravelerDTO travelerDTO = travelerBOImpl.findById(selectedTravelerId);
        if (travelerDTO != null) {
            lblTravelerName.setText(travelerDTO.getName());
        }
    }

    @FXML
    void dateEndOnAction(ActionEvent event) {

    }

    @FXML
    void dateStartOnAction(ActionEvent event) {
        dateEnd.setDisable(false);
    }

    public void setRequestId(String requestId) {
        System.out.println("Request ID received: " + requestId);
        this.requestId = requestId;
        lblRequestId.setText(requestId);
    }

    public void setTravelerId(String travelerId) {
        System.out.println("Traveler ID received: " + travelerId);
        this.traveler = travelerId;
        cmbTravelerId.setValue(travelerId);
    }

    private void setCellValues() {
        try {
            System.out.println("Setting table column values...");

            colRequestId.setCellValueFactory(new PropertyValueFactory<>("requestId"));
            colTravelerId.setCellValueFactory(new PropertyValueFactory<>("travelerId"));
            colGuideId.setCellValueFactory(new PropertyValueFactory<>("guideId"));
            colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            colAction.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));
            tblBooking.setItems(bookGuideTMS);

            System.out.println("Table column values set successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error setting table column values: " + e.getMessage()).show();
        }
    }

    private void refreshPage() throws SQLException {
        System.out.println("Refreshing the page...");

        try {
            requestDate.setText(LocalDate.now().toString());

            System.out.println("Loading IDs...");
            loadTravelerIds();
            loadLanguageIds();

            cmbTravelerId.getSelectionModel().clearSelection();
            lblTravelerName.setText("");

            bookGuideTMS.clear();
            tblBooking.refresh();

            System.out.println("Page refreshed successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error refreshing the page: " + e.getMessage()).show();
        }
    }

    private void loadTravelerIds() throws SQLException {
        System.out.println("Loading traveler IDs...");

        try {
            ArrayList<String> travelerIds = travelerBOImpl.getAllIds();
            ObservableList<String> travelerIdsObservableList = FXCollections.observableArrayList(travelerIds);
            cmbTravelerId.setItems(travelerIdsObservableList);

            System.out.println("Traveler IDs loaded: " + travelerIdsObservableList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading traveler IDs: " + e.getMessage()).show();
        }
    }

    private void loadLanguageIds() throws SQLException {
        System.out.println("Loading language IDs...");

        try {
            ArrayList<String> languageIds = languageBOImpl.getAllIds();
            ObservableList<String> languageIdsObservableList = FXCollections.observableArrayList(languageIds);
            cmbLangId.setItems(languageIdsObservableList);

            System.out.println("language IDs loaded: " + languageIdsObservableList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading language IDs: " + e.getMessage()).show();
        }
    }

    private void loadGuideIds(String selectedLanguageId) throws SQLException, ClassNotFoundException {
        System.out.println("Loading guide IDs...");

        try {
            ArrayList<String> guideIds = guideBOImpl.getAllIds(selectedLanguageId);
            ObservableList<String> guideIdsObservableList = FXCollections.observableArrayList(guideIds);
            cmbGuideId.setItems(guideIdsObservableList);

            System.out.println("guide IDs loaded: " + guideIdsObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading guide IDs: " + e.getMessage()).show();
        }
    }

    private void dateSet(DatePicker datePicker) {
        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate localDate) {
                return localDate == null ? "" : dateFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String string) {
                return string == null || string.isEmpty() ? null : LocalDate.parse(string, dateFormatter);
            }
        });

        // Disable all dates today or earlier
        LocalDate today = LocalDate.now();

        datePicker.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(today)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // Optional: Highlight disabled dates
                }
            }
        });
    }
}
