package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dto.AdminDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AdminDAO {
    public String getNextAdminId() throws SQLException;
    public boolean saveAdmin(AdminDTO adminDTO) throws SQLException;
    public ArrayList<AdminDTO> getAllAdmin() throws SQLException;
    public boolean updateAdmin(AdminDTO adminDTO) throws SQLException;
    public boolean deleteAdmin(String adminId) throws SQLException;
    public ArrayList<String> getAllAdminIds() throws SQLException;
    public AdminDTO findById(String selectedAdminId) throws SQLException;
}
