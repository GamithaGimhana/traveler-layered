package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dto.VehicleRentDTO;

import java.sql.SQLException;

public interface VehicleRentDAO {
    public boolean saveVehicleRent(VehicleRentDTO vehicleRentDTO) throws SQLException;
    public boolean checkRequestIdExists(String requestId) throws SQLException;
}
