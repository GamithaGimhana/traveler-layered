package lk.ijse.gdse.traveler.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminDTO {
    private String adminId;
    private String name;
    private String email;
    private String contactNumber;
    private String username;
    private String password;
}
