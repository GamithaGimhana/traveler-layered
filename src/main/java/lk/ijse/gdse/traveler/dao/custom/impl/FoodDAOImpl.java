package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.FoodDAO;
import lk.ijse.gdse.traveler.entity.Food;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodDAOImpl implements FoodDAO {
    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SqlUtil.execute("select foodservice_id from food order by foodservice_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // last id
            String substring = lastId.substring(1); // extract numbers
            int i = Integer.parseInt(substring); // convert the numbers part to int
            int newIdIndex = i + 1; // increment
            return String.format("F%03d", newIdIndex); // return the new id in string
        }
        return "F001"; // return the default ID
    }

    @Override
    public boolean save(Food food) throws SQLException {
        return SqlUtil.execute(
                "insert into food values (?,?,?,?)",
                food.getFoodServiceId(),
                food.getName(),
                food.getType(),
                food.getContact()
        );
    }

    @Override
    public ArrayList<Food> getAll() throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from food");

        ArrayList<Food> foods = new ArrayList<>();

        while (rst.next()) {
            Food food = new Food(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            foods.add(food);
        }
        return foods;
    }

    @Override
    public boolean update(Food food) throws SQLException {
        return SqlUtil.execute(
                "update food set name=?, cuisine_type=?, contact_info=? where foodservice_id=?",
                food.getName(),
                food.getType(),
                food.getContact(),
                food.getFoodServiceId()
        );
    }

    @Override
    public boolean delete(String foodId) throws SQLException {
        return SqlUtil.execute("delete from food where foodservice_id=?", foodId);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return null;
    }

    @Override
    public Food findById(String selectedId) throws SQLException {
        return null;
    }
}
