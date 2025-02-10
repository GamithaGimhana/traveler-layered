package lk.ijse.gdse.traveler.bo.custom.impl;

import lk.ijse.gdse.traveler.bo.custom.TripBO;
import lk.ijse.gdse.traveler.dao.DAOFactory;
import lk.ijse.gdse.traveler.dao.custom.TravelerDAO;
import lk.ijse.gdse.traveler.dao.custom.TripDAO;
import lk.ijse.gdse.traveler.dao.custom.impl.TripDAOImpl;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;
import lk.ijse.gdse.traveler.dto.TravelerDTO;
import lk.ijse.gdse.traveler.dto.TripDTO;
import lk.ijse.gdse.traveler.entity.Traveler;
import lk.ijse.gdse.traveler.entity.Trip;

import java.sql.SQLException;
import java.util.ArrayList;

public class TripBOImpl implements TripBO {
//    TripDAO tripDAO = new TripDAOImpl();
    TripDAO tripDAO = (TripDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TRIP);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return tripDAO.getNextId();
    }

    @Override
    public boolean save(TripDTO tripDTO) throws SQLException, ClassNotFoundException {
        return tripDAO.save(new Trip(tripDTO.getTripId(), tripDTO.getRequestId(), tripDTO.getGuideId(), tripDTO.getDriverId(), tripDTO.getVehicleId(), tripDTO.getStartDate(), tripDTO.getEndDate(), tripDTO.getCost(), tripDTO.isTripStatus()));
    }

    public boolean checkRequestIdExists(String requestId) throws SQLException, ClassNotFoundException {
        return tripDAO.checkRequestIdExists(requestId);
    }
}
