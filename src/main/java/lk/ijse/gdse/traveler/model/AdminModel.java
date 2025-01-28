package lk.ijse.gdse.traveler.model;

import lk.ijse.gdse.traveler.dto.AdminDTO;
import lk.ijse.gdse.traveler.dao.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminModel {
    public String getNextAdminId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select admin_id from admin order by admin_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // last id
            String substring = lastId.substring(1); // extract numbers
            int i = Integer.parseInt(substring); // convert the numbers part to int
            int newIdIndex = i + 1; // increment
            return String.format("A%03d", newIdIndex); // return the new id in string
        }
        return "A001"; // return the default ID
    }

    public boolean saveAdmin(AdminDTO adminDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into admin values (?,?,?,?,?,?)",
                adminDTO.getAdminId(),
                adminDTO.getName(),
                adminDTO.getEmail(),
                adminDTO.getContactNumber(),
                adminDTO.getUsername(),
                adminDTO.getPassword()
        );
    }

    public ArrayList<AdminDTO> getAllAdmin() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from admin");

        ArrayList<AdminDTO> adminDTOS = new ArrayList<>();

        while (rst.next()) {
            AdminDTO adminDTO = new AdminDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            adminDTOS.add(adminDTO);
        }
        return adminDTOS;
    }

    public boolean updateAdmin(AdminDTO adminDTO) throws SQLException {
        return CrudUtil.execute(
                "update admin set name=?, email=?, contact_number=?, username=?, password=? where admin_id=?",
                adminDTO.getName(),
                adminDTO.getEmail(),
                adminDTO.getContactNumber(),
                adminDTO.getUsername(),
                adminDTO.getPassword(),
                adminDTO.getAdminId()
        );
    }

    public boolean deleteAdmin(String adminId) throws SQLException {
        return CrudUtil.execute("delete from admin where admin_id=?", adminId);
    }

    public ArrayList<String> getAllAdminIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select admin_id from admin");

        ArrayList<String> adminIds = new ArrayList<>();

        while (rst.next()) {
            adminIds.add(rst.getString(1));
        }

        return adminIds;
    }

    public AdminDTO findById(String selectedAdminId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from admin where admin_id=?", selectedAdminId);

        if (rst.next()) {
            return new AdminDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
        }
        return null;
    }
}