package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.VehicleDAO;
import lk.ijse.gdse.traveler.dto.VehicleDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleDAOImpl implements VehicleDAO {

    public String getNextVehicleId() throws SQLException {
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

    public boolean saveVehicle(VehicleDTO vehicleDTO) throws SQLException {
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

    public ArrayList<VehicleDTO> getAllVehicles() throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from vehicle");

        ArrayList<VehicleDTO> vehicleDTOS = new ArrayList<>();

        while (rst.next()) {
            VehicleDTO vehicleDTO = new VehicleDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getBoolean(6)
            );
            vehicleDTOS.add(vehicleDTO);
        }
        return vehicleDTOS;
    }

    public boolean updateVehicle(VehicleDTO vehicleDTO) throws SQLException {
        return SqlUtil.execute(
                "update vehicle set vehicle_type=?, model=?, license_plate_number=?, daily_price=?, availability_status=? where vehicle_id=?",
                vehicleDTO.getVehicleType(),
                vehicleDTO.getModel(),
                vehicleDTO.getLicensePlateNumber(),
                vehicleDTO.getDailyPrice(),
                vehicleDTO.isAvailabilityStatus(),
                vehicleDTO.getVehicleId()
        );
    }

    public boolean deleteVehicle(String vehicleId) throws SQLException {
        return SqlUtil.execute("delete from vehicle where vehicle_id=?", vehicleId);
    }

    public ArrayList<String> getAllVehicleIds(String selectedVehicleModel) throws SQLException {
        ResultSet rst = SqlUtil.execute("select vehicle_id from vehicle where model=? and availability_status=true", selectedVehicleModel);

        ArrayList<String> vehicleIds = new ArrayList<>();

        while (rst.next()) {
            vehicleIds.add(rst.getString(1));
        }

        return vehicleIds;
    }

    public ArrayList<String> getAllVehicleTypes() throws SQLException {
        ResultSet rst = SqlUtil.execute("select vehicle_type from vehicle");

        ArrayList<String> vehicleTypes = new ArrayList<>();

        while (rst.next()) {
            vehicleTypes.add(rst.getString(1));
        }

        return vehicleTypes;
    }

    public ArrayList<String> getAllVehicleModels(String selectedVehicleType) throws SQLException {
        ResultSet rst = SqlUtil.execute("select model from vehicle where vehicle_type = ?", selectedVehicleType);

        ArrayList<String> vehicleModels = new ArrayList<>();

        while (rst.next()) {
            vehicleModels.add(rst.getString(1));
        }

        return vehicleModels;
    }

    public VehicleDTO findById(String selectedVehicleId) throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from vehicle where vehicle_id=?", selectedVehicleId);

        if (rst.next()) {
            return new VehicleDTO(
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

    public boolean updateVehicleList(String vehicleId, boolean status) throws SQLException {
        System.out.println("Vehicle is updated");
        return SqlUtil.execute(
                "update vehicle set availability_status = ? where vehicle_id = ?",
                status,
                vehicleId
        );
    }
}
