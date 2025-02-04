package lk.ijse.gdse.traveler.bo.custom;

import lk.ijse.gdse.traveler.bo.SuperBO;
import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.dto.DriverDTO;
import lk.ijse.gdse.traveler.entity.Driver;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DriverBO extends SuperBO {
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(DriverDTO driverDTO) throws SQLException, ClassNotFoundException;
    public ArrayList<DriverDTO> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(DriverDTO driverDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String driverId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException;
    public DriverDTO findById(String selectedDriverId) throws SQLException, ClassNotFoundException;
    public boolean updateDriverList(String driverId, boolean status) throws SQLException, ClassNotFoundException;
}
