package lk.ijse.gdse.traveler.bo.custom;

import lk.ijse.gdse.traveler.bo.SuperBO;
import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.dto.CashierDTO;
import lk.ijse.gdse.traveler.entity.Cashier;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CashierBO extends SuperBO {
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(CashierDTO cashierDTO) throws SQLException, ClassNotFoundException;
    public ArrayList<CashierDTO> getAll() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException;
    public boolean update(CashierDTO cashierDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String cashierId) throws SQLException, ClassNotFoundException;
    public CashierDTO findById(String selectedCashierId) throws SQLException, ClassNotFoundException;
}
