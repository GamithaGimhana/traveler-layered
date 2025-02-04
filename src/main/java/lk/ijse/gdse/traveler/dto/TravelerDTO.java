package lk.ijse.gdse.traveler.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TravelerDTO implements Serializable {
    private String travelerId;
    private String name;
    private String email;
    private String contactNumber;
    private String nationality;
    private String idNumber;
}
