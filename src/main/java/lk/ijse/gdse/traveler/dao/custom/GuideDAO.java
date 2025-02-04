package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.entity.Guide;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GuideDAO extends CrudDAO<Guide> {
//    public String getNextGuideId() throws SQLException;
//    public boolean saveGuide(GuideDTO guideDTO) throws SQLException;
//    public ArrayList<GuideDTO> getAllGuides() throws SQLException;
//    public boolean updateGuide(GuideDTO guideDTO) throws SQLException;
//    public boolean deleteGuide(String guideId) throws SQLException;
    public ArrayList<String> getAllIds(String selectedLanguageId) throws SQLException, ClassNotFoundException;
//    public ArrayList<String> getAllGuideIds() throws SQLException;
//    public GuideDTO findById(String selectedGuideId) throws SQLException;
    public boolean updateList(String guideId, boolean status) throws SQLException, ClassNotFoundException;
}
