package lk.ijse.gdse.traveler.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DriverTM {
    private String driverId;
    private String name;
    private String licenseNumber;
    private String contactNumber;
    private boolean availabilityStatus;
}
