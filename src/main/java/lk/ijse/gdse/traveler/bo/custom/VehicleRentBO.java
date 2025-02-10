package lk.ijse.gdse.traveler.bo.custom;

import lk.ijse.gdse.traveler.bo.SuperBO;
import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.dto.VehicleDTO;
import lk.ijse.gdse.traveler.dto.VehicleRentDTO;
import lk.ijse.gdse.traveler.entity.VehicleRent;

import java.sql.SQLException;

public interface VehicleRentBO extends SuperBO {
    public boolean save(VehicleRentDTO vehicleRentDTO) throws SQLException, ClassNotFoundException;
    public boolean checkRequestIdExists(String requestId) throws SQLException, ClassNotFoundException;
    public VehicleRentDTO findById(String selectedVehicleRentId) throws SQLException, ClassNotFoundException;
}
