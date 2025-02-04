package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.AdminDAO;
import lk.ijse.gdse.traveler.entity.Admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminDAOImpl implements AdminDAO {
    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select admin_id from admin order by admin_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // last id
            String substring = lastId.substring(1); // extract numbers
            int i = Integer.parseInt(substring); // convert the numbers part to int
            int newIdIndex = i + 1; // increment
            return String.format("A%03d", newIdIndex); // return the new id in string
        }
        return "A001"; // return the default ID
    }

    @Override
    public boolean save(Admin admin) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute(
                "insert into admin values (?,?,?,?,?,?)",
                admin.getAdminId(),
                admin.getName(),
                admin.getEmail(),
                admin.getContactNumber(),
                admin.getUsername(),
                admin.getPassword()
        );
    }

    @Override
    public ArrayList<Admin> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select * from admin");

        ArrayList<Admin> admins = new ArrayList<>();

        while (rst.next()) {
            Admin admin = new Admin(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            admins.add(admin);
        }
        return admins;
    }

    @Override
    public boolean update(Admin admin) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute(
                "update admin set name=?, email=?, contact_number=?, username=?, password=? where admin_id=?",
                admin.getName(),
                admin.getEmail(),
                admin.getContactNumber(),
                admin.getUsername(),
                admin.getPassword(),
                admin.getAdminId()
        );
    }

    @Override
    public boolean delete(String adminId) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute("delete from admin where admin_id=?", adminId);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select admin_id from admin");

        ArrayList<String> adminIds = new ArrayList<>();

        while (rst.next()) {
            adminIds.add(rst.getString(1));
        }

        return adminIds;
    }

    @Override
    public Admin findById(String selectedAdminId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select * from admin where admin_id=?", selectedAdminId);

        if (rst.next()) {
            return new Admin(
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