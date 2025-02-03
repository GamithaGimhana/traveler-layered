package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.VehicleRentDAO;
import lk.ijse.gdse.traveler.db.DBConnection;
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
    public VehicleRent findById(String selectedId) throws SQLException {
        return null;
    }

    public boolean checkRequestIdExists(String requestId) throws SQLException {
        String query = "SELECT COUNT(*) FROM request WHERE request_id = ?";
        ResultSet rs = SqlUtil.execute(query, requestId);
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }

}
