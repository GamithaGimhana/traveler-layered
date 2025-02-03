package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.HealthcareDAO;
import lk.ijse.gdse.traveler.dto.HealthcareDTO;
import lk.ijse.gdse.traveler.entity.Healthcare;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HealthcareDAOImpl implements HealthcareDAO {
    @Override
    public String getNextId() throws SQLException {
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

    @Override
    public boolean save(Healthcare healthcare) throws SQLException {
        return SqlUtil.execute(
                "insert into healthcare values (?,?,?,?)",
                healthcare.getHealthcareId(),
                healthcare.getName(),
                healthcare.getContact(),
                healthcare.isEmergency()
        );
    }

    @Override
    public ArrayList<Healthcare> getAll() throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from healthcare");

        ArrayList<Healthcare> healthcares = new ArrayList<>();

        while (rst.next()) {
            Healthcare healthcare = new Healthcare(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getBoolean(4)
            );
            healthcares.add(healthcare);
        }
        return healthcares;
    }

    @Override
    public boolean update(Healthcare healthcare) throws SQLException {
        return SqlUtil.execute(
                "update healthcare set name=?, contact_info=?, emergency_services=? where healthcare_id=?",
                healthcare.getName(),
                healthcare.getContact(),
                healthcare.isEmergency(),
                healthcare.getHealthcareId()
        );
    }

    @Override
    public boolean delete(String healthcareId) throws SQLException {
        return SqlUtil.execute("delete from healthcare where healthcare_id=?", healthcareId);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return null;
    }

    @Override
    public Healthcare findById(String selectedId) throws SQLException {
        return null;
    }
}
