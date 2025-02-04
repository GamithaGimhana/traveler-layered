package lk.ijse.gdse.traveler.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GuideAssignment implements Serializable {
    private String requestId;
    private String guideId;
    private String travelerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean status;
}
