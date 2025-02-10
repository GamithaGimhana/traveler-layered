package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.DriverDAO;
import lk.ijse.gdse.traveler.entity.Admin;
import lk.ijse.gdse.traveler.entity.Driver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriverDAOImpl implements DriverDAO {

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select driver_id from driver order by driver_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // last id
            String substring = lastId.substring(1); // extract numbers
            int i = Integer.parseInt(substring); // convert the numbers part to int
            int newIdIndex = i + 1; // increment
            return String.format("D%03d", newIdIndex); // return the new id in string
        }
        return "D001"; // return the default ID
    }

    @Override
    public boolean save(Driver driver) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute(
                "insert into driver values (?,?,?,?,?)",
                driver.getDriverId(),
                driver.getName(),
                driver.getLicenseNumber(),
                driver.getContactNumber(),
                driver.isAvailabilityStatus()
        );
    }

    @Override
    public ArrayList<Driver> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select * from driver");

        ArrayList<Driver> drivers = new ArrayList<>();

        while (rst.next()) {
            Driver driver = new Driver(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getBoolean(5)
            );
            drivers.add(driver);
        }
        return drivers;
    }

    @Override
    public boolean update(Driver driver) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute(
                "update driver set name=?, license_number=?, contact_number=?, availability_status=? where driver_id=?",
                driver.getName(),
                driver.getLicenseNumber(),
                driver.getContactNumber(),
                driver.isAvailabilityStatus(),
                driver.getDriverId()
        );
    }

    @Override
    public boolean delete(String driverId) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute("delete from driver where driver_id=?", driverId);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select driver_id from driver where availability_status=true");

        ArrayList<String> driverIds = new ArrayList<>();

        while (rst.next()) {
            driverIds.add(rst.getString(1));
        }

        return driverIds;
    }

    @Override
    public Driver findById(String selectedDriverId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select * from driver where driver_id=?", selectedDriverId);

//        if (rst.next()) {
//            return new Driver(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getString(4),
//                    rst.getBoolean(5)
//            );
//        }
//        return null;
        rst.next();

        Driver driver = new Driver(selectedDriverId, rst.getString("name"), rst.getString("license_number"), rst.getString("contact_number"), rst.getBoolean("availability_status"));
        return driver;
    }

    @Override
    public boolean updateDriverList(String driverId, boolean status) throws SQLException, ClassNotFoundException {
        System.out.println("Driver is updated");
        return SqlUtil.execute(
                "update driver set availability_status = ? where driver_id = ?",
                status,
                driverId
        );
    }
}
