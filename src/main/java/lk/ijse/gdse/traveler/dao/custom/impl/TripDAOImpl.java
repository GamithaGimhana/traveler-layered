package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.TripDAO;
import lk.ijse.gdse.traveler.db.DBConnection;
import lk.ijse.gdse.traveler.entity.Trip;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TripDAOImpl implements TripDAO {
    private final VehicleDAOImpl vehicleDaoImpl = new VehicleDAOImpl();
    private final GuideDAOImpl guideDaoImpl = new GuideDAOImpl();
    private final DriverDAOImpl driverDaoImpl = new DriverDAOImpl();

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select trip_id from trip order by trip_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // last id
            String substring = lastId.substring(1); // extract numbers
            int i = Integer.parseInt(substring); // convert the numbers part to int
            int newIdIndex = i + 1; // increment
            return String.format("B%03d", newIdIndex); // return the new id in string
        }
        return "B001"; // return the default ID
    }

    @Override
    public boolean save(Trip trip) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            // @autoCommit: Disables auto-commit to manually control the transaction
            connection.setAutoCommit(false); // 1

            // @isOrderSaved: Saves the order details into the orders table
            boolean isTripSaved = SqlUtil.execute(
                    "insert into trip values (?,?,?,?,?,?,?,?,?)",
                    trip.getTripId(),
                    trip.getRequestId(),
                    trip.getGuideId(),
                    trip.getDriverId(),
                    trip.getVehicleId(),
                    trip.getStartDate(),
                    trip.getEndDate(),
                    trip.getCost(),
                    trip.isTripStatus()
            );
            // If the order is saved successfully
            if (isTripSaved) {
                System.out.println("Trip Saved");
                // @isOrderDetailListSaved: Saves the list of order details
                boolean isVehicleUpdated = vehicleDaoImpl.updateList(trip.getVehicleId(), trip.isTripStatus());
                boolean isGuideUpdated = guideDaoImpl.updateList(trip.getGuideId(), trip.isTripStatus());
                boolean isDriverUpdated = driverDaoImpl.updateDriverList(trip.getDriverId(), trip.isTripStatus());

                if (isVehicleUpdated && isGuideUpdated && isDriverUpdated) {
                    System.out.println("Vehicle, Guide and Driver Updated");
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
    public ArrayList<Trip> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean update(Trip dto) throws SQLException {
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
    public Trip findById(String selectedId) throws SQLException {
        return null;
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
