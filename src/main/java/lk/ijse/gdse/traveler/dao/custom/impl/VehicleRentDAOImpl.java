package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.VehicleRentDAO;
import lk.ijse.gdse.traveler.db.DBConnection;
import lk.ijse.gdse.traveler.entity.Guide;
import lk.ijse.gdse.traveler.entity.Vehicle;
import lk.ijse.gdse.traveler.entity.VehicleRent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleRentDAOImpl implements VehicleRentDAO {
    private final VehicleDAOImpl vehicleDaoImpl = new VehicleDAOImpl();

    @Override
    public String getNextId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(VehicleRent vehicleRent) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            // @autoCommit: Disables auto-commit to manually control the transaction
            connection.setAutoCommit(false); // 1

            // @isOrderSaved: Saves the order details into the orders table
            boolean isVehicleRentSaved = SqlUtil.execute(
                    "insert into rental_transaction values (?,?,?,?,?,?,?)",
                    vehicleRent.getRequestId(),
                    vehicleRent.getTravelerId(),
                    vehicleRent.getVehicleId(),
                    vehicleRent.getRentalDate(),
                    vehicleRent.getReturnDate(),
                    vehicleRent.getRentalCost(),
                    vehicleRent.isVRentalStatus()
            );
            // If the order is saved successfully
            if (isVehicleRentSaved) {
                System.out.println("Vehicle Rent Saved");
                // @isOrderDetailListSaved: Saves the list of order details
                boolean isVehicleUpdated = vehicleDaoImpl.updateList(vehicleRent.getVehicleId(), vehicleRent.isVRentalStatus());
                if (isVehicleUpdated) {
                    System.out.println("Vehicle Updated");
                    // @commit: Commits the transaction if both order and details are saved successfully
                    connection.commit(); // 2
                    return true;
                }
            }
            // @rollback: Rolls back the transaction if order details saving fails
            connection.rollback(); // 3
            return false;
        } catch (Exception e) {
            // @catch: Rolls back the transaction in case of any exception
            connection.rollback();
            return false;
        } finally {
            // @finally: Resets auto-commit to true after the operation
            connection.setAutoCommit(true); // 4
        }
    }

    @Override
    public ArrayList<VehicleRent> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean update(VehicleRent dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return null;
    }

    @Override
    public VehicleRent findById(String selectedId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select * from rental_transaction where request_id=?", selectedId);

//        if (rst.next()) {
//            return new VehicleRent(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getDate(4).toLocalDate(),
//                    rst.getDate(5).toLocalDate(),
//                    rst.getDouble(6),
//                    rst.getBoolean(7)
//            );
//        }
//        return null;
        rst.next();

        VehicleRent vehicleRent = new VehicleRent(selectedId, rst.getString("traveler_id"), rst.getString("vehicle_id"), rst.getDate("rental_date").toLocalDate(), rst.getDate("return_date").toLocalDate(), rst.getDouble("rental_cost"), rst.getBoolean("vrental_status"));
        return vehicleRent;
    }

    public boolean checkRequestIdExists(String requestId) throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM request WHERE request_id = ?";
        ResultSet rs = SqlUtil.execute(query, requestId);
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }

}
