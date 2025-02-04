package lk.ijse.gdse.traveler.bo.custom;

import lk.ijse.gdse.traveler.bo.SuperBO;
import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.dto.VehicleDTO;
import lk.ijse.gdse.traveler.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleBO extends SuperBO {
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(VehicleDTO vehicleDTO) throws SQLException, ClassNotFoundException;
    public ArrayList<VehicleDTO> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(VehicleDTO vehicleDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String vehicleId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllIds(String selectedVehicleModel) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllTypes() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllModels(String selectedVehicleType) throws SQLException, ClassNotFoundException;
    public VehicleDTO findById(String selectedVehicleId) throws SQLException, ClassNotFoundException;
    public boolean updateList(String vehicleId, boolean status) throws SQLException, ClassNotFoundException;
}
