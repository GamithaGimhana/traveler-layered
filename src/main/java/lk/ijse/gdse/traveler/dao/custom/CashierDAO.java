package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dto.CashierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CashierDAO {
    public String getNextCashierId() throws SQLException;
    public boolean saveCashier(CashierDTO cashierDTO) throws SQLException;
    public ArrayList<CashierDTO> getAllCashier() throws SQLException;
    public ArrayList<String> getAllCashierIds() throws SQLException;
    public boolean updateCashier(CashierDTO cashierDTO) throws SQLException;
    public boolean deleteCashier(String cashierId) throws SQLException;
    public CashierDTO findById(String selectedCashierId) throws SQLException;
}
