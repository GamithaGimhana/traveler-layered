package lk.ijse.gdse.traveler.bo.custom;

import lk.ijse.gdse.traveler.bo.SuperBO;
import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.dto.LanguageDTO;
import lk.ijse.gdse.traveler.entity.Language;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LanguageBO extends SuperBO {
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(LanguageDTO languageDTO) throws SQLException, ClassNotFoundException;
    public ArrayList<LanguageDTO> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(LanguageDTO languagesDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String langId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException;
    public LanguageDTO findById(String selectedLanguageId) throws SQLException, ClassNotFoundException;
}
