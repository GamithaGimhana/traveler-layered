package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.VehicleDAO;
import lk.ijse.gdse.traveler.entity.Vehicle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleDAOImpl implements VehicleDAO {

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select vehicle_id from vehicle order by vehicle_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // last id
            String substring = lastId.substring(1); // extract numbers
            int i = Integer.parseInt(substring); // convert the numbers part to int
            int newIdIndex = i + 1; // increment
            return String.format("V%03d", newIdIndex); // return the new id in string
        }
        return "V001"; // return the default ID
    }

    @Override
    public boolean save(Vehicle vehicleDTO) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute(
                "insert into vehicle values (?,?,?,?,?,?)",
                vehicleDTO.getVehicleId(),
                vehicleDTO.getVehicleType(),
                vehicleDTO.getModel(),
                vehicleDTO.getLicensePlateNumber(),
                vehicleDTO.getDailyPrice(),
                vehicleDTO.isAvailabilityStatus()
        );
    }

    @Override
    public ArrayList<Vehicle> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select * from vehicle");

        ArrayList<Vehicle> vehicles = new ArrayList<>();

        while (rst.next()) {
            Vehicle vehicle = new Vehicle(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getBoolean(6)
            );
            vehicles.add(vehicle);
        }
        return vehicles;
    }

    @Override
    public boolean update(Vehicle vehicle) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute(
                "update vehicle set vehicle_type=?, model=?, license_plate_number=?, daily_price=?, availability_status=? where vehicle_id=?",
                vehicle.getVehicleType(),
                vehicle.getModel(),
                vehicle.getLicensePlateNumber(),
                vehicle.getDailyPrice(),
                vehicle.isAvailabilityStatus(),
                vehicle.getVehicleId()
        );
    }

    @Override
    public boolean delete(String vehicleId) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute("delete from vehicle where vehicle_id=?", vehicleId);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return null;
    }

    @Override
    public ArrayList<String> getAllIds(String selectedVehicleModel) throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select vehicle_id from vehicle where model=? and availability_status=true", selectedVehicleModel);

        ArrayList<String> vehicleIds = new ArrayList<>();

        while (rst.next()) {
            vehicleIds.add(rst.getString(1));
        }

        return vehicleIds;
    }

    @Override
    public ArrayList<String> getAllTypes() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select vehicle_type from vehicle");

        ArrayList<String> vehicleTypes = new ArrayList<>();

        while (rst.next()) {
            vehicleTypes.add(rst.getString(1));
        }

        return vehicleTypes;
    }

    @Override
    public ArrayList<String> getAllModels(String selectedVehicleType) throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select model from vehicle where vehicle_type = ?", selectedVehicleType);

        ArrayList<String> vehicleModels = new ArrayList<>();

        while (rst.next()) {
            vehicleModels.add(rst.getString(1));
        }

        return vehicleModels;
    }

    @Override
    public Vehicle findById(String selectedVehicleId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select * from vehicle where vehicle_id=?", selectedVehicleId);

        if (rst.next()) {
            return new Vehicle(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getBoolean(6)
            );
        }
        return null;
    }

    @Override
    public boolean updateList(String vehicleId, boolean status) throws SQLException, ClassNotFoundException {
        System.out.println("Vehicle is updated");
        return SqlUtil.execute(
                "update vehicle set availability_status = ? where vehicle_id = ?",
                status,
                vehicleId
        );
    }
}
