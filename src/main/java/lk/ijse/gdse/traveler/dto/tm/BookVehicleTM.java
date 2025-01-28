package lk.ijse.gdse.traveler.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookVehicleTM {
    private String requestId;
    private String travelerId;
    private String vehicleId;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private double cost;
    private Button removeBtn;
}
