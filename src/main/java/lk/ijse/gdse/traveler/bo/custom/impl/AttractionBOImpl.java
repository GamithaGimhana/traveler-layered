package lk.ijse.gdse.traveler.bo.custom.impl;

import lk.ijse.gdse.traveler.bo.custom.AttractionBO;
import lk.ijse.gdse.traveler.dao.DAOFactory;
import lk.ijse.gdse.traveler.dao.custom.AdminDAO;
import lk.ijse.gdse.traveler.dao.custom.AttractionDAO;
import lk.ijse.gdse.traveler.dao.custom.impl.AttractionDAOImpl;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;
import lk.ijse.gdse.traveler.dto.AdminDTO;
import lk.ijse.gdse.traveler.dto.AttractionDTO;
import lk.ijse.gdse.traveler.entity.Admin;
import lk.ijse.gdse.traveler.entity.Attraction;

import java.sql.SQLException;
import java.util.ArrayList;

public class AttractionBOImpl implements AttractionBO {
//    AttractionDAO attractionDAO = new AttractionDAOImpl();
    AttractionDAO attractionDAO = (AttractionDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ATTRACTION);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return attractionDAO.getNextId();
    }

    @Override
    public boolean save(AttractionDTO attractionDTO) throws SQLException, ClassNotFoundException {
        return attractionDAO.save(new Attraction(attractionDTO.getAttractionId(), attractionDTO.getName(), attractionDTO.getType(), attractionDTO.getOperatingHours(), attractionDTO.getDescription()));
    }

    @Override
    public ArrayList<AttractionDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<AttractionDTO> attractionDTOS = new ArrayList<>();
        ArrayList<Attraction> attractions = attractionDAO.getAll();
        for (Attraction attraction : attractions) {
            attractionDTOS.add(new AttractionDTO(attraction.getAttractionId(), attraction.getName(), attraction.getType(), attraction.getOperatingHours(), attraction.getDescription()));
        }
        return attractionDTOS;
    }

    @Override
    public boolean update(AttractionDTO attractionDTO) throws SQLException, ClassNotFoundException {
        return attractionDAO.update(new Attraction(attractionDTO.getAttractionId(), attractionDTO.getName(), attractionDTO.getType(), attractionDTO.getOperatingHours(), attractionDTO.getDescription()));
    }

    @Override
    public boolean delete(String attractionId) throws SQLException, ClassNotFoundException {
        return attractionDAO.delete(attractionId);
    }

}
