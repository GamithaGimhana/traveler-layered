package lk.ijse.gdse.traveler.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminDTO implements Serializable {
    private String adminId;
    private String name;
    private String email;
    private String contactNumber;
    private String username;
    private String password;
}
