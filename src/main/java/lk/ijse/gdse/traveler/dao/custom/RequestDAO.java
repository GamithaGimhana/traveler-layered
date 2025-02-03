package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.dto.RequestDTO;
import lk.ijse.gdse.traveler.entity.Request;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RequestDAO extends CrudDAO<Request> {
//    public String getNextRequestId() throws SQLException;
//    public boolean saveRequest(RequestDTO requestDTO) throws SQLException;
//    public ArrayList<RequestDTO> getAllRequests() throws SQLException;
//    public boolean deleteRequest(String requestId) throws SQLException;
    public ArrayList<String> getAllIds(String selectedTravelerId) throws SQLException;
//    public RequestDTO findById(String selectedRequestId) throws SQLException;
}
