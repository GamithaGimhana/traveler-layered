package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccomodationDaoImpl {
    public String getNextAccomodationId() throws SQLException {
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

    public boolean saveAccommodation(AccomodationDTO accomodationDTO) throws SQLException {
        return SqlUtil.execute(
                "insert into accommodation values (?,?,?,?)",
                accomodationDTO.getAccomodationId(),
                accomodationDTO.getName(),
                accomodationDTO.getType(),
                accomodationDTO.getContactNumber()
        );
    }

    public ArrayList<AccomodationDTO> getAllAccommodations() throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from accommodation");

        ArrayList<AccomodationDTO> accomodationDTOS = new ArrayList<>();

        while (rst.next()) {
            AccomodationDTO accomodationDTO = new AccomodationDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            accomodationDTOS.add(accomodationDTO);
        }
        return accomodationDTOS;
    }

    public boolean updateAccommodation(AccomodationDTO accomodationDTO) throws SQLException {
        return SqlUtil.execute(
                "update accommodation set name=?, type=?, contact_number=? where accommodation_id=?",
                accomodationDTO.getName(),
                accomodationDTO.getType(),
                accomodationDTO.getContactNumber(),
                accomodationDTO.getAccomodationId()
        );
    }

    public boolean deleteAccommodation(String accommodationId) throws SQLException {
        return SqlUtil.execute("delete from accommodation where accommodation_id=?", accommodationId);
    }
}
