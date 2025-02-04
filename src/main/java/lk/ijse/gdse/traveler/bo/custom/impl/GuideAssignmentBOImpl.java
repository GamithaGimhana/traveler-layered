package lk.ijse.gdse.traveler.bo.custom.impl;

import lk.ijse.gdse.traveler.bo.custom.GuideAssignmentBO;
import lk.ijse.gdse.traveler.dao.custom.GuideAssignmentDAO;
import lk.ijse.gdse.traveler.dao.custom.impl.GuideAssignmentDAOImpl;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;
import lk.ijse.gdse.traveler.dto.GuideAssignmentDTO;
import lk.ijse.gdse.traveler.entity.Food;
import lk.ijse.gdse.traveler.entity.GuideAssignment;

import java.sql.SQLException;
import java.util.ArrayList;

public class GuideAssignmentBOImpl implements GuideAssignmentBO {
    GuideAssignmentDAO guideAssignmentDAO = new GuideAssignmentDAOImpl();

    @Override
    public boolean save(GuideAssignmentDTO guideAssignmentDTO) throws SQLException, ClassNotFoundException {
        return guideAssignmentDAO.save(new GuideAssignment(guideAssignmentDTO.getRequestId(), guideAssignmentDTO.getGuideId(), guideAssignmentDTO.getTravelerId(), guideAssignmentDTO.getStartDate(), guideAssignmentDTO.getEndDate(), guideAssignmentDTO.isStatus()));
    }

    @Override
    public boolean checkRequestIdExists(String requestId) throws SQLException, ClassNotFoundException {
        return guideAssignmentDAO.checkRequestIdExists(requestId);
    }
}
