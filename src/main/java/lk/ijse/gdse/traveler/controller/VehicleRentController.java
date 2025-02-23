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
import lk.ijse.gdse.traveler.bo.BOFactory;
import lk.ijse.gdse.traveler.bo.custom.impl.*;
import lk.ijse.gdse.traveler.dto.TravelerDTO;
import lk.ijse.gdse.traveler.dto.VehicleDTO;
import lk.ijse.gdse.traveler.dto.VehicleRentDTO;
import lk.ijse.gdse.traveler.view.tdm.BookVehicleTM;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VehicleRentController implements Initializable {

    @FXML
    private AnchorPane ancVehicleRent;

    @FXML
    private Label assignmentDate;

    @FXML
    private Button btnAddToBooking;

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
    private TableColumn<BookVehicleTM, Double> colAmount;

    @FXML
    private TableColumn<BookVehicleTM, String> colRId;

    @FXML
    private TableColumn<BookVehicleTM, LocalDate> colRentalDate;

    @FXML
    private TableColumn<BookVehicleTM, LocalDate> colReturnDate;

    @FXML
    private TableColumn<BookVehicleTM, String> colTId;

    @FXML
    private TableColumn<BookVehicleTM, String> colVId;

    @FXML
    private DatePicker dateRental;

    @FXML
    private DatePicker dateReturn;

    @FXML
    private Label lblAssignmentId;

    @FXML
    private Label lblLPlate;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblTravelerName;

    @FXML
    private TableView<BookVehicleTM> tblBooking;

    private String traveler;

    private String requestId;

//    private final TravelerBOImpl travelerBOImpl = new TravelerBOImpl();
//    private final VehicleBOImpl vehicleBOImpl = new VehicleBOImpl();
//    private final VehicleRentBOImpl vehicleRentBOImpl = new VehicleRentBOImpl();

    TravelerBOImpl travelerBOImpl = (TravelerBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.TRAVELER);
    VehicleBOImpl vehicleBOImpl = (VehicleBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.VEHICLE);
    VehicleRentBOImpl vehicleRentBOImpl = (VehicleRentBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.VEHICLERENT);

    private final ObservableList<BookVehicleTM> bookVehicleTMS = FXCollections.observableArrayList();

    @FXML
    void btnAddToBookingOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String rId = lblAssignmentId.getText();
        String tId = cmbTravelerId.getValue();
        String selectedVehicleId = cmbVId.getValue();
        LocalDate rentalDate = dateRental.getValue();
        LocalDate returnDate = dateReturn.getValue();

        if (selectedVehicleId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a vehicle!").show();
            return;
        }

        if (rentalDate == null || returnDate == null) {
            new Alert(Alert.AlertType.ERROR, "Please select both rental and return dates.").show();
            return;
        }

        if (!rentalDate.isBefore(returnDate)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Date Selection. The rental date must be before the return date.").show();
            return;
        }

        double priceForADay = vehicleBOImpl.findById(selectedVehicleId).getDailyPrice();
        long rentedDays = ChronoUnit.DAYS.between(rentalDate, returnDate);
        double cost = priceForADay * rentedDays;

        Button btn = new Button("Remove");
        BookVehicleTM newBookVehicleTM = new BookVehicleTM(
                rId,
                tId,
                selectedVehicleId,
                rentalDate,
                returnDate,
                cost,
                btn
        );

        btn.setOnAction(actionEvent -> {
            bookVehicleTMS.remove(newBookVehicleTM);
            tblBooking.refresh();
        });

        bookVehicleTMS.add(newBookVehicleTM);
        calculateTotalAmount();
        tblBooking.refresh();

        dateRental.setValue(null);
        dateReturn.setValue(null);
        cmbTravelerId.getSelectionModel().clearSelection();
        cmbVId.getSelectionModel().clearSelection();
        cmbVType.getSelectionModel().clearSelection();
        cmbVModel.getSelectionModel().clearSelection();
        lblTravelerName.setText("");
        lblLPlate.setText("");
    }

    @FXML
    void btnRentVehicleOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (tblBooking.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add vehicles to the cart!").show();
            return;
        }

        ArrayList<VehicleRentDTO> vehicleRentDTOS = new ArrayList<>();
        for (BookVehicleTM bookVehicleTM : bookVehicleTMS) {
            String requestId = bookVehicleTM.getRequestId();

            // Check if request_id exists in the request table
            if (!vehicleRentBOImpl.checkRequestIdExists(requestId)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Request ID: " + requestId).show();
                return;
            }

            VehicleRentDTO vehicleRentDTO = new VehicleRentDTO(
                    requestId,
                    bookVehicleTM.getTravelerId(),
                    bookVehicleTM.getVehicleId(),
                    bookVehicleTM.getRentalDate(),
                    bookVehicleTM.getReturnDate(),
                    bookVehicleTM.getCost(),
                    false
            );
            vehicleRentDTOS.add(vehicleRentDTO);
        }

        boolean isSaved = true;
        for (VehicleRentDTO vehicleRentDTO : vehicleRentDTOS) {
            isSaved &= vehicleRentBOImpl.save(vehicleRentDTO);
        }

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Booking saved!").show();
            lblTotal.setText("");
            bookVehicleTMS.clear();
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
    void dateRentalOnAction(ActionEvent event) {
        dateReturn.setDisable(false);
    }

    @FXML
    void dateReturnOnAction(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("Initializing the page...");

            dateSet(dateRental);
            dateSet(dateReturn);

            // Disable components initially
            dateReturn.setDisable(true);
            cmbVModel.setDisable(true);
            cmbVId.setDisable(true);

            setCellValues();
//            bookVehicleTMS.clear();

            refreshPage();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load data during initialization: " + e.getMessage()).show();
        }
    }

    private void setCellValues() {
        try {
            System.out.println("Setting table column values...");

            colRId.setCellValueFactory(new PropertyValueFactory<>("requestId"));
            colTId.setCellValueFactory(new PropertyValueFactory<>("travelerId"));
            colVId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
            colRentalDate.setCellValueFactory(new PropertyValueFactory<>("rentalDate"));
            colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
            colAmount.setCellValueFactory(new PropertyValueFactory<>("cost"));
            colAction.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));
            tblBooking.setItems(bookVehicleTMS);

            System.out.println("Table column values set successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error setting table column values: " + e.getMessage()).show();
        }
    }

    public void setRequestId(String requestId) {
        System.out.println("Request ID received: " + requestId);
        this.requestId = requestId;
        lblAssignmentId.setText(requestId);
    }

    public void setTravelerId(String travelerId) {
        System.out.println("Traveler ID received: " + travelerId);
        this.traveler = travelerId;
        cmbTravelerId.setValue(travelerId);
    }

    private void refreshPage() throws SQLException {
        System.out.println("Refreshing the page...");

        try {
//            lblAssignmentId.setText(vehicleRentModel.getNextVehicleRentId());
            assignmentDate.setText(LocalDate.now().toString());

            System.out.println("Loading IDs...");
            loadTravelerIds();
            loadVehicleTypes();

            cmbTravelerId.getSelectionModel().clearSelection();
            cmbVId.getSelectionModel().clearSelection();
            cmbVType.getSelectionModel().clearSelection();
            cmbVModel.getSelectionModel().clearSelection();
            lblTravelerName.setText("");
            lblLPlate.setText("");

            bookVehicleTMS.clear();
            tblBooking.refresh();

            System.out.println("Page refreshed successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error refreshing the page: " + e.getMessage()).show();
        }
    }

    private void loadVehicleIds(String selectedVehicleModel) throws SQLException {
        System.out.println("Loading vehicle IDs for model: " + selectedVehicleModel);

        try {
            ArrayList<String> vehicleIds = vehicleBOImpl.getAllIds(selectedVehicleModel);
            if (!vehicleIds.isEmpty()) {
                ObservableList<String> vehicleIdObservableList = FXCollections.observableArrayList(vehicleIds);
                cmbVId.setItems(vehicleIdObservableList);

                System.out.println("Vehicle IDs loaded: " + vehicleIdObservableList);
            } else {
                new Alert(Alert.AlertType.ERROR, "All vehicles are fully booked on this model!").show();
                cmbVId.setDisable(true);
            }
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

    private double calculateTotalAmount() {
        double total = 0;
        for (BookVehicleTM bookVehicleTM : bookVehicleTMS) {
            total += bookVehicleTM.getCost();
        }
        lblTotal.setText(String.valueOf(total));
        return total;
    }
}
