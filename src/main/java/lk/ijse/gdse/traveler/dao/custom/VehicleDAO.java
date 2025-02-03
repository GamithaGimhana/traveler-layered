package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dto.VehicleDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleDAO {
    public String getNextVehicleId() throws SQLException;
    public boolean saveVehicle(VehicleDTO vehicleDTO) throws SQLException;
    public ArrayList<VehicleDTO> getAllVehicles() throws SQLException;
    public boolean updateVehicle(VehicleDTO vehicleDTO) throws SQLException;
    public boolean deleteVehicle(String vehicleId) throws SQLException;
    public ArrayList<String> getAllVehicleIds(String selectedVehicleModel) throws SQLException;
    public ArrayList<String> getAllVehicleTypes() throws SQLException;
    public ArrayList<String> getAllVehicleModels(String selectedVehicleType) throws SQLException;
    public VehicleDTO findById(String selectedVehicleId) throws SQLException;
    public boolean updateVehicleList(String vehicleId, boolean status) throws SQLException;
}
