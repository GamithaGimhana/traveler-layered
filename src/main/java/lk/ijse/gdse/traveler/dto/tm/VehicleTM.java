package lk.ijse.gdse.traveler.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleTM {
    private String vehicleId;
    private String vehicleType;
    private String model;
    private String licensePlateNumber;
    private double dailyPrice;
    private boolean availabilityStatus;
}
