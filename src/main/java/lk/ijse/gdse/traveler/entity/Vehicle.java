package lk.ijse.gdse.traveler.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vehicle {
    private String vehicleId;
    private String vehicleType;
    private String model;
    private String licensePlateNumber;
    private double dailyPrice;
    private boolean availabilityStatus;
}
