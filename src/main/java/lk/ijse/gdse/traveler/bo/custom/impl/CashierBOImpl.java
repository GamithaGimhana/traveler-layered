package lk.ijse.gdse.traveler.bo.custom.impl;

import lk.ijse.gdse.traveler.bo.custom.CashierBO;
import lk.ijse.gdse.traveler.dao.custom.CashierDAO;
import lk.ijse.gdse.traveler.dao.custom.impl.CashierDAOImpl;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;
import lk.ijse.gdse.traveler.dto.AttractionDTO;
import lk.ijse.gdse.traveler.dto.CashierDTO;
import lk.ijse.gdse.traveler.entity.Admin;
import lk.ijse.gdse.traveler.entity.Attraction;
import lk.ijse.gdse.traveler.entity.Cashier;

import java.sql.SQLException;
import java.util.ArrayList;

public class CashierBOImpl implements CashierBO {
    CashierDAO cashierDAO = new CashierDAOImpl();

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return cashierDAO.getNextId();
    }

    @Override
    public boolean save(CashierDTO cashierDTO) throws SQLException, ClassNotFoundException {
        return cashierDAO.save(new Cashier(cashierDTO.getCashierId(), cashierDTO.getName(), cashierDTO.getEmail(), cashierDTO.getContactNumber(), cashierDTO.getUsername(), cashierDTO.getPassword(), cashierDTO.getAdminId()));
    }

    @Override
    public ArrayList<CashierDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<CashierDTO> cashierDTOS = new ArrayList<>();
        ArrayList<Cashier> cashiers = cashierDAO.getAll();
        for (Cashier cashier : cashiers) {
            cashierDTOS.add(new CashierDTO(cashier.getCashierId(), cashier.getName(), cashier.getEmail(), cashier.getContactNumber(), cashier.getUsername(), cashier.getPassword(), cashier.getAdminId()));
        }
        return cashierDTOS;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return cashierDAO.getAllIds();
    }

    @Override
    public boolean update(CashierDTO cashierDTO) throws SQLException, ClassNotFoundException {
        return cashierDAO.update(new Cashier(cashierDTO.getCashierId(), cashierDTO.getName(), cashierDTO.getEmail(), cashierDTO.getContactNumber(), cashierDTO.getUsername(), cashierDTO.getPassword(), cashierDTO.getAdminId()));
    }

    @Override
    public boolean delete(String cashierId) throws SQLException, ClassNotFoundException {
        return cashierDAO.delete(cashierId);
    }

    @Override
    public CashierDTO findById(String selectedCashierId) throws SQLException, ClassNotFoundException {
        Cashier cashier = cashierDAO.findById(selectedCashierId);
        return new CashierDTO(cashier.getCashierId(), cashier.getName(), cashier.getEmail(), cashier.getContactNumber(), cashier.getUsername(), cashier.getPassword(), cashier.getAdminId());
    }
}
