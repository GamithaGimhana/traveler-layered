package lk.ijse.gdse.traveler.bo.custom.impl;

import lk.ijse.gdse.traveler.bo.custom.PaymentBO;
import lk.ijse.gdse.traveler.dao.DAOFactory;
import lk.ijse.gdse.traveler.dao.custom.GuideDAO;
import lk.ijse.gdse.traveler.dao.custom.PaymentDAO;
import lk.ijse.gdse.traveler.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;
import lk.ijse.gdse.traveler.dto.LanguageDTO;
import lk.ijse.gdse.traveler.dto.PaymentDTO;
import lk.ijse.gdse.traveler.entity.Language;
import lk.ijse.gdse.traveler.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {
//    PaymentDAO paymentDAO = new PaymentDAOImpl();
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return paymentDAO.getNextId();
    }

    @Override
    public boolean save(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(new Payment(paymentDTO.getPaymentId(), paymentDTO.getTravelerId(), paymentDTO.getTripId(), paymentDTO.getAmount(), paymentDTO.getRemaining(), paymentDTO.getPaymentDate(), paymentDTO.getPaymentMethod()));
    }

    @Override
    public ArrayList<PaymentDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();
        ArrayList<Payment> payments = paymentDAO.getAll();
        for (Payment payment: payments) {
            paymentDTOS.add(new PaymentDTO(payment.getPaymentId(), payment.getTravelerId(), payment.getTripId(), payment.getAmount(), payment.getRemaining(), payment.getPaymentDate(), payment.getPaymentMethod()));
        }
        return paymentDTOS;
    }

    @Override
    public boolean update(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return paymentDAO.update(new Payment(paymentDTO.getPaymentId(), paymentDTO.getTravelerId(), paymentDTO.getTripId(), paymentDTO.getAmount(), paymentDTO.getRemaining(), paymentDTO.getPaymentDate(), paymentDTO.getPaymentMethod()));
    }

    @Override
    public boolean delete(String paymentId) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(paymentId);
    }

    @Override
    public ArrayList<String> getAllIds(String selectedTraveler) throws SQLException, ClassNotFoundException {
        return paymentDAO.getAllIds(selectedTraveler);
    }

    @Override
    public PaymentDTO findById(String selectedPaymentId) throws SQLException, ClassNotFoundException {
        Payment payment = paymentDAO.findById(selectedPaymentId);
        return new PaymentDTO(payment.getPaymentId(), payment.getTravelerId(), payment.getTripId(), payment.getAmount(), payment.getRemaining(), payment.getPaymentDate(), payment.getPaymentMethod());
    }
}
