package lk.ijse.gdse.traveler.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TravelerTM {
    private String travelerId;
    private String name;
    private String email;
    private String contactNumber;
    private String nationality;
    private String idNumber;
}
