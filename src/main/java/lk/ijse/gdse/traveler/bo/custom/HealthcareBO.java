package lk.ijse.gdse.traveler.bo.custom;

import lk.ijse.gdse.traveler.bo.SuperBO;
import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.dto.HealthcareDTO;
import lk.ijse.gdse.traveler.entity.Healthcare;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HealthcareBO extends SuperBO {
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(HealthcareDTO healthcareDTO) throws SQLException, ClassNotFoundException;
    public ArrayList<HealthcareDTO> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(HealthcareDTO healthcareDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String healthcareId) throws SQLException, ClassNotFoundException;
}
