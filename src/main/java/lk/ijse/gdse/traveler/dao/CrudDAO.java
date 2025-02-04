package lk.ijse.gdse.traveler.dao;

import lk.ijse.gdse.traveler.dto.AccomodationDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(T dto) throws SQLException, ClassNotFoundException;
    public ArrayList<T> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(T dto) throws SQLException, ClassNotFoundException;
    public boolean delete(String id) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException;
    public T findById(String selectedId) throws SQLException, ClassNotFoundException;
}
