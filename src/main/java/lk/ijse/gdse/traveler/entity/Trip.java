package lk.ijse.gdse.traveler.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Trip implements Serializable {
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
