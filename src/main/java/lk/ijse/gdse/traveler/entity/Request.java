package lk.ijse.gdse.traveler.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Request {
    private String requestId;
    private String travelerId;
    private Date requestDate;
    private String requestType;
    private String cashierId;
}
