package lk.ijse.gdse.traveler.view.tdm;

import javafx.scene.control.Button;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookTripTM {
    private String tripId;
    private String requestId;
    private String travelerId;
    private String guideId;
    private String vehicleId;
    private String driverId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double cost;
    private Button removeBtn;
}
