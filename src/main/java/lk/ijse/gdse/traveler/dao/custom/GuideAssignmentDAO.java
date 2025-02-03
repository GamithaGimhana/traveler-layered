package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.entity.GuideAssignment;

import java.sql.SQLException;

public interface GuideAssignmentDAO extends CrudDAO<GuideAssignment> {
//    public boolean saveGuideAssignment(GuideAssignmentDTO guideAssignmentDTO) throws SQLException;
    public boolean checkRequestIdExists(String requestId) throws SQLException;
}
