package lk.ijse.gdse.traveler.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.traveler.bo.BOFactory;
import lk.ijse.gdse.traveler.bo.custom.impl.*;
import lk.ijse.gdse.traveler.dto.PaymentDTO;
import lk.ijse.gdse.traveler.dto.TravelerDTO;
import lk.ijse.gdse.traveler.view.tdm.PaymentTM;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("Initializing the page...");

            setCellValues();
            refreshPage();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load data during initialization: " + e.getMessage()).show();
        }
    }

    @FXML
    private Label paymentDate;

    @FXML
    private AnchorPane ancPayment;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSettle;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbPType;

    @FXML
    private ComboBox<String> cmbRId;

    @FXML
    private ComboBox<String> cmbTravelerId;

    @FXML
    private TableColumn<PaymentTM, Double> colAmount;

    @FXML
    private TableColumn<PaymentTM, String> colPId;

    @FXML
    private TableColumn<PaymentTM, LocalDate> colPaymentDate;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentMethod;

    @FXML
    private TableColumn<PaymentTM, String> colRId;

    @FXML
    private TableColumn<PaymentTM, Double> colRemaining;

    @FXML
    private TableColumn<PaymentTM, String> colTId;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblRemaining;

    @FXML
    private Label lblTravelerName;

    @FXML
    private TableView<PaymentTM> tblPayment;

    @FXML
    private TextField txtPaying;

//    private final TravelerBOImpl travelerBOImpl = new TravelerBOImpl();
//    private final RequestBOImpl requestBOImpl = new RequestBOImpl();
//    private final PaymentBOImpl paymentBOImpl = new PaymentBOImpl();
//    private final VehicleBOImpl vehicleBOImpl = new VehicleBOImpl();
//    private final VehicleRentBOImpl vehicleRentBOImpl = new VehicleRentBOImpl();

    TravelerBOImpl travelerBOImpl = (TravelerBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.TRAVELER);
    RequestBOImpl requestBOImpl = (RequestBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.REQUEST);
    PaymentBOImpl paymentBOImpl = (PaymentBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
    VehicleBOImpl vehicleBOImpl = (VehicleBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.VEHICLE);
    VehicleRentBOImpl vehicleRentBOImpl = (VehicleRentBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.VEHICLERENT);

    private final String[] paymentTypes = {"Cash", "Card"};

    private final ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String paymentId = lblPaymentId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = paymentBOImpl.delete(paymentId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Payment deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete payment...!").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String paymentId = lblPaymentId.getText();
        String tId = cmbTravelerId.getValue();
        String rId = cmbRId.getValue();
        String pType = cmbPType.getValue();
        double amount = Double.parseDouble(lblAmount.getText());
        double remaining = Double.parseDouble(lblRemaining.getText());
        LocalDate pDate = LocalDate.parse(paymentDate.getText());
        double paying = Double.parseDouble(txtPaying.getText());

        txtPaying.setStyle(txtPaying.getStyle() + ";-fx-border-color: #7367F0;");

        String payingPattern = "^((0?\\.((0[1-9])|\\d{2}))|([1-9]\\d*(\\.\\d{2})?))$";

//        boolean isValidPay = paying.matches(payingPattern);
//
//        if (!isValidPay) {
//            System.out.println(txtPaying.getStyle());
//            txtPaying.setStyle(txtPaying.getStyle() + ";-fx-border-color: red;");
//            System.out.println("Invalid type.............");
//        }

        if (true) {
            PaymentDTO paymentDTO = new PaymentDTO(
                    paymentId,
                    tId,
                    rId,
                    amount,
                    remaining,
                    pDate,
                    pType
            );

            boolean isSaved = paymentBOImpl.save(paymentDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Payment saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save payment...!").show();
            }
        }
    }

    @FXML
    void btnSettleOnAction(ActionEvent event) {
        double paying = Double.parseDouble(txtPaying.getText());
        double amount = Double.parseDouble(lblAmount.getText());
        double remaining = amount - paying;

        lblRemaining.setText(String.format("%.2f", remaining));
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String paymentId = lblPaymentId.getText();
        String tId = cmbTravelerId.getValue();
        String rId = cmbRId.getValue();
        String pType = cmbPType.getValue();
        double amount = Double.parseDouble(lblAmount.getText());
        double remaining = Double.parseDouble(lblRemaining.getText());
        LocalDate pDate = LocalDate.parse(paymentDate.getText());
        double paying = Double.parseDouble(txtPaying.getText());

        txtPaying.setStyle(txtPaying.getStyle() + ";-fx-border-color: #7367F0;");

        String payingPattern = "^((0?\\.((0[1-9])|\\d{2}))|([1-9]\\d*(\\.\\d{2})?))$";

//        boolean isValidPay = paying.matches(payingPattern);
//
//        if (!isValidPay) {
//            System.out.println(txtPaying.getStyle());
//            txtPaying.setStyle(txtPaying.getStyle() + ";-fx-border-color: red;");
//            System.out.println("Invalid type.............");
//        }

        if (true) {
            PaymentDTO paymentDTO = new PaymentDTO(
                    paymentId,
                    tId,
                    rId,
                    amount,
                    remaining,
                    pDate,
                    pType
            );

            boolean isSaved = paymentBOImpl.update(paymentDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Payment updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update payment...!").show();
            }
        }
    }

//    @FXML
//    void btngeneratePaymentReportOnAction(ActionEvent event) {
//
//    }
//
//    @FXML
//    void cmbPTypeOnAction(ActionEvent event) {
//
//    }
//
//    @FXML
//    void cmbRIdOnAction(ActionEvent event) throws SQLException {
//
//    }

    @FXML
    void btngeneratePaymentReportOnAction(ActionEvent event) {
        try {
            System.out.println("Generating payment report...");

            // Example logic to generate a report (You may use JasperReports, PDF, or Excel)
            ArrayList<PaymentDTO> paymentList = paymentBOImpl.getAll();

            if (!paymentList.isEmpty()) {
                // Generate the report logic here
                new Alert(Alert.AlertType.INFORMATION, "Payment report generated successfully!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "No payment records found to generate the report.").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error generating payment report: " + e.getMessage()).show();
        }
    }

    @FXML
    void cmbPTypeOnAction(ActionEvent event) {
        String selectedPaymentType = cmbPType.getSelectionModel().getSelectedItem();

        if (selectedPaymentType != null) {
            System.out.println("Selected Payment Type: " + selectedPaymentType);
        }
    }

    @FXML
    void cmbRIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedRequestId = cmbRId.getSelectionModel().getSelectedItem();
        LocalDate pDate = LocalDate.parse(paymentDate.getText());

        if (selectedRequestId != null) {
            System.out.println("Selected Request ID: " + selectedRequestId);

            double amount = vehicleRentBOImpl.findById(selectedRequestId).getRentalCost();

            lblAmount.setText(String.format("%.2f", amount));
        }
    }


    @FXML
    void cmbTravelerOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedTravelerId = cmbTravelerId.getSelectionModel().getSelectedItem();
        TravelerDTO travelerDTO = travelerBOImpl.findById(selectedTravelerId);
        if (travelerDTO != null) {
            lblTravelerName.setText(travelerDTO.getName());
        }
        loadRequestIds(selectedTravelerId);
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    private void setCellValues() {
        try {
            System.out.println("Setting table column values...");

            colPId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
            colTId.setCellValueFactory(new PropertyValueFactory<>("travelerId"));
            colRId.setCellValueFactory(new PropertyValueFactory<>("requestId"));
            colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            colRemaining.setCellValueFactory(new PropertyValueFactory<>("remaining"));
            colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
            colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
            tblPayment.setItems(paymentTMS);

            System.out.println("Table column values set successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error setting table column values: " + e.getMessage()).show();
        }
    }

    private void refreshPage() throws SQLException {
        System.out.println("Refreshing the page...");

        try {
            paymentDate.setText(LocalDate.now().toString());

            System.out.println("Loading IDs...");
            loadTravelerIds();
            loadPaymentTypes();

            cmbTravelerId.getSelectionModel().clearSelection();
            cmbRId.getSelectionModel().clearSelection();
            cmbPType.getSelectionModel().clearSelection();
            lblTravelerName.setText("");
            lblAmount.setText("");
            lblRemaining.setText("");
            txtPaying.setText("");

            paymentTMS.clear();
            tblPayment.refresh();

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

    private void loadRequestIds(String selectedTravelerId) throws SQLException {
        System.out.println("Loading Request IDs...");

        try {
            ArrayList<String> travelerIds = requestBOImpl.getAllIds(selectedTravelerId);
            ObservableList<String> travelerIdsObservableList = FXCollections.observableArrayList(travelerIds);
            cmbRId.setItems(travelerIdsObservableList);

            System.out.println("Request IDs loaded: " + travelerIdsObservableList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading Request IDs: " + e.getMessage()).show();
        }
    }

    private void loadPaymentTypes() throws SQLException {
        System.out.println("Loading Types...");

        cmbPType.getItems().addAll(paymentTypes);

        System.out.println("Types loaded: " + paymentTypes);
    }

}
