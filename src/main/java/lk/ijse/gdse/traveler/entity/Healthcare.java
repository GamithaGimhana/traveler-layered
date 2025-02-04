package lk.ijse.gdse.traveler.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Healthcare implements Serializable {
    private String healthcareId;
    private String name;
    private String contact;
    private boolean emergency;
}
