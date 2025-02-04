package lk.ijse.gdse.traveler.bo.custom.impl;

import lk.ijse.gdse.traveler.bo.custom.VehicleBO;
import lk.ijse.gdse.traveler.dao.custom.VehicleDAO;
import lk.ijse.gdse.traveler.dao.custom.impl.VehicleDAOImpl;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;
import lk.ijse.gdse.traveler.dto.DriverDTO;
import lk.ijse.gdse.traveler.dto.TravelerDTO;
import lk.ijse.gdse.traveler.dto.VehicleDTO;
import lk.ijse.gdse.traveler.entity.Driver;
import lk.ijse.gdse.traveler.entity.Traveler;
import lk.ijse.gdse.traveler.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleBOImpl implements VehicleBO {
    VehicleDAO vehicleDAO = new VehicleDAOImpl();

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return vehicleDAO.getNextId();
    }

    @Override
    public boolean save(VehicleDTO vehicleDTO) throws SQLException, ClassNotFoundException {
        return vehicleDAO.save(new Vehicle(vehicleDTO.getVehicleId(), vehicleDTO.getVehicleType(), vehicleDTO.getModel(), vehicleDTO.getLicensePlateNumber(), vehicleDTO.getDailyPrice(), vehicleDTO.isAvailabilityStatus()));
    }

    @Override
    public ArrayList<VehicleDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<VehicleDTO> vehicleDTOS = new ArrayList<>();
        ArrayList<Vehicle> vehicles = vehicleDAO.getAll();
        for (Vehicle vehicle: vehicles) {
            vehicleDTOS.add(new VehicleDTO(vehicle.getVehicleId(), vehicle.getVehicleType(), vehicle.getModel(), vehicle.getLicensePlateNumber(), vehicle.getDailyPrice(), vehicle.isAvailabilityStatus()));
        }
        return vehicleDTOS;
    }

    @Override
    public boolean update(VehicleDTO vehicleDTO) throws SQLException, ClassNotFoundException {
        return vehicleDAO.update(new Vehicle(vehicleDTO.getVehicleId(), vehicleDTO.getVehicleType(), vehicleDTO.getModel(), vehicleDTO.getLicensePlateNumber(), vehicleDTO.getDailyPrice(), vehicleDTO.isAvailabilityStatus()));
    }

    @Override
    public boolean delete(String vehicleId) throws SQLException, ClassNotFoundException {
        return vehicleDAO.delete(vehicleId);
    }

    @Override
    public ArrayList<String> getAllIds(String selectedVehicleModel) throws SQLException, ClassNotFoundException {
        return vehicleDAO.getAllIds(selectedVehicleModel);
    }

    @Override
    public ArrayList<String> getAllTypes() throws SQLException, ClassNotFoundException {
        return vehicleDAO.getAllTypes();
    }

    @Override
    public ArrayList<String> getAllModels(String selectedVehicleType) throws SQLException, ClassNotFoundException {
        return vehicleDAO.getAllModels(selectedVehicleType);
    }

    @Override
    public VehicleDTO findById(String selectedVehicleId) throws SQLException, ClassNotFoundException {
        Vehicle vehicle = vehicleDAO.findById(selectedVehicleId);
        return new VehicleDTO(vehicle.getVehicleId(), vehicle.getVehicleType(), vehicle.getModel(), vehicle.getLicensePlateNumber(), vehicle.getDailyPrice(), vehicle.isAvailabilityStatus());
    }

    @Override
    public boolean updateList(String vehicleId, boolean status) throws SQLException, ClassNotFoundException {
        return vehicleDAO.updateList(vehicleId, status);
    }
}
