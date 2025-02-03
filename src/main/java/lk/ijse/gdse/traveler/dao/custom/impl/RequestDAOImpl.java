package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.RequestDAO;
import lk.ijse.gdse.traveler.dto.RequestDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RequestDAOImpl implements RequestDAO {
    public String getNextRequestId() throws SQLException {
        ResultSet rst = SqlUtil.execute("select request_id from request order by request_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // last id
            String substring = lastId.substring(1); // extract numbers
            int i = Integer.parseInt(substring); // convert the numbers part to int
            int newIdIndex = i + 1; // increment
            return String.format("R%03d", newIdIndex); // return the new id in string
        }
        return "R001"; // return the default ID
    }

    public boolean saveRequest(RequestDTO requestDTO) throws SQLException {
        return SqlUtil.execute(
                "insert into request values (?,?,?,?,?)",
                requestDTO.getRequestId(),
                requestDTO.getTravelerId(),
                requestDTO.getRequestDate(),
                requestDTO.getRequestType(),
                requestDTO.getCashierId()
        );
    }

    public ArrayList<RequestDTO> getAllRequests() throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from request");

        ArrayList<RequestDTO> requestDTOS = new ArrayList<>();

        while (rst.next()) {
            RequestDTO requestDTO = new RequestDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            requestDTOS.add(requestDTO);
        }
        return requestDTOS;
    }

    public boolean deleteRequest(String requestId) throws SQLException {
        return SqlUtil.execute("delete from request where request_id=?", requestId);
    }

    public ArrayList<String> getAllRequestIds(String selectedTravelerId) throws SQLException {
        ResultSet rst = SqlUtil.execute("select request_id from request where traveler_id=?", selectedTravelerId);

        ArrayList<String> requestIds = new ArrayList<>();

        while (rst.next()) {
            requestIds.add(rst.getString(1));
        }

        return requestIds;
    }

    public RequestDTO findById(String selectedRequestId) throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from request where request_id=?", selectedRequestId);

        if (rst.next()) {
            return new RequestDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }

}
