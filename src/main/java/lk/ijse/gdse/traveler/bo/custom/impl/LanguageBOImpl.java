package lk.ijse.gdse.traveler.bo.custom.impl;

import lk.ijse.gdse.traveler.bo.custom.LanguageBO;
import lk.ijse.gdse.traveler.dao.custom.LanguageDAO;
import lk.ijse.gdse.traveler.dao.custom.impl.LanguageDAOImpl;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;
import lk.ijse.gdse.traveler.dto.GuideDTO;
import lk.ijse.gdse.traveler.dto.HealthcareDTO;
import lk.ijse.gdse.traveler.dto.LanguageDTO;
import lk.ijse.gdse.traveler.entity.Guide;
import lk.ijse.gdse.traveler.entity.Healthcare;
import lk.ijse.gdse.traveler.entity.Language;

import java.sql.SQLException;
import java.util.ArrayList;

public class LanguageBOImpl implements LanguageBO {
    LanguageDAO languageDAO = new LanguageDAOImpl();

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return languageDAO.getNextId();
    }

    @Override
    public boolean save(LanguageDTO languageDTO) throws SQLException, ClassNotFoundException {
        return languageDAO.save(new Language(languageDTO.getLangId(), languageDTO.getLanguage()));
    }

    @Override
    public ArrayList<LanguageDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<LanguageDTO> languageDTOS = new ArrayList<>();
        ArrayList<Language> languages = languageDAO.getAll();
        for (Language language: languages) {
            languageDTOS.add(new LanguageDTO(language.getLangId(), language.getLanguage()));
        }
        return languageDTOS;
    }

    @Override
    public boolean update(LanguageDTO languageDTO) throws SQLException, ClassNotFoundException {
        return languageDAO.update(new Language(languageDTO.getLangId(), languageDTO.getLanguage()));
    }

    @Override
    public boolean delete(String langId) throws SQLException, ClassNotFoundException {
        return languageDAO.delete(langId);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return languageDAO.getAllIds();
    }

    @Override
    public LanguageDTO findById(String selectedLanguageId) throws SQLException, ClassNotFoundException {
        Language language = languageDAO.findById(selectedLanguageId);
        return new LanguageDTO(language.getLangId(), language.getLanguage());
    }
}
