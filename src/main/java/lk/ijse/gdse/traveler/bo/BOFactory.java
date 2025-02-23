package lk.ijse.gdse.traveler.bo;

import lk.ijse.gdse.traveler.bo.custom.impl.*;
import lk.ijse.gdse.traveler.dao.custom.impl.*;

public class BOFactory {
    public static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getInstance() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public enum BOType {
        ACCOMODATION, ADMIN, ATTRACTION, CASHIER, DRIVER, FOOD, GUIDEASSIGNMENT, GUIDE, GUIDELANGUAGE, HEALTHCARE, LANGUAGE, PAYMENT, REQUEST, TRAVELER, TRIP, VEHICLE, VEHICLERENT
    }

    public SuperBO getBO(BOType type) {
        switch (type) {
            case ACCOMODATION:
                return new AccomodationBOImpl();
            case ADMIN:
                return new AdminBOImpl();
            case ATTRACTION:
                return new AttractionBOImpl();
            case CASHIER:
                return new CashierBOImpl();
            case DRIVER:
                return new DriverBOImpl();
            case FOOD:
                return new FoodBOImpl();
            case GUIDEASSIGNMENT:
                return new GuideAssignmentBOImpl();
            case GUIDE:
                return new GuideBOImpl();
            case GUIDELANGUAGE:
                return new GuideLanguagesBOImpl();
            case HEALTHCARE:
                return new HealthcareBOImpl();
            case LANGUAGE:
                return new LanguageBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case REQUEST:
                return new RequestBOImpl();
            case TRAVELER:
                return new TravelerBOImpl();
            case VEHICLE:
                return new VehicleBOImpl();
            case VEHICLERENT:
                return new VehicleRentBOImpl();
            default:
                return null;
        }
    }
}
