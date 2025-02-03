package lk.ijse.gdse.traveler.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Guide {
    private String guideId;
    private String name;
    private String licenseNumber;
    private String contactNumber;
    private boolean availabilityStatus;
}
