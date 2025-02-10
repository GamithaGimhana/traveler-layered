package lk.ijse.gdse.traveler.bo.custom.impl;

import lk.ijse.gdse.traveler.bo.custom.GuideBO;
import lk.ijse.gdse.traveler.dao.DAOFactory;
import lk.ijse.gdse.traveler.dao.custom.FoodDAO;
import lk.ijse.gdse.traveler.dao.custom.GuideDAO;
import lk.ijse.gdse.traveler.dao.custom.impl.GuideDAOImpl;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;
import lk.ijse.gdse.traveler.dto.DriverDTO;
import lk.ijse.gdse.traveler.dto.FoodDTO;
import lk.ijse.gdse.traveler.dto.GuideDTO;
import lk.ijse.gdse.traveler.entity.Driver;
import lk.ijse.gdse.traveler.entity.Food;
import lk.ijse.gdse.traveler.entity.Guide;

import java.sql.SQLException;
import java.util.ArrayList;

public class GuideBOImpl implements GuideBO {
//    GuideDAO guideDAO = new GuideDAOImpl();
    GuideDAO guideDAO = (GuideDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.GUIDE);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return guideDAO.getNextId();
    }

    @Override
    public boolean save(GuideDTO guideDTO) throws SQLException, ClassNotFoundException {
        return guideDAO.save(new Guide(guideDTO.getGuideId(), guideDTO.getName(), guideDTO.getLicenseNumber(), guideDTO.getContactNumber(), guideDTO.isAvailabilityStatus()));
    }

    @Override
    public ArrayList<GuideDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<GuideDTO> guideDTOS = new ArrayList<>();
        ArrayList<Guide> guides = guideDAO.getAll();
        for (Guide guide: guides) {
            guideDTOS.add(new GuideDTO(guide.getGuideId(), guide.getName(), guide.getLicenseNumber(), guide.getContactNumber(), guide.isAvailabilityStatus()));
        }
        return guideDTOS;
    }

    @Override
    public boolean update(GuideDTO guideDTO) throws SQLException, ClassNotFoundException {
        return guideDAO.update(new Guide(guideDTO.getGuideId(), guideDTO.getName(), guideDTO.getLicenseNumber(), guideDTO.getContactNumber(), guideDTO.isAvailabilityStatus()));
    }

    @Override
    public boolean delete(String guideId) throws SQLException, ClassNotFoundException {
        return guideDAO.delete(guideId);
    }

    @Override
    public ArrayList<String> getAllIds(String selectedLanguageId) throws SQLException, ClassNotFoundException {
        return guideDAO.getAllIds(selectedLanguageId);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return guideDAO.getAllIds();
    }

    @Override
    public GuideDTO findById(String selectedGuideId) throws SQLException, ClassNotFoundException {
        Guide guide = guideDAO.findById(selectedGuideId);
        return new GuideDTO(guide.getGuideId(), guide.getName(), guide.getLicenseNumber(), guide.getContactNumber(), guide.isAvailabilityStatus());
    }

    @Override
    public boolean updateList(String guideId, boolean status) throws SQLException, ClassNotFoundException {
        return guideDAO.updateList(guideId, status);
    }
}
