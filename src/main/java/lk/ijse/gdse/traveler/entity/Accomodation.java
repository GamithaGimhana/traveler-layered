package lk.ijse.gdse.traveler.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Accomodation implements Serializable {
    private String accomodationId;
    private String name;
    private String type;
    private String contactNumber;
}
