package lk.ijse.gdse.traveler.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GuideLanguages implements Serializable {
    private String langId;
    private String guideId;
}
