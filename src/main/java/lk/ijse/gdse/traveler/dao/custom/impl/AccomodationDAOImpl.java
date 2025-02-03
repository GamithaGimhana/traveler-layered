package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.AccomodationDAO;
import lk.ijse.gdse.traveler.entity.Accomodation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccomodationDAOImpl implements AccomodationDAO {
    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SqlUtil.execute("select accommodation_id from accommodation order by accommodation_id desc limit 1");

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
    public boolean save(Accomodation accomodation) throws SQLException {
        return SqlUtil.execute(
                "insert into accommodation values (?,?,?,?)",
                accomodation.getAccomodationId(),
                accomodation.getName(),
                accomodation.getType(),
                accomodation.getContactNumber()
        );
    }

    @Override
    public ArrayList<Accomodation> getAll() throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from accommodation");

        ArrayList<Accomodation> accomodations = new ArrayList<>();

        while (rst.next()) {
            Accomodation accomodation = new Accomodation(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            accomodations.add(accomodation);
        }
        return accomodations;
    }

    @Override
    public boolean update(Accomodation accomodation) throws SQLException {
        return SqlUtil.execute(
                "update accommodation set name=?, type=?, contact_number=? where accommodation_id=?",
                accomodation.getName(),
                accomodation.getType(),
                accomodation.getContactNumber(),
                accomodation.getAccomodationId()
        );
    }

    @Override
    public boolean delete(String accommodationId) throws SQLException {
        return SqlUtil.execute("delete from accommodation where accommodation_id=?", accommodationId);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return null;
    }

    @Override
    public Accomodation findById(String selectedId) throws SQLException {
        return null;
    }
}
