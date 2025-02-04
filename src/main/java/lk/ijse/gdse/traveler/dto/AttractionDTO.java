package lk.ijse.gdse.traveler.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttractionDTO implements Serializable {
    private String attractionId;
    private String name;
    private String type;
    private String operatingHours;
    private String description;
}
