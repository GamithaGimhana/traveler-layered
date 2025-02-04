package lk.ijse.gdse.traveler.bo.custom;

import lk.ijse.gdse.traveler.bo.SuperBO;
import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.dto.AdminDTO;
import lk.ijse.gdse.traveler.entity.Admin;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AdminBO extends SuperBO {
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(AdminDTO adminDTO) throws SQLException, ClassNotFoundException;
    public ArrayList<AdminDTO> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(AdminDTO adminDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String adminId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException;
    public AdminDTO findById(String selectedAdminId) throws SQLException, ClassNotFoundException;
}
