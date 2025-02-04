package lk.ijse.gdse.traveler.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccomodationDTO implements Serializable {
    private String accomodationId;
    private String name;
    private String type;
    private String contactNumber;
}
