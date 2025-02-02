package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dto.FoodDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodDaoImpl {
    public String getNextFoodId() throws SQLException {
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

    public boolean saveFood(FoodDTO foodDTO) throws SQLException {
        return SqlUtil.execute(
                "insert into food values (?,?,?,?)",
                foodDTO.getFoodServiceId(),
                foodDTO.getName(),
                foodDTO.getType(),
                foodDTO.getContact()
        );
    }

    public ArrayList<FoodDTO> getAllFoods() throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from food");

        ArrayList<FoodDTO> foodDTOS = new ArrayList<>();

        while (rst.next()) {
            FoodDTO foodDTO = new FoodDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            foodDTOS.add(foodDTO);
        }
        return foodDTOS;
    }

    public boolean updateFood(FoodDTO foodDTO) throws SQLException {
        return SqlUtil.execute(
                "update food set name=?, cuisine_type=?, contact_info=? where foodservice_id=?",
                foodDTO.getName(),
                foodDTO.getType(),
                foodDTO.getContact(),
                foodDTO.getFoodServiceId()
        );
    }

    public boolean deleteFood(String foodId) throws SQLException {
        return SqlUtil.execute("delete from food where foodservice_id=?", foodId);
    }
}
