package lk.ijse.gdse.traveler.dao.custom;

import lk.ijse.gdse.traveler.dto.GuideAssignmentDTO;

import java.sql.SQLException;

public interface GuideAssignmentDAO {
    public boolean saveGuideAssignment(GuideAssignmentDTO guideAssignmentDTO) throws SQLException;
    public boolean checkRequestIdExists(String requestId) throws SQLException;
}
