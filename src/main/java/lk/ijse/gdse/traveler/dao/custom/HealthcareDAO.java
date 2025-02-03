package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dto.HealthcareDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HealthcareDAO {
    public String getNextHealthcareId() throws SQLException;
    public boolean saveHealthcare(HealthcareDTO healthcareDTO) throws SQLException;
    public ArrayList<HealthcareDTO> getAllHealthcares() throws SQLException;
    public boolean updateHealthcare(HealthcareDTO healthcareDTO) throws SQLException;
    public boolean deleteHealthcare(String healthcareId) throws SQLException;
}
