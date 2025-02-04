package lk.ijse.gdse.traveler.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CashierDTO implements Serializable {
    private String cashierId;
    private String name;
    private String email;
    private String contactNumber;
    private String adminId;
    private String username;
    private String password;
}
