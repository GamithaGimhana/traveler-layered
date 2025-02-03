package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dto.DriverDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DriverDAO {
    public String getNextDriverId() throws SQLException;
    public boolean saveDriver(DriverDTO driverDTO) throws SQLException;
    public ArrayList<DriverDTO> getAllDrivers() throws SQLException;
    public boolean updateDriver(DriverDTO driverDTO) throws SQLException;
    public boolean deleteDriver(String driverId) throws SQLException;
    public ArrayList<String> getAllDriverIds() throws SQLException;
    public DriverDTO findById(String selectedDriverId) throws SQLException;
    public boolean updateDriverList(String driverId, boolean status) throws SQLException;
}
