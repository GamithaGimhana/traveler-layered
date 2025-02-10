package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.CashierDAO;
import lk.ijse.gdse.traveler.entity.Cashier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CashierDAOImpl implements CashierDAO {

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select cashier_id from cashier order by cashier_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // last id
            String substring = lastId.substring(1); // extract numbers
            int i = Integer.parseInt(substring); // convert the numbers part to int
            int newIdIndex = i + 1; // increment
            return String.format("C%03d", newIdIndex); // return the new id in string
        }
        return "C001"; // return the default ID
    }

    @Override
    public boolean save(Cashier cashier) throws SQLException, ClassNotFoundException {
        // Validate admin_id
        try {
            System.out.println("Validating Admin ID: " + cashier.getAdminId());
            ResultSet adminCheck = SqlUtil.execute("SELECT admin_id FROM admin WHERE admin_id = ?", cashier.getAdminId());
            if (!adminCheck.next()) {
                throw new SQLException("Invalid Admin ID: " + cashier.getAdminId());
            }
        } catch (SQLException e) {
            System.err.println("Error during Admin ID validation: " + e.getMessage());
            throw e; // Rethrow the exception after logging
        }

        return SqlUtil.execute(
                "insert into cashier values (?,?,?,?,?,?,?)",
                cashier.getCashierId(),
                cashier.getName(),
                cashier.getEmail(),
                cashier.getContactNumber(),
                cashier.getUsername(),
                cashier.getPassword(),
                cashier.getAdminId()
        );
    }

    @Override
    public ArrayList<Cashier> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select * from cashier");

        ArrayList<Cashier> cashiers = new ArrayList<>();

        while (rst.next()) {
            Cashier cashier = new Cashier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
                    );
            cashiers.add(cashier);
        }
        return cashiers;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select cashier_id from cashier");

        ArrayList<String> cashierIds = new ArrayList<>();

        while (rst.next()) {
            cashierIds.add(rst.getString(1));
        }

        return cashierIds;
    }

    @Override
    public boolean update(Cashier cashier) throws SQLException, ClassNotFoundException {
        // Validate admin_id
        try {
            System.out.println("Validating Admin ID: " + cashier.getAdminId());
            ResultSet adminCheck = SqlUtil.execute("SELECT admin_id FROM admin WHERE admin_id = ?", cashier.getAdminId());
            if (!adminCheck.next()) {
                throw new SQLException("Invalid Admin ID: " + cashier.getAdminId());
            }
        } catch (SQLException e) {
            System.err.println("Error during Admin ID validation: " + e.getMessage());
            throw e; // Rethrow the exception after logging
        }

        return SqlUtil.execute(
                "update cashier set name=?, email=?, contact_number=?, username=?, password=?, admin_id=? where cashier_id=?",
                cashier.getName(),
                cashier.getEmail(),
                cashier.getContactNumber(),
                cashier.getUsername(),
                cashier.getPassword(),
                cashier.getAdminId(),
                cashier.getCashierId()
        );
    }

    @Override
    public boolean delete(String cashierId) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute("delete from cashier where cashier_id=?", cashierId);
    }

    @Override
    public Cashier findById(String selectedCashierId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select * from cashier where cashier_id=?", selectedCashierId);

//        if (rst.next()) {
//            return new Cashier(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getString(4),
//                    rst.getString(5),
//                    rst.getString(6),
//                    rst.getString(7)
//            );
//        }
//        return null;
        rst.next();

        Cashier cashier = new Cashier(selectedCashierId, rst.getString("name"), rst.getString("email"), rst.getString("contact_number"), rst.getString("username"), rst.getString("password"), rst.getString("admin_id"));
        return cashier;
    }
}
