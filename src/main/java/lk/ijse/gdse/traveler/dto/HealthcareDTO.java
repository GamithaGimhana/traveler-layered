package lk.ijse.gdse.traveler.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HealthcareDTO {
    private String healthcareId;
    private String name;
    private String contact;
    private boolean emergency;
}
