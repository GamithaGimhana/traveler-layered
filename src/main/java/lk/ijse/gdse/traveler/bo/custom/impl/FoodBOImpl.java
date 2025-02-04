package lk.ijse.gdse.traveler.bo.custom.impl;

import lk.ijse.gdse.traveler.bo.custom.FoodBO;
import lk.ijse.gdse.traveler.dao.custom.FoodDAO;
import lk.ijse.gdse.traveler.dao.custom.impl.FoodDAOImpl;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;
import lk.ijse.gdse.traveler.dto.DriverDTO;
import lk.ijse.gdse.traveler.dto.FoodDTO;
import lk.ijse.gdse.traveler.entity.Driver;
import lk.ijse.gdse.traveler.entity.Food;

import java.sql.SQLException;
import java.util.ArrayList;

public class FoodBOImpl implements FoodBO {
    FoodDAO foodDAO = new FoodDAOImpl();
    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return foodDAO.getNextId();
    }

    @Override
    public boolean save(FoodDTO foodDTO) throws SQLException, ClassNotFoundException {
        return foodDAO.save(new Food(foodDTO.getFoodServiceId(), foodDTO.getName(), foodDTO.getType(), foodDTO.getContact()));
    }

    @Override
    public ArrayList<FoodDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<FoodDTO> foodDTOS = new ArrayList<>();
        ArrayList<Food> foods = foodDAO.getAll();
        for (Food food: foods) {
            foodDTOS.add(new FoodDTO(food.getFoodServiceId(), food.getName(), food.getType(), food.getContact()));
        }
        return foodDTOS;
    }

    @Override
    public boolean update(FoodDTO foodDTO) throws SQLException, ClassNotFoundException {
        return foodDAO.update(new Food(foodDTO.getFoodServiceId(), foodDTO.getName(), foodDTO.getType(), foodDTO.getContact()));
    }

    @Override
    public boolean delete(String foodId) throws SQLException, ClassNotFoundException {
        return foodDAO.delete(foodId);
    }
}
