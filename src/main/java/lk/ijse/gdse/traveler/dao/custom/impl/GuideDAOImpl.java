package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.GuideDAO;
import lk.ijse.gdse.traveler.entity.Guide;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuideDAOImpl implements GuideDAO {

    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SqlUtil.execute("select guide_id from guide order by guide_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // last id
            String substring = lastId.substring(1); // extract numbers
            int i = Integer.parseInt(substring); // convert the numbers part to int
            int newIdIndex = i + 1; // increment
            return String.format("G%03d", newIdIndex); // return the new id in string
        }
        return "G001"; // return the default ID
    }

    @Override
    public boolean save(Guide guide) throws SQLException {
        return SqlUtil.execute(
                "insert into guide values (?,?,?,?,?)",
                guide.getGuideId(),
                guide.getName(),
                guide.getLicenseNumber(),
                guide.getContactNumber(),
                guide.isAvailabilityStatus()
        );
    }

    @Override
    public ArrayList<Guide> getAll() throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from guide");

        ArrayList<Guide> guides = new ArrayList<>();

        while (rst.next()) {
            Guide guide = new Guide(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getBoolean(5)
            );
            guides.add(guide);
        }
        return guides;
    }

    @Override
    public boolean update(Guide guide) throws SQLException {
        return SqlUtil.execute(
                "update guide set name=?, license_number=?, contact_number=?, availability_status=? where guide_id=?",
                guide.getName(),
                guide.getLicenseNumber(),
                guide.getContactNumber(),
                guide.isAvailabilityStatus(),
                guide.getGuideId()
        );
    }

    @Override
    public boolean delete(String guideId) throws SQLException {
        return SqlUtil.execute("delete from guide where guide_id=?", guideId);
    }

    @Override
    public ArrayList<String> getAllIds(String selectedLanguageId) throws SQLException {
        ResultSet rst = SqlUtil.execute("select g.guide_id from guide g join guide_languages gL on g.guide_id = gL.guide_id where gL.language_id = ? and g.availability_status=true", selectedLanguageId);

        ArrayList<String> guideIds = new ArrayList<>();

        while (rst.next()) {
            guideIds.add(rst.getString(1));
        }

        return guideIds;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = SqlUtil.execute("select guide_id from guide");

        ArrayList<String> guideIds = new ArrayList<>();

        while (rst.next()) {
            guideIds.add(rst.getString(1));
        }

        return guideIds;
    }

    @Override
    public Guide findById(String selectedGuideId) throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from guide where guide_id=?", selectedGuideId);

        if (rst.next()) {
            return new Guide(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getBoolean(5)
            );
        }
        return null;
    }

    @Override
    public boolean updateGuideList(String guideId, boolean status) throws SQLException {
        System.out.println("Guide is updated");
        return SqlUtil.execute(
                "update guide set availability_status = ? where guide_id = ?",
                status,
                guideId
        );
    }
}
