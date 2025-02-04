package lk.ijse.gdse.traveler.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vehicle implements Serializable {
    private String vehicleId;
    private String vehicleType;
    private String model;
    private String licensePlateNumber;
    private double dailyPrice;
    private boolean availabilityStatus;
}
