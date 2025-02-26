package lk.ijse.gdse.traveler.dto;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GuideAssignmentDTO implements Serializable {
    private String requestId;
    private String guideId;
    private String travelerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean status;
}
