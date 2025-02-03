package lk.ijse.gdse.traveler.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Healthcare {
    private String healthcareId;
    private String name;
    private String contact;
    private boolean emergency;
}
