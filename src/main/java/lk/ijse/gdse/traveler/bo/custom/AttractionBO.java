package lk.ijse.gdse.traveler.bo.custom;

import lk.ijse.gdse.traveler.bo.SuperBO;
import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.dto.AttractionDTO;
import lk.ijse.gdse.traveler.entity.Attraction;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AttractionBO extends SuperBO {
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(AttractionDTO attractionDTO) throws SQLException, ClassNotFoundException;
    public ArrayList<AttractionDTO> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(AttractionDTO attractionDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String attractionId) throws SQLException, ClassNotFoundException;
}
