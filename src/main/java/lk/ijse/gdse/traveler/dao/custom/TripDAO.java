package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dto.TripDTO;

import java.sql.SQLException;

public interface TripDAO {
    public String getNextTripId() throws SQLException;
    public boolean saveTrip(TripDTO tripDTO) throws SQLException;
    public boolean checkRequestIdExists(String requestId) throws SQLException;
}
