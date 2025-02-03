package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.entity.Trip;

import java.sql.SQLException;

public interface TripDAO extends CrudDAO<Trip> {
//    public String getNextTripId() throws SQLException;
//    public boolean saveTrip(TripDTO tripDTO) throws SQLException;
    public boolean checkRequestIdExists(String requestId) throws SQLException;
}
