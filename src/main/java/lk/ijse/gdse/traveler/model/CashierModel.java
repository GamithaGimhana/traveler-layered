package lk.ijse.gdse.traveler.model;

import lk.ijse.gdse.traveler.dto.CashierDTO;
import lk.ijse.gdse.traveler.dao.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CashierModel {
    public String getNextCashierId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select cashier_id from cashier order by cashier_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // last id
            String substring = lastId.substring(1); // extract numbers
            int i = Integer.parseInt(substring); // convert the numbers part to int
            int newIdIndex = i + 1; // increment
            return String.format("C%03d", newIdIndex); // return the new id in string
        }
        return "C001"; // return the default ID
    }

    public boolean saveCashier(CashierDTO cashierDTO) throws SQLException {
        // Validate admin_id
        try {
            System.out.println("Validating Admin ID: " + cashierDTO.getAdminId());
            ResultSet adminCheck = CrudUtil.execute("SELECT admin_id FROM admin WHERE admin_id = ?", cashierDTO.getAdminId());
            if (!adminCheck.next()) {
                throw new SQLException("Invalid Admin ID: " + cashierDTO.getAdminId());
            }
        } catch (SQLException e) {
            System.err.println("Error during Admin ID validation: " + e.getMessage());
            throw e; // Rethrow the exception after logging
        }

        return CrudUtil.execute(
                "insert into cashier values (?,?,?,?,?,?,?)",
                cashierDTO.getCashierId(),
                cashierDTO.getName(),
                cashierDTO.getEmail(),
                cashierDTO.getContactNumber(),
                cashierDTO.getUsername(),
                cashierDTO.getPassword(),
                cashierDTO.getAdminId()
        );
    }

    public ArrayList<CashierDTO> getAllCashier() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from cashier");

        ArrayList<CashierDTO> cashierDTOS = new ArrayList<>();

        while (rst.next()) {
            CashierDTO cashierDTO = new CashierDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
                    );
            cashierDTOS.add(cashierDTO);
        }
        return cashierDTOS;
    }

    public ArrayList<String> getAllCashierIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select cashier_id from cashier");

        ArrayList<String> cashierIds = new ArrayList<>();

        while (rst.next()) {
            cashierIds.add(rst.getString(1));
        }

        return cashierIds;
    }

    public boolean updateCashier(CashierDTO cashierDTO) throws SQLException {
        // Validate admin_id
        try {
            System.out.println("Validating Admin ID: " + cashierDTO.getAdminId());
            ResultSet adminCheck = CrudUtil.execute("SELECT admin_id FROM admin WHERE admin_id = ?", cashierDTO.getAdminId());
            if (!adminCheck.next()) {
                throw new SQLException("Invalid Admin ID: " + cashierDTO.getAdminId());
            }
        } catch (SQLException e) {
            System.err.println("Error during Admin ID validation: " + e.getMessage());
            throw e; // Rethrow the exception after logging
        }

        return CrudUtil.execute(
                "update cashier set name=?, email=?, contact_number=?, username=?, password=?, admin_id=? where cashier_id=?",
                cashierDTO.getName(),
                cashierDTO.getEmail(),
                cashierDTO.getContactNumber(),
                cashierDTO.getUsername(),
                cashierDTO.getPassword(),
                cashierDTO.getAdminId(),
                cashierDTO.getCashierId()
        );
    }

    public boolean deleteCashier(String cashierId) throws SQLException {
        return CrudUtil.execute("delete from cashier where cashier_id=?", cashierId);
    }

    public CashierDTO findById(String selectedCashierId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from cashier where cashier_id=?", selectedCashierId);

        if (rst.next()) {
            return new CashierDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );
        }
        return null;
    }
}
