package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.AttractionDAO;
import lk.ijse.gdse.traveler.entity.Attraction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttractionDAOImpl implements AttractionDAO {
    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select attraction_id from attraction order by attraction_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // last id
            String substring = lastId.substring(1); // extract numbers
            int i = Integer.parseInt(substring); // convert the numbers part to int
            int newIdIndex = i + 1; // increment
            return String.format("A%03d", newIdIndex); // return the new id in string
        }
        return "A001"; // return the default ID
    }

    @Override
    public boolean save(Attraction attraction) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute(
                "insert into attraction values (?,?,?,?,?)",
                attraction.getAttractionId(),
                attraction.getName(),
                attraction.getType(),
                attraction.getOperatingHours(),
                attraction.getDescription()
        );
    }

    @Override
    public ArrayList<Attraction> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select * from attraction");

        ArrayList<Attraction> attractions = new ArrayList<>();

        while (rst.next()) {
            Attraction attraction = new Attraction(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            attractions.add(attraction);
        }
        return attractions;
    }

    @Override
    public boolean update(Attraction attraction) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute(
                "update attraction set name=?, type=?, operating_hours=?, description=? where attraction_id=?",
                attraction.getName(),
                attraction.getType(),
                attraction.getOperatingHours(),
                attraction.getDescription(),
                attraction.getAttractionId()
        );
    }

    @Override
    public boolean delete(String attractionId) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute("delete from attraction where attraction_id=?", attractionId);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return null;
    }

    @Override
    public Attraction findById(String selectedId) throws SQLException {
        return null;
    }
}
