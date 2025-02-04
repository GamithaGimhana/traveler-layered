package lk.ijse.gdse.traveler.bo.custom.impl;

import lk.ijse.gdse.traveler.bo.custom.RequestBO;
import lk.ijse.gdse.traveler.dao.custom.RequestDAO;
import lk.ijse.gdse.traveler.dao.custom.impl.RequestDAOImpl;
import lk.ijse.gdse.traveler.dto.AccomodationDTO;
import lk.ijse.gdse.traveler.dto.LanguageDTO;
import lk.ijse.gdse.traveler.dto.PaymentDTO;
import lk.ijse.gdse.traveler.dto.RequestDTO;
import lk.ijse.gdse.traveler.entity.Language;
import lk.ijse.gdse.traveler.entity.Payment;
import lk.ijse.gdse.traveler.entity.Request;

import java.sql.SQLException;
import java.util.ArrayList;

public class RequestBOImpl implements RequestBO {
    RequestDAO requestDAO = new RequestDAOImpl();
    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return requestDAO.getNextId();
    }

    @Override
    public boolean save(RequestDTO requestDTO) throws SQLException, ClassNotFoundException {
        return requestDAO.save(new Request(requestDTO.getRequestId(), requestDTO.getTravelerId(), requestDTO.getRequestDate(), requestDTO.getRequestType(), requestDTO.getCashierId()));
    }

    @Override
    public ArrayList<RequestDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<RequestDTO> requestDTOS = new ArrayList<>();
        ArrayList<Request> requests = requestDAO.getAll();
        for (Request request: requests) {
            requestDTOS.add(new RequestDTO(request.getRequestId(), request.getTravelerId(), request.getRequestDate(), request.getRequestType(), request.getCashierId()));
        }
        return requestDTOS;
    }

    @Override
    public boolean delete(String requestId) throws SQLException, ClassNotFoundException {
        return requestDAO.delete(requestId);
    }

    @Override
    public ArrayList<String> getAllIds(String selectedTravelerId) throws SQLException, ClassNotFoundException {
        return requestDAO.getAllIds(selectedTravelerId);
    }

    @Override
    public RequestDTO findById(String selectedRequestId) throws SQLException, ClassNotFoundException {
        Request request = requestDAO.findById(selectedRequestId);
        return new RequestDTO(request.getRequestId(), request.getTravelerId(), request.getRequestDate(), request.getRequestType(), request.getCashierId());
    }

}
