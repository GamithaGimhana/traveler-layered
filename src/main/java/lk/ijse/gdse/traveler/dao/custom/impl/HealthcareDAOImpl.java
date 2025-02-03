package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.HealthcareDAO;
import lk.ijse.gdse.traveler.dto.HealthcareDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HealthcareDAOImpl implements HealthcareDAO {
    public String getNextHealthcareId() throws SQLException {
        ResultSet rst = SqlUtil.execute("select healthcare_id from healthcare order by healthcare_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // last id
            String substring = lastId.substring(1); // extract numbers
            int i = Integer.parseInt(substring); // convert the numbers part to int
            int newIdIndex = i + 1; // increment
            return String.format("H%03d", newIdIndex); // return the new id in string
        }
        return "H001"; // return the default ID
    }

    public boolean saveHealthcare(HealthcareDTO healthcareDTO) throws SQLException {
        return SqlUtil.execute(
                "insert into healthcare values (?,?,?,?)",
                healthcareDTO.getHealthcareId(),
                healthcareDTO.getName(),
                healthcareDTO.getContact(),
                healthcareDTO.isEmergency()
        );
    }

    public ArrayList<HealthcareDTO> getAllHealthcares() throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from healthcare");

        ArrayList<HealthcareDTO> healthcareDTOS = new ArrayList<>();

        while (rst.next()) {
            HealthcareDTO healthcareDTO = new HealthcareDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getBoolean(4)
            );
            healthcareDTOS.add(healthcareDTO);
        }
        return healthcareDTOS;
    }

    public boolean updateHealthcare(HealthcareDTO healthcareDTO) throws SQLException {
        return SqlUtil.execute(
                "update healthcare set name=?, contact_info=?, emergency_services=? where healthcare_id=?",
                healthcareDTO.getName(),
                healthcareDTO.getContact(),
                healthcareDTO.isEmergency(),
                healthcareDTO.getHealthcareId()
        );
    }

    public boolean deleteHealthcare(String healthcareId) throws SQLException {
        return SqlUtil.execute("delete from healthcare where healthcare_id=?", healthcareId);
    }
}
