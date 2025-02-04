package lk.ijse.gdse.traveler.bo.custom.impl;

import lk.ijse.gdse.traveler.bo.custom.VehicleRentBO;
import lk.ijse.gdse.traveler.dao.custom.VehicleRentDAO;
import lk.ijse.gdse.traveler.dao.custom.impl.VehicleRentDAOImpl;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;
import lk.ijse.gdse.traveler.dto.VehicleRentDTO;
import lk.ijse.gdse.traveler.entity.VehicleRent;

import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleRentBOImpl implements VehicleRentBO {
    VehicleRentDAO vehicleRentDAO = new VehicleRentDAOImpl();

    @Override
    public boolean save(VehicleRentDTO vehicleRentDTO) throws SQLException, ClassNotFoundException {
        return vehicleRentDAO.save(new VehicleRent(vehicleRentDTO.getRequestId(), vehicleRentDTO.getTravelerId(), vehicleRentDTO.getVehicleId(), vehicleRentDTO.getRentalDate(), vehicleRentDTO.getReturnDate(), vehicleRentDTO.getRentalCost(), vehicleRentDTO.isVRentalStatus()));
    }

    public boolean checkRequestIdExists(String requestId) throws SQLException, ClassNotFoundException {
        return vehicleRentDAO.checkRequestIdExists(requestId);
    }

}
