package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dto.RequestDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RequestDAO {
    public String getNextRequestId() throws SQLException;
    public boolean saveRequest(RequestDTO requestDTO) throws SQLException;
    public ArrayList<RequestDTO> getAllRequests() throws SQLException;
    public boolean deleteRequest(String requestId) throws SQLException;
    public ArrayList<String> getAllRequestIds(String selectedTravelerId) throws SQLException;
    public RequestDTO findById(String selectedRequestId) throws SQLException;
}
