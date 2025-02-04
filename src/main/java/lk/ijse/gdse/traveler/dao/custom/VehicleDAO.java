package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleDAO extends CrudDAO<Vehicle> {
//    public String getNextVehicleId() throws SQLException;
//    public boolean saveVehicle(VehicleDTO vehicleDTO) throws SQLException;
//    public ArrayList<VehicleDTO> getAllVehicles() throws SQLException;
//    public boolean updateVehicle(VehicleDTO vehicleDTO) throws SQLException;
//    public boolean deleteVehicle(String vehicleId) throws SQLException;
    public ArrayList<String> getAllIds(String selectedVehicleModel) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllTypes() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllModels(String selectedVehicleType) throws SQLException, ClassNotFoundException;
//    public VehicleDTO findById(String selectedVehicleId) throws SQLException;
    public boolean updateList(String vehicleId, boolean status) throws SQLException, ClassNotFoundException;
}
