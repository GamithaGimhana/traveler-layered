package lk.ijse.gdse.traveler.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleRent {
    private String requestId;
    private String travelerId;
    private String vehicleId;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private double rentalCost;
    private boolean vRentalStatus;
}
