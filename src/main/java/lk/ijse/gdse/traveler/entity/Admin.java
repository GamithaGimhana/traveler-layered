package lk.ijse.gdse.traveler.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Admin implements Serializable {
    private String adminId;
    private String name;
    private String email;
    private String contactNumber;
    private String username;
    private String password;
}
