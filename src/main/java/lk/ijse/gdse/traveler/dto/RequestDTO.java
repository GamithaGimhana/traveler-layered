package lk.ijse.gdse.traveler.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestDTO {
    private String requestId;
    private String travelerId;
    private Date requestDate;
    private String requestType;
    private String cashierId;
}
