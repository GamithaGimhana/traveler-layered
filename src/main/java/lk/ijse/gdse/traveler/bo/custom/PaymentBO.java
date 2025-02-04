package lk.ijse.gdse.traveler.bo.custom;

import lk.ijse.gdse.traveler.bo.SuperBO;
import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.dto.PaymentDTO;
import lk.ijse.gdse.traveler.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO {
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;
    public ArrayList<PaymentDTO> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String paymentId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllIds(String selectedTraveler) throws SQLException, ClassNotFoundException;
    public PaymentDTO findById(String selectedPaymentId) throws SQLException, ClassNotFoundException;
}
