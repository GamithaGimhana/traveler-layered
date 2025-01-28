module lk.ijse.gdse.traveler {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;
    requires java.mail;

    opens lk.ijse.gdse.traveler.dto.tm to javafx.base;
    opens lk.ijse.gdse.traveler.controller to javafx.fxml;
    exports lk.ijse.gdse.traveler;
}