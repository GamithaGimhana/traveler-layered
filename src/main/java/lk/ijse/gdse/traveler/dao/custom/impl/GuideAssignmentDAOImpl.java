package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.GuideAssignmentDAO;
import lk.ijse.gdse.traveler.db.DBConnection;
import lk.ijse.gdse.traveler.dto.GuideAssignmentDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuideAssignmentDAOImpl implements GuideAssignmentDAO {
    private final GuideDAOImpl guideDaoImpl = new GuideDAOImpl();

    public boolean saveGuideAssignment(GuideAssignmentDTO guideAssignmentDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            // @autoCommit: Disables auto-commit to manually control the transaction
            connection.setAutoCommit(false); // 1

            // @isOrderSaved: Saves the order details into the orders table
            boolean isGuideAssignment = SqlUtil.execute(
                    "insert into guide_assignment values (?,?,?,?,?,?)",
                    guideAssignmentDTO.getRequestId(),
                    guideAssignmentDTO.getGuideId(),
                    guideAssignmentDTO.getTravelerId(),
                    guideAssignmentDTO.getStartDate(),
                    guideAssignmentDTO.getEndDate(),
                    guideAssignmentDTO.isStatus()
            );
            // If the order is saved successfully
            if (isGuideAssignment) {
                System.out.println("Guide Assignment Saved");
                // @isOrderDetailListSaved: Saves the list of order details
                boolean isGuideUpdated = guideDaoImpl.updateGuideList(guideAssignmentDTO.getGuideId(), guideAssignmentDTO.isStatus());
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

    public boolean checkRequestIdExists(String requestId) throws SQLException {
        String query = "SELECT COUNT(*) FROM request WHERE request_id = ?";
        ResultSet rs = SqlUtil.execute(query, requestId);
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }
}
