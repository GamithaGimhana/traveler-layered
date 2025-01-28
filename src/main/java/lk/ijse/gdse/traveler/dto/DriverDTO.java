package lk.ijse.gdse.traveler.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DriverDTO {
    private String driverId;
    private String name;
    private String licenseNumber;
    private String contactNumber;
    private boolean availabilityStatus;
}
