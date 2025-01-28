package lk.ijse.gdse.traveler.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleDTO {
    private String vehicleId;
    private String vehicleType;
    private String model;
    private String licensePlateNumber;
    private double dailyPrice;
    private boolean availabilityStatus;
}
