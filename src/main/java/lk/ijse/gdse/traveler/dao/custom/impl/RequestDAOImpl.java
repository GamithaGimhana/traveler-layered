package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.RequestDAO;
import lk.ijse.gdse.traveler.entity.GuideLanguages;
import lk.ijse.gdse.traveler.entity.Request;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RequestDAOImpl implements RequestDAO {
    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select request_id from request order by request_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // last id
            String substring = lastId.substring(1); // extract numbers
            int i = Integer.parseInt(substring); // convert the numbers part to int
            int newIdIndex = i + 1; // increment
            return String.format("R%03d", newIdIndex); // return the new id in string
        }
        return "R001"; // return the default ID
    }

    @Override
    public boolean save(Request request) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute(
                "insert into request values (?,?,?,?,?)",
                request.getRequestId(),
                request.getTravelerId(),
                request.getRequestDate(),
                request.getRequestType(),
                request.getCashierId()
        );
    }

    @Override
    public ArrayList<Request> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select * from request");

        ArrayList<Request> requests = new ArrayList<>();

        while (rst.next()) {
            Request request = new Request(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            requests.add(request);
        }
        return requests;
    }

    @Override
    public boolean update(Request dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String requestId) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute("delete from request where request_id=?", requestId);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return null;
    }

    @Override
    public ArrayList<String> getAllIds(String selectedTravelerId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select request_id from request where traveler_id=?", selectedTravelerId);

        ArrayList<String> requestIds = new ArrayList<>();

        while (rst.next()) {
            requestIds.add(rst.getString(1));
        }

        return requestIds;
    }

    @Override
    public Request findById(String selectedRequestId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select * from request where request_id=?", selectedRequestId);

//        if (rst.next()) {
//            return new Request(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getDate(3),
//                    rst.getString(4),
//                    rst.getString(5)
//            );
//        }
//        return null;

        rst.next();

        Request request = new Request(selectedRequestId, rst.getString("traveler_id"), rst.getDate("request_date"), rst.getString("request_type"), rst.getString("cashier_id"));
        return request;
    }

}
