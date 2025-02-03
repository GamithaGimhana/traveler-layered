package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dto.AttractionDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AttractionDAO {
    public String getNextAttractionId() throws SQLException;
    public boolean saveAttraction(AttractionDTO attractionDTO) throws SQLException;
    public ArrayList<AttractionDTO> getAllAttractions() throws SQLException;
    public boolean updateAttraction(AttractionDTO attractionDTO) throws SQLException;
    public boolean deleteAttraction(String attractionId) throws SQLException;
}
