package lk.ijse.gdse.traveler.dao;

import lk.ijse.gdse.traveler.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
//        if (daoFactory == null) {
//            daoFactory = new DAOFactory();
//        }
//        return daoFactory;
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOType {
        ACCOMODATION, ADMIN, ATTRACTION, CASHIER, DRIVER, FOOD, GUIDEASSIGNMENT, GUIDE, GUIDELANGUAGE, HEALTHCARE, LANGUAGE, PAYMENT, REQUEST, TRAVELER, TRIP, VEHICLE, VEHICLERENT, QUERY
    }

    public SuperDAO getDAO(DAOType type) {
        switch (type) {
            case ACCOMODATION:
                return new AccomodationDAOImpl();
            case ADMIN:
                return new AdminDAOImpl();
            case ATTRACTION:
                return new AttractionDAOImpl();
            case CASHIER:
                return new CashierDAOImpl();
            case DRIVER:
                return new DriverDAOImpl();
            case FOOD:
                return new FoodDAOImpl();
            case GUIDEASSIGNMENT:
                return new GuideAssignmentDAOImpl();
            case GUIDE:
                return new GuideDAOImpl();
            case GUIDELANGUAGE:
                return new GuideLanguagesDAOImpl();
            case HEALTHCARE:
                return new HealthcareDAOImpl();
            case LANGUAGE:
                return new LanguageDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case REQUEST:
                return new RequestDAOImpl();
            case TRAVELER:
                return new TravelerDAOImpl();
            case VEHICLE:
                return new VehicleDAOImpl();
            case VEHICLERENT:
                return new VehicleRentDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            default:
                return null;
        }
    }
}
