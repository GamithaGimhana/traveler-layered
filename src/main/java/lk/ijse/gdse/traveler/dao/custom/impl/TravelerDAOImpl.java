package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.TravelerDAO;
import lk.ijse.gdse.traveler.dto.TravelerDTO;
import lk.ijse.gdse.traveler.entity.Guide;
import lk.ijse.gdse.traveler.entity.Traveler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TravelerDAOImpl implements TravelerDAO {
    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select traveler_id from traveler order by traveler_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // last id
            String substring = lastId.substring(1); // extract numbers
            int i = Integer.parseInt(substring); // convert the numbers part to int
            int newIdIndex = i + 1; // increment
            return String.format("T%03d", newIdIndex); // return the new id in string
        }
        return "T001"; // return the default ID
    }

    @Override
    public boolean save(Traveler traveler) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute(
                "insert into traveler values (?,?,?,?,?,?)",
                traveler.getTravelerId(),
                traveler.getName(),
                traveler.getEmail(),
                traveler.getContactNumber(),
                traveler.getNationality(),
                traveler.getIdNumber()
        );
    }

    @Override
    public ArrayList<Traveler> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select * from traveler");

        ArrayList<Traveler> travelers = new ArrayList<>();

        while (rst.next()) {
            Traveler traveler = new Traveler(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            travelers.add(traveler);
        }
        return travelers;
    }

    @Override
    public boolean update(Traveler traveler) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute(
                "update traveler set name=?, email=?, contact_number=?, nationality=?, id_number=? where traveler_id=?",
                traveler.getName(),
                traveler.getEmail(),
                traveler.getContactNumber(),
                traveler.getNationality(),
                traveler.getIdNumber(),
                traveler.getTravelerId()
        );
    }

    @Override
    public boolean delete(String travelerId) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute("delete from traveler where traveler_id=?", travelerId);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select traveler_id from traveler");

        ArrayList<String> travelerIds = new ArrayList<>();

        while (rst.next()) {
            travelerIds.add(rst.getString(1));
        }

        return travelerIds;
    }

    @Override
    public Traveler findById(String selectedTravelerId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select * from traveler where traveler_id=?", selectedTravelerId);

//        if (rst.next()) {
//            return new Traveler(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getString(4),
//                    rst.getString(5),
//                    rst.getString(6)
//            );
//        }
//        return null;
        rst.next();

        Traveler traveler = new Traveler(selectedTravelerId, rst.getString("name"), rst.getString("email"), rst.getString("contact_number"), rst.getString("nationality"), rst.getString("id_number"));
        return traveler;
    }
}
