package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dto.AccomodationDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AccomodationDAO {
    public String getNextAccomodationId() throws SQLException;
    public boolean saveAccommodation(AccomodationDTO accomodationDTO) throws SQLException;
    public ArrayList<AccomodationDTO> getAllAccommodations() throws SQLException;
    public boolean updateAccommodation(AccomodationDTO accomodationDTO) throws SQLException;
    public boolean deleteAccommodation(String accommodationId) throws SQLException;
}
