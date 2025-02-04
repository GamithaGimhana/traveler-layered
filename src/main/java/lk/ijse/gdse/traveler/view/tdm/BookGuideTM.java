package lk.ijse.gdse.traveler.view.tdm;

import javafx.scene.control.Button;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookGuideTM {
    private String requestId;
    private String travelerId;
    private String guideId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Button removeBtn;
}
