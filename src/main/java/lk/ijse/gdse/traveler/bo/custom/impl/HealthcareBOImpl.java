package lk.ijse.gdse.traveler.bo.custom.impl;

import lk.ijse.gdse.traveler.bo.custom.HealthcareBO;
import lk.ijse.gdse.traveler.dao.DAOFactory;
import lk.ijse.gdse.traveler.dao.custom.GuideDAO;
import lk.ijse.gdse.traveler.dao.custom.HealthcareDAO;
import lk.ijse.gdse.traveler.dao.custom.impl.HealthcareDAOImpl;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;
import lk.ijse.gdse.traveler.dto.GuideLanguagesDTO;
import lk.ijse.gdse.traveler.dto.HealthcareDTO;
import lk.ijse.gdse.traveler.entity.GuideLanguages;
import lk.ijse.gdse.traveler.entity.Healthcare;

import java.sql.SQLException;
import java.util.ArrayList;

public class HealthcareBOImpl implements HealthcareBO {
//    HealthcareDAO healthcareDAO = new HealthcareDAOImpl();
    HealthcareDAO healthcareDAO = (HealthcareDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.HEALTHCARE);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return healthcareDAO.getNextId();
    }

    @Override
    public boolean save(HealthcareDTO healthcareDTO) throws SQLException, ClassNotFoundException {
        return healthcareDAO.save(new Healthcare(healthcareDTO.getHealthcareId(), healthcareDTO.getName(), healthcareDTO.getContact(), healthcareDTO.isEmergency()));
    }

    @Override
    public ArrayList<HealthcareDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<HealthcareDTO> healthcareDTOS = new ArrayList<>();
        ArrayList<Healthcare> healthcares = healthcareDAO.getAll();
        for (Healthcare healthcare: healthcares) {
            healthcareDTOS.add(new HealthcareDTO(healthcare.getHealthcareId(), healthcare.getName(), healthcare.getContact(), healthcare.isEmergency()));
        }
        return healthcareDTOS;
    }

    @Override
    public boolean update(HealthcareDTO healthcareDTO) throws SQLException, ClassNotFoundException {
        return healthcareDAO.update(new Healthcare(healthcareDTO.getHealthcareId(), healthcareDTO.getName(), healthcareDTO.getContact(), healthcareDTO.isEmergency()));
    }

    @Override
    public boolean delete(String healthcareId) throws SQLException, ClassNotFoundException {
        return healthcareDAO.delete(healthcareId);
    }

}
