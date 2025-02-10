package lk.ijse.gdse.traveler.bo.custom.impl;

import lk.ijse.gdse.traveler.bo.custom.DriverBO;
import lk.ijse.gdse.traveler.dao.DAOFactory;
import lk.ijse.gdse.traveler.dao.custom.CashierDAO;
import lk.ijse.gdse.traveler.dao.custom.DriverDAO;
import lk.ijse.gdse.traveler.dao.custom.impl.DriverDAOImpl;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;
import lk.ijse.gdse.traveler.dto.CashierDTO;
import lk.ijse.gdse.traveler.dto.DriverDTO;
import lk.ijse.gdse.traveler.entity.Cashier;
import lk.ijse.gdse.traveler.entity.Driver;

import java.sql.SQLException;
import java.util.ArrayList;

public class DriverBOImpl implements DriverBO {
//    DriverDAO driverDAO = new DriverDAOImpl();
    DriverDAO driverDAO = (DriverDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DRIVER);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return driverDAO.getNextId();
    }

    @Override
    public boolean save(DriverDTO driverDTO) throws SQLException, ClassNotFoundException {
        return driverDAO.save(new Driver(driverDTO.getDriverId(), driverDTO.getName(), driverDTO.getLicenseNumber(), driverDTO.getContactNumber(), driverDTO.isAvailabilityStatus()));
    }

    @Override
    public ArrayList<DriverDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<DriverDTO> driverDTOS = new ArrayList<>();
        ArrayList<Driver> drivers = driverDAO.getAll();
        for (Driver driver: drivers) {
            driverDTOS.add(new DriverDTO(driver.getDriverId(), driver.getName(), driver.getLicenseNumber(), driver.getContactNumber(), driver.isAvailabilityStatus()));
        }
        return driverDTOS;
    }

    @Override
    public boolean update(DriverDTO driverDTO) throws SQLException, ClassNotFoundException {
        return driverDAO.update(new Driver(driverDTO.getDriverId(), driverDTO.getName(), driverDTO.getLicenseNumber(), driverDTO.getContactNumber(), driverDTO.isAvailabilityStatus()));
    }

    @Override
    public boolean delete(String driverId) throws SQLException, ClassNotFoundException {
        return driverDAO.delete(driverId);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return driverDAO.getAllIds();
    }

    @Override
    public DriverDTO findById(String selectedDriverId) throws SQLException, ClassNotFoundException {
        Driver driver = driverDAO.findById(selectedDriverId);
        return new DriverDTO(driver.getDriverId(), driver.getName(), driver.getLicenseNumber(), driver.getContactNumber(), driver.isAvailabilityStatus());
    }

    @Override
    public boolean updateDriverList(String driverId, boolean status) throws SQLException, ClassNotFoundException {
        return driverDAO.updateDriverList(driverId, status);
    }
}
