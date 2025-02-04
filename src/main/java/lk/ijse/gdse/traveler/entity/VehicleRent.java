package lk.ijse.gdse.traveler.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleRent implements Serializable {
    private String requestId;
    private String travelerId;
    private String vehicleId;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private double rentalCost;
    private boolean vRentalStatus;
}
