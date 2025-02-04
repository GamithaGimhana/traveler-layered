package lk.ijse.gdse.traveler.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Attraction implements Serializable {
    private String attractionId;
    private String name;
    private String type;
    private String operatingHours;
    private String description;
}
