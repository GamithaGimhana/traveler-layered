package lk.ijse.gdse.traveler.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleRentDTO implements Serializable {
    private String requestId;
    private String travelerId;
    private String vehicleId;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private double rentalCost;
    private boolean vRentalStatus;
}
