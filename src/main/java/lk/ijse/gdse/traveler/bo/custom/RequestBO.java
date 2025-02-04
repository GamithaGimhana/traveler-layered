package lk.ijse.gdse.traveler.bo.custom;

import lk.ijse.gdse.traveler.bo.SuperBO;
import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.dto.RequestDTO;
import lk.ijse.gdse.traveler.entity.Request;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RequestBO extends SuperBO {
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(RequestDTO requestDTO) throws SQLException, ClassNotFoundException;
    public ArrayList<RequestDTO> getAll() throws SQLException, ClassNotFoundException;
    public boolean delete(String requestId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllIds(String selectedTravelerId) throws SQLException, ClassNotFoundException;
    public RequestDTO findById(String selectedRequestId) throws SQLException, ClassNotFoundException;
}
