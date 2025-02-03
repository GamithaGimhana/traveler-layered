package lk.ijse.gdse.traveler.dao;

import lk.ijse.gdse.traveler.dto.TravelerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {
    public String getNextId() throws SQLException;
    public boolean save(T dto) throws SQLException;
    public ArrayList<T> getAll() throws SQLException;
    public boolean update(T dto) throws SQLException;
    public boolean delete(String id) throws SQLException;
    public ArrayList<String> getAllIds() throws SQLException;
    public T findById(String selectedId) throws SQLException;
}
