package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dto.LanguageDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LanguageDAO {
    public String getNextLangId() throws SQLException;
    public boolean saveLang(LanguageDTO languageDTO) throws SQLException;
    public ArrayList<LanguageDTO> getAllLanguages() throws SQLException;
    public boolean updateLang(LanguageDTO languagesDTO) throws SQLException;
    public boolean deletLang(String langId) throws SQLException;
    public ArrayList<String> getAllLangIds() throws SQLException;
    public LanguageDTO findById(String selectedLanguageId) throws SQLException;
}
