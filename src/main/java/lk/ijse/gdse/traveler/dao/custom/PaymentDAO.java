package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentDAO {
    public String getNextPaymentId() throws SQLException;
    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException;
    public ArrayList<PaymentDTO> getAllPayments() throws SQLException;
    public boolean updatePayment(PaymentDTO paymentDTO) throws SQLException;
    public boolean deletePayment(String paymentId) throws SQLException;
    public ArrayList<String> getAllPaymentIds(String selectedTraveler) throws SQLException;
    public PaymentDTO findById(String selectedPaymentId) throws SQLException;
}
