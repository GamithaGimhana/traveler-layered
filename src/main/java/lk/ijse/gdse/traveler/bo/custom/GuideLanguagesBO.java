package lk.ijse.gdse.traveler.bo.custom;

import lk.ijse.gdse.traveler.bo.SuperBO;
import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.dto.GuideLanguagesDTO;
import lk.ijse.gdse.traveler.entity.GuideLanguages;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GuideLanguagesBO extends SuperBO {
    public boolean save(GuideLanguagesDTO guideLanguagesDTO) throws SQLException, ClassNotFoundException;
    public ArrayList<GuideLanguagesDTO> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(GuideLanguagesDTO guideLanguagesDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String guideLangId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException;
    public GuideLanguagesDTO findById(String selectedGuideLanguageId) throws SQLException, ClassNotFoundException;
}
