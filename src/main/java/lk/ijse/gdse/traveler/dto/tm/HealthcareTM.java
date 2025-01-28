package lk.ijse.gdse.traveler.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HealthcareTM {
    private String healthcareId;
    private String name;
    private String contact;
    private boolean emergency;
}
