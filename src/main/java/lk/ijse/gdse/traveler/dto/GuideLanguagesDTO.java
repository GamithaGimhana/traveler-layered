package lk.ijse.gdse.traveler.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GuideLanguagesDTO implements Serializable {
    private String langId;
    private String guideId;
}
