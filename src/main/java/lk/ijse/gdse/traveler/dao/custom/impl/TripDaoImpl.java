package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.db.DBConnection;
import lk.ijse.gdse.traveler.dto.TripDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TripDaoImpl {
    private final VehicleDaoImpl vehicleDaoImpl = new VehicleDaoImpl();
    private final GuideDaoImpl guideDaoImpl = new GuideDaoImpl();
    private final DriverDaoImpl driverDaoImpl = new DriverDaoImpl();

    public String getNextTripId() throws SQLException {
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

    public boolean saveTrip(TripDTO tripDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            // @autoCommit: Disables auto-commit to manually control the transaction
            connection.setAutoCommit(false); // 1

            // @isOrderSaved: Saves the order details into the orders table
            boolean isTripSaved = SqlUtil.execute(
                    "insert into trip values (?,?,?,?,?,?,?,?,?)",
                    tripDTO.getTripId(),
                    tripDTO.getRequestId(),
                    tripDTO.getGuideId(),
                    tripDTO.getDriverId(),
                    tripDTO.getVehicleId(),
                    tripDTO.getStartDate(),
                    tripDTO.getEndDate(),
                    tripDTO.getCost(),
                    tripDTO.isTripStatus()
            );
            // If the order is saved successfully
            if (isTripSaved) {
                System.out.println("Trip Saved");
                // @isOrderDetailListSaved: Saves the list of order details
                boolean isVehicleUpdated = vehicleDaoImpl.updateVehicleList(tripDTO.getVehicleId(), tripDTO.isTripStatus());
                boolean isGuideUpdated = guideDaoImpl.updateGuideList(tripDTO.getGuideId(), tripDTO.isTripStatus());
                boolean isDriverUpdated = driverDaoImpl.updateDriverList(tripDTO.getDriverId(), tripDTO.isTripStatus());

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

    public boolean checkRequestIdExists(String requestId) throws SQLException {
        String query = "SELECT COUNT(*) FROM request WHERE request_id = ?";
        ResultSet rs = SqlUtil.execute(query, requestId);
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }
}
