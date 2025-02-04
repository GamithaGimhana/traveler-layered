package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.dto.PaymentDTO;
import lk.ijse.gdse.traveler.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentDAO extends CrudDAO<Payment> {
//    public String getNextPaymentId() throws SQLException;
//    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException;
//    public ArrayList<PaymentDTO> getAllPayments() throws SQLException;
//    public boolean updatePayment(PaymentDTO paymentDTO) throws SQLException;
//    public boolean deletePayment(String paymentId) throws SQLException;
    public ArrayList<String> getAllIds(String selectedTraveler) throws SQLException, ClassNotFoundException;
//    public PaymentDTO findById(String selectedPaymentId) throws SQLException;
}
