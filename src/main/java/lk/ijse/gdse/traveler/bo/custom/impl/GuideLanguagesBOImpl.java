package lk.ijse.gdse.traveler.bo.custom.impl;

import lk.ijse.gdse.traveler.bo.custom.GuideLanguagesBO;
import lk.ijse.gdse.traveler.dao.custom.GuideLanguagesDAO;
import lk.ijse.gdse.traveler.dao.custom.impl.GuideLanguagesDAOImpl;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;
import lk.ijse.gdse.traveler.dto.GuideDTO;
import lk.ijse.gdse.traveler.dto.GuideLanguagesDTO;
import lk.ijse.gdse.traveler.entity.Guide;
import lk.ijse.gdse.traveler.entity.GuideLanguages;

import java.sql.SQLException;
import java.util.ArrayList;

public class GuideLanguagesBOImpl implements GuideLanguagesBO {
    GuideLanguagesDAO guideLanguagesDAO = new GuideLanguagesDAOImpl();

    @Override
    public boolean save(GuideLanguagesDTO guideLanguagesDTO) throws SQLException, ClassNotFoundException {
        return guideLanguagesDAO.save(new GuideLanguages(guideLanguagesDTO.getGuideId(), guideLanguagesDTO.getGuideId()));
    }

    @Override
    public ArrayList<GuideLanguagesDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<GuideLanguagesDTO> guideLanguagesDTOS = new ArrayList<>();
        ArrayList<GuideLanguages> guideLanguages = guideLanguagesDAO.getAll();
        for (GuideLanguages guideLanguage: guideLanguages) {
            guideLanguagesDTOS.add(new GuideLanguagesDTO(guideLanguage.getGuideId(), guideLanguage.getLangId()));
        }
        return guideLanguagesDTOS;
    }

    @Override
    public boolean update(GuideLanguagesDTO guideLanguagesDTO) throws SQLException, ClassNotFoundException {
        return guideLanguagesDAO.update(new GuideLanguages(guideLanguagesDTO.getGuideId(), guideLanguagesDTO.getGuideId()));
    }

    @Override
    public boolean delete(String guideLangId) throws SQLException, ClassNotFoundException {
        return guideLanguagesDAO.delete(guideLangId);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return guideLanguagesDAO.getAllIds();
    }

    @Override
    public GuideLanguagesDTO findById(String selectedGuideLanguageId) throws SQLException, ClassNotFoundException {
        GuideLanguages guideLanguages = guideLanguagesDAO.findById(selectedGuideLanguageId);
        return new GuideLanguagesDTO(guideLanguages.getGuideId(), guideLanguages.getLangId());
    }
}
