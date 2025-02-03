package lk.ijse.gdse.traveler.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Accomodation {
    private String accomodationId;
    private String name;
    private String type;
    private String contactNumber;
}
