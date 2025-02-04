package lk.ijse.gdse.traveler.bo.custom;

import lk.ijse.gdse.traveler.bo.SuperBO;
import lk.ijse.gdse.traveler.dto.TravelerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TravelerBO extends SuperBO {
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(TravelerDTO travelerDTO) throws SQLException, ClassNotFoundException;
    public ArrayList<TravelerDTO> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(TravelerDTO travelerDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String travelerId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException;
    public TravelerDTO findById(String selectedTravelerId) throws SQLException, ClassNotFoundException;
}
