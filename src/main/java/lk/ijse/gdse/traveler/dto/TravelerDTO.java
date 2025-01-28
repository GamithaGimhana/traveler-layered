package lk.ijse.gdse.traveler.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TravelerDTO {
    private String travelerId;
    private String name;
    private String email;
    private String contactNumber;
    private String nationality;
    private String idNumber;
}
