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
import lk.ijse.gdse.traveler.dto.CashierDTO;
import lk.ijse.gdse.traveler.dto.RequestDTO;
import lk.ijse.gdse.traveler.dto.TravelerDTO;
import lk.ijse.gdse.traveler.view.tdm.RequestTM;
import lk.ijse.gdse.traveler.model.CashierModel;
import lk.ijse.gdse.traveler.model.RequestModel;
import lk.ijse.gdse.traveler.model.TravelerModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class RequestController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set table column to cell factory value
        colRequestId.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        colTravelerId.setCellValueFactory(new PropertyValueFactory<>("travelerId"));
        colRequestDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
        colRequestType.setCellValueFactory(new PropertyValueFactory<>("requestType"));
        colCashierId.setCellValueFactory(new PropertyValueFactory<>("cashierId"));

        // inside initialize method
        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load request id").show();
        }
    }

    @FXML
    private Label requestDate;

    @FXML
    private AnchorPane ancRequest;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnMakeARequest;

    @FXML
    private ComboBox<String> cmbCashierId;

    @FXML
    private ComboBox<String> cmbReqType;

    @FXML
    private ComboBox<String> cmbTravelerId;

    @FXML
    private TableColumn<RequestTM, String> colCashierId;

    @FXML
    private TableColumn<RequestTM, String> colRequestDate;

    @FXML
    private TableColumn<RequestTM, String> colRequestId;

    @FXML
    private TableColumn<RequestTM, String> colRequestType;

    @FXML
    private TableColumn<RequestTM, String> colTravelerId;

    @FXML
    private Label lblCashierName;

    @FXML
    private Label lblRequestId;

    @FXML
    private Label lblTravelerName;

    @FXML
    private TableView<RequestTM> tblRequest;

    private final TravelerModel travelerModel = new TravelerModel();
    private final CashierModel cashierModel = new CashierModel();

    ObservableList<String> requestTypes = FXCollections.observableArrayList("Trip", "Vehicle Rent", "Guide Assignment");

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String requestId = lblRequestId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = requestModel.deleteRequest(requestId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Request deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Request...!").show();
            }
        }
    }

    @FXML
    void btnMakeARequestOnAction(ActionEvent event) throws SQLException, IOException {
        String reqId = lblRequestId.getText();
        String travelerId = cmbTravelerId.getValue();
        Date reqDate = Date.valueOf(requestDate.getText());
        String reqType = cmbReqType.getValue();
        String cashierId = cmbCashierId.getValue();

        if (travelerId != null && reqType != null && cashierId != null) {
            RequestDTO requestDTO = new RequestDTO(
                    reqId,
                    travelerId,
                    reqDate,
                    reqType,
                    cashierId
            );

            boolean isSaved = requestModel.saveRequest(requestDTO);
            if (isSaved) {
                String selectedType = cmbReqType.getSelectionModel().getSelectedItem();

                if ("Trip".equals(selectedType)) {
                    ancRequest.getChildren().clear();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tripFx.fxml"));
                    AnchorPane load = loader.load();

                    // Passing user type
                    TripController controller = loader.getController();
                    controller.setRequestId(reqId);
                    controller.setTravelerId(travelerId);

                    load.prefWidthProperty().bind(ancRequest.widthProperty());
                    load.prefHeightProperty().bind(ancRequest.heightProperty());

                    ancRequest.getChildren().add(load);
                } else if ("Vehicle Rent".equals(selectedType)) {
                    ancRequest.getChildren().clear();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/vehicleRentFx.fxml"));
                    AnchorPane load = loader.load();

                    // Passing user type
                    VehicleRentController controller = loader.getController();
                    controller.setRequestId(reqId);
                    controller.setTravelerId(travelerId);

                    load.prefWidthProperty().bind(ancRequest.widthProperty());
                    load.prefHeightProperty().bind(ancRequest.heightProperty());

                    ancRequest.getChildren().add(load);
                } else if ("Guide Assignment".equals(selectedType)) {
                    ancRequest.getChildren().clear();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/guideAssignmentFx.fxml"));
                    AnchorPane load = loader.load();

                    // Passing user type
                    GuideAssignmentController controller = loader.getController();
                    controller.setRequestId(reqId);
                    controller.setTravelerId(travelerId);

                    load.prefWidthProperty().bind(ancRequest.widthProperty());
                    load.prefHeightProperty().bind(ancRequest.heightProperty());

                    ancRequest.getChildren().add(load);
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to load Request...!").show();
            }
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void cmbCashierOnAction(ActionEvent event) throws SQLException {
        String selectedCashierId = cmbCashierId.getSelectionModel().getSelectedItem();
        CashierDTO cashierDTO = cashierModel.findById(selectedCashierId);

        // If traveler found (travelerDTO not null)
        if (cashierDTO != null) {

            // FIll traveler related labels
            lblCashierName.setText(cashierDTO.getName());
        }
    }

    @FXML
    void cmbTravelerOnAction(ActionEvent event) throws SQLException {
        String selectedTravelerId = cmbTravelerId.getSelectionModel().getSelectedItem();
        TravelerDTO travelerDTO = travelerModel.findById(selectedTravelerId);

        // If traveler found (travelerDTO not null)
        if (travelerDTO != null) {

            // FIll traveler related labels
            lblTravelerName.setText(travelerDTO.getName());
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        RequestTM requestTM = tblRequest.getSelectionModel().getSelectedItem();
        if (requestTM != null) {
            lblRequestId.setText(requestTM.getRequestId());
            cmbTravelerId.setValue(requestTM.getTravelerId());
            requestDate.setText(String.valueOf(requestTM.getRequestDate()));
            cmbReqType.setValue(requestTM.getRequestType());
            cmbCashierId.setValue(requestTM.getCashierId());

            btnMakeARequest.setDisable(true);

            btnDelete.setDisable(false);
        }
    }

    RequestModel requestModel = new RequestModel();

    private void refreshPage() throws SQLException {
        loadNextRequestId();
        loadTableData();

        requestDate.setText(LocalDate.now().toString());

        loadTravelerIds();
        loadCashierIds();

        btnMakeARequest.setDisable(false);

        btnDelete.setDisable(true);

        cmbTravelerId.getSelectionModel().clearSelection();
        cmbCashierId.getSelectionModel().clearSelection();
        cmbReqType.getSelectionModel().clearSelection();

        cmbReqType.setItems(requestTypes);
        lblTravelerName.setText("");
        lblCashierName.setText("");
    }

    private void loadTableData() throws SQLException {
        ArrayList<RequestDTO> requestDTOS = requestModel.getAllRequests();

        ObservableList<RequestTM> requestTMS = FXCollections.observableArrayList();

        for (RequestDTO requestDTO : requestDTOS) {
            RequestTM requestTM = new RequestTM(
                    requestDTO.getRequestId(),
                    requestDTO.getTravelerId(),
                    requestDTO.getRequestDate(),
                    requestDTO.getRequestType(),
                    requestDTO.getCashierId()
            );
            requestTMS.add(requestTM);
        }

        tblRequest.setItems(requestTMS);
    }

    public void loadNextRequestId() throws SQLException {
        String nextGuideId = requestModel.getNextRequestId();
        lblRequestId.setText(nextGuideId);
    }

    private void loadTravelerIds() throws SQLException {
        ArrayList<String> travelerIds = travelerModel.getAllTravelerIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(travelerIds);
        cmbTravelerId.setItems(observableList);
    }

    private void loadCashierIds() throws SQLException {
        ArrayList<String> cashierIds = cashierModel.getAllCashierIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(cashierIds);
        cmbCashierId.setItems(observableList);
    }
}
