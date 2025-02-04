package lk.ijse.gdse.traveler.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Traveler implements Serializable {
    private String travelerId;
    private String name;
    private String email;
    private String contactNumber;
    private String nationality;
    private String idNumber;
}
