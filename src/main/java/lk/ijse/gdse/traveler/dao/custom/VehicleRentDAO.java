package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.dto.VehicleRentDTO;
import lk.ijse.gdse.traveler.entity.VehicleRent;

import java.sql.SQLException;

public interface VehicleRentDAO extends CrudDAO<VehicleRent> {
//    public boolean saveVehicleRent(VehicleRentDTO vehicleRentDTO) throws SQLException;
    public boolean checkRequestIdExists(String requestId) throws SQLException, ClassNotFoundException;
}
