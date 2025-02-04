package lk.ijse.gdse.traveler.view.tdm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttractionTM {
    private String attractionId;
    private String name;
    private String type;
    private String operatingHours;
    private String description;
}
