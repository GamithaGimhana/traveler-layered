package lk.ijse.gdse.traveler.bo.custom.impl;

import lk.ijse.gdse.traveler.bo.custom.AccomodationBO;
import lk.ijse.gdse.traveler.dao.DAOFactory;
import lk.ijse.gdse.traveler.dao.custom.AccomodationDAO;
import lk.ijse.gdse.traveler.dao.custom.impl.AccomodationDAOImpl;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;
import lk.ijse.gdse.traveler.entity.Accomodation;

import java.sql.SQLException;
import java.util.ArrayList;

public class AccomodationBOImpl implements AccomodationBO {
//    AccomodationDAO accomodationDAO = new AccomodationDAOImpl();
    AccomodationDAO accomodationDAO = (AccomodationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ACCOMODATION);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return accomodationDAO.getNextId();
    }

    @Override
    public boolean save(AccomodationDTO accomodationDTO) throws SQLException, ClassNotFoundException {
        return accomodationDAO.save(new Accomodation(accomodationDTO.getAccomodationId(),accomodationDTO.getName(),accomodationDTO.getType(),accomodationDTO.getContactNumber()));
    }

    @Override
    public ArrayList<AccomodationDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<AccomodationDTO> accomodationDTOS = new ArrayList<>();
        ArrayList<Accomodation> accomodations = accomodationDAO.getAll();
        for (Accomodation accomodation : accomodations) {
            accomodationDTOS.add(new AccomodationDTO(accomodation.getAccomodationId(),accomodation.getName(),accomodation.getType(),accomodation.getContactNumber()));
        }
        return accomodationDTOS;
    }

    @Override
    public boolean update(AccomodationDTO accomodationDTO) throws SQLException, ClassNotFoundException {
        return accomodationDAO.update(new Accomodation(accomodationDTO.getAccomodationId(),accomodationDTO.getName(),accomodationDTO.getType(),accomodationDTO.getContactNumber()));

    }

    @Override
    public boolean delete(String accommodationId) throws SQLException, ClassNotFoundException {
        return accomodationDAO.delete(accommodationId);
    }
}
