package lk.ijse.gdse.traveler.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GuideDTO {
    private String guideId;
    private String name;
    private String licenseNumber;
    private String contactNumber;
    private boolean availabilityStatus;
}
