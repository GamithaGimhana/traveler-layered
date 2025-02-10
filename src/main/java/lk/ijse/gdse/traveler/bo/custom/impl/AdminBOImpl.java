package lk.ijse.gdse.traveler.bo.custom.impl;

import lk.ijse.gdse.traveler.bo.custom.AdminBO;
import lk.ijse.gdse.traveler.dao.DAOFactory;
import lk.ijse.gdse.traveler.dao.custom.AccomodationDAO;
import lk.ijse.gdse.traveler.dao.custom.AdminDAO;
import lk.ijse.gdse.traveler.dao.custom.impl.AdminDAOImpl;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;
import lk.ijse.gdse.traveler.dto.AdminDTO;
import lk.ijse.gdse.traveler.entity.Accomodation;
import lk.ijse.gdse.traveler.entity.Admin;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminBOImpl implements AdminBO {
//    AdminDAO adminDAO = new AdminDAOImpl();
    AdminDAO adminDAO = (AdminDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ADMIN);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return adminDAO.getNextId();
    }

    @Override
    public boolean save(AdminDTO adminDTO) throws SQLException, ClassNotFoundException {
        return adminDAO.save(new Admin(adminDTO.getAdminId(),adminDTO.getName(), adminDTO.getEmail(), adminDTO.getContactNumber(), adminDTO.getUsername(), adminDTO.getPassword()));
    }

    @Override
    public ArrayList<AdminDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<AdminDTO> adminDTOS = new ArrayList<>();
        ArrayList<Admin> admins = adminDAO.getAll();
        for (Admin admin : admins) {
            adminDTOS.add(new AdminDTO(admin.getAdminId(),admin.getName(), admin.getEmail(), admin.getContactNumber(), admin.getUsername(), admin.getPassword()));
        }
        return adminDTOS;
    }

    @Override
    public boolean update(AdminDTO adminDTO) throws SQLException, ClassNotFoundException {
        return adminDAO.update(new Admin(adminDTO.getAdminId(),adminDTO.getName(), adminDTO.getEmail(), adminDTO.getContactNumber(), adminDTO.getUsername(), adminDTO.getPassword()));
    }

    @Override
    public boolean delete(String adminId) throws SQLException, ClassNotFoundException {
        return adminDAO.delete(adminId);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return adminDAO.getAllIds();
    }

    @Override
    public AdminDTO findById(String selectedAdminId) throws SQLException, ClassNotFoundException {
        System.out.println("selectedAdminId: " + selectedAdminId);
        Admin admin = adminDAO.findById(selectedAdminId);
        return new AdminDTO(admin.getAdminId(),admin.getName(), admin.getEmail(), admin.getContactNumber(), admin.getUsername(), admin.getPassword());
    }
}