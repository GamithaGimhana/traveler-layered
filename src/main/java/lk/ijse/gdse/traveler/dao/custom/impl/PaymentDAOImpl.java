package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.PaymentDAO;
import lk.ijse.gdse.traveler.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
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

    @Override
    public boolean save(Payment payment) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute(
                "insert into payment values (?,?,?,?,?,?)",
                payment.getPaymentId(),
                payment.getTravelerId(),
                payment.getTripId(),
                payment.getAmount(),
                payment.getRemaining(),
                payment.getPaymentDate(),
                payment.getPaymentMethod()
        );
    }

    @Override
    public ArrayList<Payment> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select * from payment");

        ArrayList<Payment> payments = new ArrayList<>();

        while (rst.next()) {
            Payment payment = new Payment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getDate(6).toLocalDate(),
                    rst.getString(7)
            );
            payments.add(payment);
        }
        return payments;
    }

    @Override
    public boolean update(Payment payment) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute(
                "update payment set travelr_id=?, trip_id=?, amount=?, remain_amount=?, payment_date=?, payment_method=? where payment_id=?",
                payment.getTravelerId(),
                payment.getTripId(),
                payment.getAmount(),
                payment.getRemaining(),
                payment.getPaymentDate(),
                payment.getPaymentMethod(),
                payment.getPaymentId()
        );
    }

    @Override
    public boolean delete(String paymentId) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute("delete from payment where payment_id=?", paymentId);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return null;
    }

    @Override
    public ArrayList<String> getAllIds(String selectedTraveler) throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select payment_id from payment where traveler_id=?", selectedTraveler);

        ArrayList<String> paymentIds = new ArrayList<>();

        while (rst.next()) {
            paymentIds.add(rst.getString(1));
        }

        return paymentIds;
    }

    @Override
    public Payment findById(String selectedPaymentId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select * from payment where payment_id=?", selectedPaymentId);

        if (rst.next()) {
            return new Payment(
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
