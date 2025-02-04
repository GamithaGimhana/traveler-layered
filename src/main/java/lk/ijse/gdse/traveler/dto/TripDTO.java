package lk.ijse.gdse.traveler.dto;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TripDTO implements Serializable {
    private String tripId;
    private String requestId;
    private String guideId;
    private String driverId;
    private String vehicleId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double cost;
    private boolean tripStatus;
}
