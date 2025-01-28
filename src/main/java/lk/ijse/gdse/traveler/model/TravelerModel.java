package lk.ijse.gdse.traveler.model;

import lk.ijse.gdse.traveler.dto.TravelerDTO;
import lk.ijse.gdse.traveler.dao.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TravelerModel {

    public String getNextTravelerId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select traveler_id from traveler order by traveler_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // last id
            String substring = lastId.substring(1); // extract numbers
            int i = Integer.parseInt(substring); // convert the numbers part to int
            int newIdIndex = i + 1; // increment
            return String.format("T%03d", newIdIndex); // return the new id in string
        }
        return "T001"; // return the default ID
    }

    public boolean saveTraveler(TravelerDTO travelerDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into traveler values (?,?,?,?,?,?)",
                travelerDTO.getTravelerId(),
                travelerDTO.getName(),
                travelerDTO.getEmail(),
                travelerDTO.getContactNumber(),
                travelerDTO.getNationality(),
                travelerDTO.getIdNumber()
        );
    }

    public ArrayList<TravelerDTO> getAllTravelers() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from traveler");

        ArrayList<TravelerDTO> travelerDTOS = new ArrayList<>();

        while (rst.next()) {
            TravelerDTO travelerDTO = new TravelerDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            travelerDTOS.add(travelerDTO);
        }
        return travelerDTOS;
    }

    public boolean updateTraveler(TravelerDTO travelerDTO) throws SQLException {
        return CrudUtil.execute(
                "update traveler set name=?, email=?, contact_number=?, nationality=?, id_number=? where traveler_id=?",
                travelerDTO.getName(),
                travelerDTO.getEmail(),
                travelerDTO.getContactNumber(),
                travelerDTO.getNationality(),
                travelerDTO.getIdNumber(),
                travelerDTO.getTravelerId()
        );
    }

    public boolean deleteTraveler(String travelerId) throws SQLException {
        return CrudUtil.execute("delete from traveler where traveler_id=?", travelerId);
    }

    public ArrayList<String> getAllTravelerIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select traveler_id from traveler");

        ArrayList<String> travelerIds = new ArrayList<>();

        while (rst.next()) {
            travelerIds.add(rst.getString(1));
        }

        return travelerIds;
    }

    public TravelerDTO findById(String selectedTravelerId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from traveler where traveler_id=?", selectedTravelerId);

        if (rst.next()) {
            return new TravelerDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
        }
        return null;
    }
}
