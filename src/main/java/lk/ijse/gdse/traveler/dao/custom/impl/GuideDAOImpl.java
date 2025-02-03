package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.GuideDAO;
import lk.ijse.gdse.traveler.dto.GuideDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuideDAOImpl implements GuideDAO {

    public String getNextGuideId() throws SQLException {
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

    public boolean saveGuide(GuideDTO guideDTO) throws SQLException {
        return SqlUtil.execute(
                "insert into guide values (?,?,?,?,?)",
                guideDTO.getGuideId(),
                guideDTO.getName(),
                guideDTO.getLicenseNumber(),
                guideDTO.getContactNumber(),
                guideDTO.isAvailabilityStatus()
        );
    }

    public ArrayList<GuideDTO> getAllGuides() throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from guide");

        ArrayList<GuideDTO> guideDTOS = new ArrayList<>();

        while (rst.next()) {
            GuideDTO guideDTO = new GuideDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getBoolean(5)
            );
            guideDTOS.add(guideDTO);
        }
        return guideDTOS;
    }

    public boolean updateGuide(GuideDTO guideDTO) throws SQLException {
        return SqlUtil.execute(
                "update guide set name=?, license_number=?, contact_number=?, availability_status=? where guide_id=?",
                guideDTO.getName(),
                guideDTO.getLicenseNumber(),
                guideDTO.getContactNumber(),
                guideDTO.isAvailabilityStatus(),
                guideDTO.getGuideId()
        );
    }

    public boolean deleteGuide(String guideId) throws SQLException {
        return SqlUtil.execute("delete from guide where guide_id=?", guideId);
    }

    public ArrayList<String> getAllGuideIds(String selectedLanguageId) throws SQLException {
        ResultSet rst = SqlUtil.execute("select g.guide_id from guide g join guide_languages gL on g.guide_id = gL.guide_id where gL.language_id = ? and g.availability_status=true", selectedLanguageId);

        ArrayList<String> guideIds = new ArrayList<>();

        while (rst.next()) {
            guideIds.add(rst.getString(1));
        }

        return guideIds;
    }

    public ArrayList<String> getAllGuideIds() throws SQLException {
        ResultSet rst = SqlUtil.execute("select guide_id from guide");

        ArrayList<String> guideIds = new ArrayList<>();

        while (rst.next()) {
            guideIds.add(rst.getString(1));
        }

        return guideIds;
    }

    public GuideDTO findById(String selectedGuideId) throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from guide where guide_id=?", selectedGuideId);

        if (rst.next()) {
            return new GuideDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getBoolean(5)
            );
        }
        return null;
    }

    public boolean updateGuideList(String guideId, boolean status) throws SQLException {
        System.out.println("Guide is updated");
        return SqlUtil.execute(
                "update guide set availability_status = ? where guide_id = ?",
                status,
                guideId
        );
    }
}
