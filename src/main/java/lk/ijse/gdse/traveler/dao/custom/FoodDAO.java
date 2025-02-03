package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dto.FoodDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FoodDAO {
    public String getNextFoodId() throws SQLException;
    public boolean saveFood(FoodDTO foodDTO) throws SQLException;
    public ArrayList<FoodDTO> getAllFoods() throws SQLException;
    public boolean updateFood(FoodDTO foodDTO) throws SQLException;
    public boolean deleteFood(String foodId) throws SQLException;
}
