package lk.ijse.gdse.traveler.model;

import lk.ijse.gdse.traveler.dto.DriverDTO;
import lk.ijse.gdse.traveler.dao.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriverModel {

    public String getNextDriverId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select driver_id from driver order by driver_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // last id
            String substring = lastId.substring(1); // extract numbers
            int i = Integer.parseInt(substring); // convert the numbers part to int
            int newIdIndex = i + 1; // increment
            return String.format("D%03d", newIdIndex); // return the new id in string
        }
        return "D001"; // return the default ID
    }

    public boolean saveDriver(DriverDTO driverDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into driver values (?,?,?,?,?)",
                driverDTO.getDriverId(),
                driverDTO.getName(),
                driverDTO.getLicenseNumber(),
                driverDTO.getContactNumber(),
                driverDTO.isAvailabilityStatus()
        );
    }

    public ArrayList<DriverDTO> getAllDrivers() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from driver");

        ArrayList<DriverDTO> driverDTOS = new ArrayList<>();

        while (rst.next()) {
            DriverDTO driverDTO = new DriverDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getBoolean(5)
            );
            driverDTOS.add(driverDTO);
        }
        return driverDTOS;
    }

    public boolean updateDriver(DriverDTO driverDTO) throws SQLException {
        return CrudUtil.execute(
                "update driver set name=?, license_number=?, contact_number=?, availability_status=? where driver_id=?",
                driverDTO.getName(),
                driverDTO.getLicenseNumber(),
                driverDTO.getContactNumber(),
                driverDTO.isAvailabilityStatus(),
                driverDTO.getDriverId()
        );
    }

    public boolean deleteDriver(String driverId) throws SQLException {
        return CrudUtil.execute("delete from driver where driver_id=?", driverId);
    }

    public ArrayList<String> getAllDriverIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select driver_id from driver where availability_status=true");

        ArrayList<String> driverIds = new ArrayList<>();

        while (rst.next()) {
            driverIds.add(rst.getString(1));
        }

        return driverIds;
    }

    public DriverDTO findById(String selectedDriverId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from driver where driver_id=?", selectedDriverId);

        if (rst.next()) {
            return new DriverDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getBoolean(5)
            );
        }
        return null;
    }

    public boolean updateDriverList(String driverId, boolean status) throws SQLException {
        System.out.println("Driver is updated");
        return CrudUtil.execute(
                "update driver set availability_status = ? where driver_id = ?",
                status,
                driverId
        );
    }
}
