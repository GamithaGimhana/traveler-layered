package lk.ijse.gdse.traveler.model;

import lk.ijse.gdse.traveler.dto.AttractionDTO;
import lk.ijse.gdse.traveler.dao.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttractionModel {
    public String getNextAttractionId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select attraction_id from attraction order by attraction_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // last id
            String substring = lastId.substring(1); // extract numbers
            int i = Integer.parseInt(substring); // convert the numbers part to int
            int newIdIndex = i + 1; // increment
            return String.format("A%03d", newIdIndex); // return the new id in string
        }
        return "A001"; // return the default ID
    }

    public boolean saveAttraction(AttractionDTO attractionDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into attraction values (?,?,?,?,?)",
                attractionDTO.getAttractionId(),
                attractionDTO.getName(),
                attractionDTO.getType(),
                attractionDTO.getOperatingHours(),
                attractionDTO.getDescription()
        );
    }

    public ArrayList<AttractionDTO> getAllAttractions() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from attraction");

        ArrayList<AttractionDTO> attractionDTOS = new ArrayList<>();

        while (rst.next()) {
            AttractionDTO attractionDTO = new AttractionDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            attractionDTOS.add(attractionDTO);
        }
        return attractionDTOS;
    }

    public boolean updateAttraction(AttractionDTO attractionDTO) throws SQLException {
        return CrudUtil.execute(
                "update attraction set name=?, type=?, operating_hours=?, description=? where attraction_id=?",
                attractionDTO.getName(),
                attractionDTO.getType(),
                attractionDTO.getOperatingHours(),
                attractionDTO.getDescription(),
                attractionDTO.getAttractionId()
        );
    }

    public boolean deleteAttraction(String attractionId) throws SQLException {
        return CrudUtil.execute("delete from attraction where attraction_id=?", attractionId);
    }
}
