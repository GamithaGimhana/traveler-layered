package lk.ijse.gdse.traveler.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HealthcareDTO implements Serializable {
    private String healthcareId;
    private String name;
    private String contact;
    private boolean emergency;
}
