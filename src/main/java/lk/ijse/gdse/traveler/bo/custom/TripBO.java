package lk.ijse.gdse.traveler.bo.custom;

import lk.ijse.gdse.traveler.bo.SuperBO;
import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.dto.TripDTO;
import lk.ijse.gdse.traveler.entity.Trip;

import java.sql.SQLException;

public interface TripBO extends SuperBO {
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(TripDTO tripDTO) throws SQLException, ClassNotFoundException;
    public boolean checkRequestIdExists(String requestId) throws SQLException, ClassNotFoundException;
}
