package lk.ijse.gdse.traveler.bo.custom;

import lk.ijse.gdse.traveler.bo.SuperBO;
import lk.ijse.gdse.traveler.dao.CrudDAO;
import lk.ijse.gdse.traveler.dto.GuideAssignmentDTO;
import lk.ijse.gdse.traveler.entity.GuideAssignment;

import java.sql.SQLException;

public interface GuideAssignmentBO extends SuperBO {
    public boolean save(GuideAssignmentDTO guideAssignmentDTO) throws SQLException, ClassNotFoundException;
    public boolean checkRequestIdExists(String requestId) throws SQLException, ClassNotFoundException;
}
