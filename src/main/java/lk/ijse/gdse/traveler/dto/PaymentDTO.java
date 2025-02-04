package lk.ijse.gdse.traveler.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDTO implements Serializable {
    private String paymentId;
    private String travelerId;
    private String tripId;
    private double amount;
    private double remaining;
    private LocalDate paymentDate;
    private String paymentMethod;
}
