package lk.ijse.gdse.traveler.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Food implements Serializable {
    private String foodServiceId;
    private String name;
    private String type;
    private String contact;
}
