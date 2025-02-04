package lk.ijse.gdse.traveler.bo.custom;

import lk.ijse.gdse.traveler.bo.SuperBO;
import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;
import lk.ijse.gdse.traveler.entity.Accomodation;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AccomodationBO extends SuperBO {
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(AccomodationDTO accomodationDTO) throws SQLException, ClassNotFoundException;
    public ArrayList<AccomodationDTO> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(AccomodationDTO accomodationDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String accommodationId) throws SQLException, ClassNotFoundException;
}
