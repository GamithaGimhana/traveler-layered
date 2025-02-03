package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.PaymentDAO;
import lk.ijse.gdse.traveler.dto.PaymentDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {
    public String getNextPaymentId() throws SQLException {
        ResultSet rst = SqlUtil.execute("select payment_id from payment order by payment_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // last id
            String substring = lastId.substring(1); // extract numbers
            int i = Integer.parseInt(substring); // convert the numbers part to int
            int newIdIndex = i + 1; // increment
            return String.format("P%03d", newIdIndex); // return the new id in string
        }
        return "P001"; // return the default ID
    }

    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException {
        return SqlUtil.execute(
                "insert into payment values (?,?,?,?,?,?)",
                paymentDTO.getPaymentId(),
                paymentDTO.getTravelerId(),
                paymentDTO.getTripId(),
                paymentDTO.getAmount(),
                paymentDTO.getRemaining(),
                paymentDTO.getPaymentDate(),
                paymentDTO.getPaymentMethod()
        );
    }

    public ArrayList<PaymentDTO> getAllPayments() throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from payment");

        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();

        while (rst.next()) {
            PaymentDTO paymentDTO = new PaymentDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getDate(6).toLocalDate(),
                    rst.getString(7)
            );
            paymentDTOS.add(paymentDTO);
        }
        return paymentDTOS;
    }

    public boolean updatePayment(PaymentDTO paymentDTO) throws SQLException {
        return SqlUtil.execute(
                "update payment set travelr_id=?, trip_id=?, amount=?, remain_amount=?, payment_date=?, payment_method=? where payment_id=?",
                paymentDTO.getTravelerId(),
                paymentDTO.getTripId(),
                paymentDTO.getAmount(),
                paymentDTO.getRemaining(),
                paymentDTO.getPaymentDate(),
                paymentDTO.getPaymentMethod(),
                paymentDTO.getPaymentId()
        );
    }

    public boolean deletePayment(String paymentId) throws SQLException {
        return SqlUtil.execute("delete from payment where payment_id=?", paymentId);
    }

    public ArrayList<String> getAllPaymentIds(String selectedTraveler) throws SQLException {
        ResultSet rst = SqlUtil.execute("select payment_id from payment where traveler_id=?", selectedTraveler);

        ArrayList<String> paymentIds = new ArrayList<>();

        while (rst.next()) {
            paymentIds.add(rst.getString(1));
        }

        return paymentIds;
    }

    public PaymentDTO findById(String selectedPaymentId) throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from payment where payment_id=?", selectedPaymentId);

        if (rst.next()) {
            return new PaymentDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getDate(6).toLocalDate(),
                    rst.getString(7)
            );
        }
        return null;
    }
}
