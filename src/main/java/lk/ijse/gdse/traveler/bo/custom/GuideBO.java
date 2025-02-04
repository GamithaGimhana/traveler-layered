package lk.ijse.gdse.traveler.bo.custom;

import lk.ijse.gdse.traveler.bo.SuperBO;
import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.dto.GuideDTO;
import lk.ijse.gdse.traveler.entity.Guide;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GuideBO extends SuperBO {
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(GuideDTO guideDTO) throws SQLException, ClassNotFoundException;
    public ArrayList<GuideDTO> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(GuideDTO guideDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String guideId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllIds(String selectedLanguageId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException;
    public GuideDTO findById(String selectedGuideId) throws SQLException, ClassNotFoundException;
    public boolean updateList(String guideId, boolean status) throws SQLException, ClassNotFoundException;
}
