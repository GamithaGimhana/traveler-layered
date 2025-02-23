package lk.ijse.gdse.traveler.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import lk.ijse.gdse.traveler.bo.BOFactory;
import lk.ijse.gdse.traveler.bo.custom.impl.*;
import lk.ijse.gdse.traveler.dto.*;
import lk.ijse.gdse.traveler.view.tdm.BookTripTM;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TripController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("Initializing the page...");

            dateSet(dateStart);
            dateSet(dateEnd);

            // Disable components initially
            dateEnd.setDisable(true);
            cmbVModel.setDisable(true);
            cmbVId.setDisable(true);
            cmbGuideId.setDisable(true);

            setCellValues();

            refreshPage();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load data during initialization: " + e.getMessage()).show();
        }
    }

    @FXML
    private Label assignmentDate;

    @FXML
    private Button btnAddToBooking;

    @FXML
    private ComboBox<String> cmbDriverId;

    @FXML
    private ComboBox<String> cmbGuideId;

    @FXML
    private ComboBox<String> cmbLangId;

    @FXML
    private ComboBox<String> cmbTravelerId;

    @FXML
    private ComboBox<String> cmbVId;

    @FXML
    private ComboBox<String> cmbVModel;

    @FXML
    private ComboBox<String> cmbVType;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<BookTripTM, Double> colAmount;

    @FXML
    private TableColumn<BookTripTM, LocalDate> colEndDate;

    @FXML
    private TableColumn<BookTripTM, String> colGId;

    @FXML
    private TableColumn<BookTripTM, String> colRId;

    @FXML
    private TableColumn<BookTripTM, LocalDate> colStartDate;

    @FXML
    private TableColumn<BookTripTM, String> colTId;

    @FXML
    private TableColumn<BookTripTM, String> colVId;

    @FXML
    private TableColumn<BookTripTM, String> colTripId;

    @FXML
    private TableColumn<BookTripTM, String> colDId;

    @FXML
    private DatePicker dateEnd;

    @FXML
    private DatePicker dateStart;

    @FXML
    private Label lblDriverName;

    @FXML
    private Label lblLanguage;

    @FXML
    private Label lblGuideName;

    @FXML
    private Label lblLPlate;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblTravelerName;

    @FXML
    private Label lblTripId;

    @FXML
    private Label lblRentalId;

    @FXML
    private TableView<BookTripTM> tblBooking;

    private String traveler;

    private String requestId;

//    private final TravelerBOImpl travelerBOImpl = new TravelerBOImpl();
//    private final VehicleBOImpl vehicleBOImpl = new VehicleBOImpl();
//    private final GuideBOImpl guideBOImpl = new GuideBOImpl();
//    private final DriverBOImpl driverBOImpl = new DriverBOImpl();
//    private final LanguageBOImpl languageBOImpl = new LanguageBOImpl();
//    private final TripBOImpl tripBOImpl = new TripBOImpl();

    TravelerBOImpl travelerBOImpl = (TravelerBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.TRAVELER);
    VehicleBOImpl vehicleBOImpl = (VehicleBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.VEHICLE);
    GuideBOImpl guideBOImpl = (GuideBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.GUIDE);
    LanguageBOImpl languageBOImpl = (LanguageBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.LANGUAGE);
    DriverBOImpl driverBOImpl = (DriverBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.DRIVER);
    TripBOImpl tripBOImpl = (TripBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.TRIP);

    private final ObservableList<BookTripTM> bookTripTMS = FXCollections.observableArrayList();

    @FXML
    void btnAddToBookingOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String rId = lblRentalId.getText();
        String tripId = lblTripId.getText();
        String tId = cmbTravelerId.getValue();
        String selectedVehicleId = cmbVId.getValue();
        String selectedLanguageId = cmbLangId.getValue();
        String gId = cmbGuideId.getValue();
        String dId = cmbDriverId.getValue();
        LocalDate startDate = dateStart.getValue();
        LocalDate endDate = dateEnd.getValue();

        if (selectedVehicleId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a vehicle!").show();
            return;
        }

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

        double priceForADay = vehicleBOImpl.findById(selectedVehicleId).getDailyPrice();
        long rentedDays = ChronoUnit.DAYS.between(startDate, endDate);
        double cost = priceForADay * rentedDays;

        Button btn = new Button("Remove");
        BookTripTM newBookTripTM = new BookTripTM(
                tripId,
                rId,
                tId,
                gId,
                selectedVehicleId,
                dId,
                startDate,
                endDate,
                cost,
                btn
        );

        btn.setOnAction(actionEvent -> {
            bookTripTMS.remove(newBookTripTM);
            tblBooking.refresh();
        });

        bookTripTMS.add(newBookTripTM);
        calculateTotalAmount();
        tblBooking.refresh();

        dateStart.setValue(null);
        dateEnd.setValue(null);
        cmbTravelerId.getSelectionModel().clearSelection();
        cmbLangId.getSelectionModel().clearSelection();
        cmbGuideId.getSelectionModel().clearSelection();
        cmbVId.getSelectionModel().clearSelection();
        cmbVType.getSelectionModel().clearSelection();
        cmbVModel.getSelectionModel().clearSelection();
        cmbDriverId.getSelectionModel().clearSelection();
        lblTravelerName.setText("");
        lblLanguage.setText("");
        lblGuideName.setText("");
        lblLPlate.setText("");
    }

    @FXML
    void btnBookTripOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (tblBooking.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add booking to the cart!").show();
            return;
        }

        ArrayList<TripDTO> tripDTOS = new ArrayList<>();
        for (BookTripTM bookTripTM : bookTripTMS) {
            String requestId = bookTripTM.getRequestId();

            // Check if request_id exists in the request table
            if (!tripBOImpl.checkRequestIdExists(requestId)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Request ID: " + requestId).show();
                return;
            }

            TripDTO tripDTO = new TripDTO(
                    bookTripTM.getTripId(),
                    requestId,
                    bookTripTM.getGuideId(),
                    bookTripTM.getDriverId(),
                    bookTripTM.getVehicleId(),
                    bookTripTM.getStartDate(),
                    bookTripTM.getEndDate(),
                    bookTripTM.getCost(),
                    false
            );
            tripDTOS.add(tripDTO);
        }

        boolean isSaved = true;
        for (TripDTO tripDTO : tripDTOS) {
            isSaved &= tripBOImpl.save(tripDTO);
        }

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Booking saved!").show();
            bookTripTMS.clear();
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
    void cmbVIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedVehicleId = cmbVId.getSelectionModel().getSelectedItem();
        VehicleDTO vehicleDTO = vehicleBOImpl.findById(selectedVehicleId);
        if (vehicleDTO != null) {
            lblLPlate.setText(vehicleDTO.getLicensePlateNumber());
        }
    }

    @FXML
    void cmbVModelOnAction(ActionEvent event) throws SQLException {
        String selectedVehicleModel = cmbVModel.getSelectionModel().getSelectedItem();
        cmbVId.setDisable(false);
        loadVehicleIds(selectedVehicleModel);
    }

    @FXML
    void cmbVTypeOnAction(ActionEvent event) throws SQLException {
        String selectedVehicleType = cmbVType.getSelectionModel().getSelectedItem();
        cmbVModel.setDisable(false);
        loadVehicleModels(selectedVehicleType);
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
        lblRentalId.setText(requestId);
    }

    public void setTravelerId(String travelerId) {
        System.out.println("Traveler ID received: " + travelerId);
        this.traveler = travelerId;
        cmbTravelerId.setValue(travelerId);
    }

    private void setCellValues() {
        try {
            System.out.println("Setting table column values...");

            colTripId.setCellValueFactory(new PropertyValueFactory<>("tripId"));
            colRId.setCellValueFactory(new PropertyValueFactory<>("requestId"));
            colTId.setCellValueFactory(new PropertyValueFactory<>("travelerId"));
            colGId.setCellValueFactory(new PropertyValueFactory<>("guideId"));
            colVId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
            colDId.setCellValueFactory(new PropertyValueFactory<>("driverId"));
            colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            colAmount.setCellValueFactory(new PropertyValueFactory<>("cost"));
            colAction.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));
            tblBooking.setItems(bookTripTMS);

            System.out.println("Table column values set successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error setting table column values: " + e.getMessage()).show();
        }
    }

    public void loadNextTripId() throws SQLException, ClassNotFoundException {
        String nextTripId = tripBOImpl.getNextId();
        lblTripId.setText(nextTripId);
    }

    private void refreshPage() throws SQLException {
        System.out.println("Refreshing the page...");

        try {
            loadNextTripId();
            assignmentDate.setText(LocalDate.now().toString());

            System.out.println("Loading IDs...");
            loadTravelerIds();
            loadVehicleTypes();
            loadLanguageIds();
            loadDriverIds();

            cmbTravelerId.getSelectionModel().clearSelection();
            cmbVId.getSelectionModel().clearSelection();
            cmbVType.getSelectionModel().clearSelection();
            cmbVModel.getSelectionModel().clearSelection();
            lblTravelerName.setText("");
            lblLPlate.setText("");

            bookTripTMS.clear();
            tblBooking.refresh();

            System.out.println("Page refreshed successfully!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error refreshing the page: " + e.getMessage()).show();
        }
    }

    private void loadVehicleIds(String selectedVehicleModel) throws SQLException {
        System.out.println("Loading vehicle IDs for model: " + selectedVehicleModel);

        try {
            ArrayList<String> vehicleIds = vehicleBOImpl.getAllIds(selectedVehicleModel);
            ObservableList<String> vehicleIdObservableList = FXCollections.observableArrayList(vehicleIds);
            cmbVId.setItems(vehicleIdObservableList);

            System.out.println("Vehicle IDs loaded: " + vehicleIdObservableList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading vehicle IDs: " + e.getMessage()).show();
        }
    }

    private void loadVehicleModels(String selectedVehicleType) throws SQLException {
        System.out.println("Loading vehicle models for type: " + selectedVehicleType);

        try {
            ArrayList<String> vehicleModels = vehicleBOImpl.getAllModels(selectedVehicleType);
            ObservableList<String> vehicleModelObservableList = FXCollections.observableArrayList(vehicleModels);
            cmbVModel.setItems(vehicleModelObservableList);

            System.out.println("Vehicle models loaded: " + vehicleModelObservableList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading vehicle models: " + e.getMessage()).show();
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

    private void loadVehicleTypes() throws SQLException {
        System.out.println("Loading vehicle types...");

        try {
            ArrayList<String> vehicleTypes = vehicleBOImpl.getAllTypes();
            ObservableList<String> vehicleTypeObservableList = FXCollections.observableArrayList(vehicleTypes);
            cmbVType.setItems(vehicleTypeObservableList);

            System.out.println("Vehicle types loaded: " + vehicleTypeObservableList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading vehicle types: " + e.getMessage()).show();
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

    private void loadGuideIds(String selectedLanguageId) throws SQLException {
        System.out.println("Loading guide IDs...");

        try {
            ArrayList<String> guideIds = guideBOImpl.getAllIds(selectedLanguageId);
            ObservableList<String> guideIdsObservableList = FXCollections.observableArrayList(guideIds);
            cmbGuideId.setItems(guideIdsObservableList);

            System.out.println("guide IDs loaded: " + guideIdsObservableList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading guide IDs: " + e.getMessage()).show();
        }
    }

    private void loadDriverIds() throws SQLException {
        System.out.println("Loading driver IDs...");

        try {
            ArrayList<String> driverIds = driverBOImpl.getAllIds();
            ObservableList<String> driverIdsObservableList = FXCollections.observableArrayList(driverIds);
            cmbDriverId.setItems(driverIdsObservableList);

            System.out.println("driver IDs loaded: " + driverIdsObservableList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading driver IDs: " + e.getMessage()).show();
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

    private void calculateTotalAmount() {
        double total = 0;
        for (BookTripTM bookTripTM : bookTripTMS) {
            total += bookTripTM.getCost();
        }
        lblTotal.setText(String.valueOf(total));
    }
}
