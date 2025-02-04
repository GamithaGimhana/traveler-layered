package lk.ijse.gdse.traveler.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FoodDTO implements Serializable {
    private String foodServiceId;
    private String name;
    private String type;
    private String contact;
}
