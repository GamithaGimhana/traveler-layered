package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.GuideAssignmentDAO;
import lk.ijse.gdse.traveler.db.DBConnection;
import lk.ijse.gdse.traveler.entity.GuideAssignment;
import lk.ijse.gdse.traveler.entity.GuideLanguages;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuideAssignmentDAOImpl implements GuideAssignmentDAO {
    private final GuideDAOImpl guideDaoImpl = new GuideDAOImpl();

    @Override
    public String getNextId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(GuideAssignment guideAssignment) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            // @autoCommit: Disables auto-commit to manually control the transaction
            connection.setAutoCommit(false); // 1

            // @isOrderSaved: Saves the order details into the orders table
            boolean isGuideAssignment = SqlUtil.execute(
                    "insert into guide_assignment values (?,?,?,?,?,?)",
                    guideAssignment.getRequestId(),
                    guideAssignment.getGuideId(),
                    guideAssignment.getTravelerId(),
                    guideAssignment.getStartDate(),
                    guideAssignment.getEndDate(),
                    guideAssignment.isStatus()
            );
            // If the order is saved successfully
            if (isGuideAssignment) {
                System.out.println("Guide Assignment Saved");
                // @isOrderDetailListSaved: Saves the list of order details
                boolean isGuideUpdated = guideDaoImpl.updateList(guideAssignment.getGuideId(), guideAssignment.isStatus());
                if (isGuideUpdated) {
                    System.out.println("Guide Updated");
                    // @commit: Commits the transaction if both order and details are saved successfully
                    connection.commit(); // 2
                    return true;
                }
            }
            // @rollback: Rolls back the transaction if order details saving fails
            connection.rollback(); // 3
            return false;
        } catch (Exception e) {
            // @catch: Rolls back the transaction in case of any exception
            connection.rollback();
            return false;
        } finally {
            // @finally: Resets auto-commit to true after the operation
            connection.setAutoCommit(true); // 4
        }
    }

    @Override
    public ArrayList<GuideAssignment> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean update(GuideAssignment guideAssignment) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return null;
    }

    @Override
    public GuideAssignment findById(String selectedId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("select * from guide_assignment where request_id=?", selectedId);

        rst.next();

        GuideAssignment guideAssignment = new GuideAssignment(selectedId, rst.getString("guide_id"), rst.getString("traveler_id"), rst.getDate("start_date").toLocalDate(), rst.getDate("end_date").toLocalDate(), rst.getBoolean("gassignment_status"));
        return guideAssignment;
    }

    @Override
    public boolean checkRequestIdExists(String requestId) throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM request WHERE request_id = ?";
        ResultSet rs = SqlUtil.execute(query, requestId);
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }
}
