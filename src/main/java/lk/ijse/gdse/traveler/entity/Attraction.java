package lk.ijse.gdse.traveler.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Attraction {
    private String attractionId;
    private String name;
    private String type;
    private String operatingHours;
    private String description;
}
