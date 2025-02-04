package lk.ijse.gdse.traveler.bo.custom;

import lk.ijse.gdse.traveler.bo.SuperBO;
import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.dto.FoodDTO;
import lk.ijse.gdse.traveler.entity.Food;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FoodBO extends SuperBO {
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(FoodDTO foodDTO) throws SQLException, ClassNotFoundException;
    public ArrayList<FoodDTO> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(FoodDTO foodDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String foodId) throws SQLException, ClassNotFoundException;
}
