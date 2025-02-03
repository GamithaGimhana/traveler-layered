package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dto.GuideLanguagesDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GuideLanguagesDAO {
    public boolean saveGLang(GuideLanguagesDTO guideLanguagesDTO) throws SQLException;
    public ArrayList<GuideLanguagesDTO> getAllGLanguages() throws SQLException;
    public boolean updateGLang(GuideLanguagesDTO guideLanguagesDTO) throws SQLException;
    public boolean deleteGLang(String guideLangId) throws SQLException;
    public ArrayList<String> getAllGLangIds() throws SQLException;
    public GuideLanguagesDTO findById(String selectedGuideLanguageId) throws SQLException;
}
