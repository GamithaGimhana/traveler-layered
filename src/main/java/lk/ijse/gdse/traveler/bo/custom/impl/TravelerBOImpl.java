package lk.ijse.gdse.traveler.bo.custom.impl;

import lk.ijse.gdse.traveler.bo.custom.TravelerBO;
import lk.ijse.gdse.traveler.dao.DAOFactory;
import lk.ijse.gdse.traveler.dao.custom.RequestDAO;
import lk.ijse.gdse.traveler.dao.custom.TravelerDAO;
import lk.ijse.gdse.traveler.dao.custom.impl.TravelerDAOImpl;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;
import lk.ijse.gdse.traveler.dto.LanguageDTO;
import lk.ijse.gdse.traveler.dto.TravelerDTO;
import lk.ijse.gdse.traveler.entity.Language;
import lk.ijse.gdse.traveler.entity.Traveler;

import java.sql.SQLException;
import java.util.ArrayList;

public class TravelerBOImpl implements TravelerBO {
//    TravelerDAO travelerDAO = new TravelerDAOImpl();
    TravelerDAO travelerDAO = (TravelerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TRAVELER);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return travelerDAO.getNextId();
    }

    @Override
    public boolean save(TravelerDTO travelerDTO) throws SQLException, ClassNotFoundException {
        return travelerDAO.save(new Traveler(travelerDTO.getTravelerId(), travelerDTO.getName(), travelerDTO.getEmail(), travelerDTO.getContactNumber(), travelerDTO.getNationality(), travelerDTO.getIdNumber()));
    }

    @Override
    public ArrayList<TravelerDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<TravelerDTO> travelerDTOS = new ArrayList<>();
        ArrayList<Traveler> travelers = travelerDAO.getAll();
        for (Traveler traveler: travelers) {
            travelerDTOS.add(new TravelerDTO(traveler.getTravelerId(), traveler.getName(), traveler.getEmail(), traveler.getContactNumber(), traveler.getNationality(), traveler.getIdNumber()));
        }
        return travelerDTOS;

    }

    @Override
    public boolean update(TravelerDTO travelerDTO) throws SQLException, ClassNotFoundException {
        return travelerDAO.update(new Traveler(travelerDTO.getTravelerId(), travelerDTO.getName(), travelerDTO.getEmail(), travelerDTO.getContactNumber(), travelerDTO.getNationality(), travelerDTO.getIdNumber()));
    }

    @Override
    public boolean delete(String travelerId) throws SQLException, ClassNotFoundException {
        return travelerDAO.delete(travelerId);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return travelerDAO.getAllIds();
    }

    @Override
    public TravelerDTO findById(String selectedTravelerId) throws SQLException, ClassNotFoundException {
        System.out.println("selectedTravelerId: " + selectedTravelerId);
        Traveler traveler = travelerDAO.findById(selectedTravelerId);
        return new TravelerDTO(traveler.getTravelerId(), traveler.getName(), traveler.getEmail(), traveler.getContactNumber(), traveler.getNationality(), traveler.getIdNumber());
    }
}
