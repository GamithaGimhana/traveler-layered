package lk.ijse.gdse.traveler.bo.custom.impl;

import lk.ijse.gdse.traveler.bo.custom.VehicleRentBO;
import lk.ijse.gdse.traveler.dao.DAOFactory;
import lk.ijse.gdse.traveler.dao.custom.TripDAO;
import lk.ijse.gdse.traveler.dao.custom.VehicleRentDAO;
import lk.ijse.gdse.traveler.dao.custom.impl.VehicleRentDAOImpl;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;
import lk.ijse.gdse.traveler.dto.VehicleRentDTO;
import lk.ijse.gdse.traveler.entity.Vehicle;
import lk.ijse.gdse.traveler.entity.VehicleRent;

import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleRentBOImpl implements VehicleRentBO {
//    VehicleRentDAO vehicleRentDAO = new VehicleRentDAOImpl();
    VehicleRentDAO vehicleRentDAO = (VehicleRentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.VEHICLERENT);

    @Override
    public boolean save(VehicleRentDTO vehicleRentDTO) throws SQLException, ClassNotFoundException {
        return vehicleRentDAO.save(new VehicleRent(vehicleRentDTO.getRequestId(), vehicleRentDTO.getTravelerId(), vehicleRentDTO.getVehicleId(), vehicleRentDTO.getRentalDate(), vehicleRentDTO.getReturnDate(), vehicleRentDTO.getRentalCost(), vehicleRentDTO.isVRentalStatus()));
    }

    public boolean checkRequestIdExists(String requestId) throws SQLException, ClassNotFoundException {
        return vehicleRentDAO.checkRequestIdExists(requestId);
    }

    @Override
    public VehicleRentDTO findById(String selectedId) throws SQLException, ClassNotFoundException {
        VehicleRent vehicleRent = vehicleRentDAO.findById(selectedId);
        return new VehicleRentDTO(vehicleRent.getRequestId(), vehicleRent.getTravelerId(), vehicleRent.getVehicleId(), vehicleRent.getRentalDate(), vehicleRent.getReturnDate(), vehicleRent.getRentalCost(), vehicleRent.isVRentalStatus());
    }

}
