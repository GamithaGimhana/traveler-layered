package lk.ijse.gdse.traveler.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttractionDTO {
    private String attractionId;
    private String name;
    private String type;
    private String operatingHours;
    private String description;
}
