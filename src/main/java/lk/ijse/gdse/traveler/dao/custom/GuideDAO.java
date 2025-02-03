package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dto.GuideDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GuideDAO {
    public String getNextGuideId() throws SQLException;
    public boolean saveGuide(GuideDTO guideDTO) throws SQLException;
    public ArrayList<GuideDTO> getAllGuides() throws SQLException;
    public boolean updateGuide(GuideDTO guideDTO) throws SQLException;
    public boolean deleteGuide(String guideId) throws SQLException;
    public ArrayList<String> getAllGuideIds(String selectedLanguageId) throws SQLException;
    public ArrayList<String> getAllGuideIds() throws SQLException;
    public GuideDTO findById(String selectedGuideId) throws SQLException;
    public boolean updateGuideList(String guideId, boolean status) throws SQLException;
}
