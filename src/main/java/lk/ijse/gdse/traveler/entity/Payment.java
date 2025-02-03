package lk.ijse.gdse.traveler.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Payment {
    private String paymentId;
    private String travelerId;
    private String tripId;
    private double amount;
    private double remaining;
    private LocalDate paymentDate;
    private String paymentMethod;
}
