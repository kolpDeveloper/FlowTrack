package by.kolp.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AckDTO {

    private String message;
    private Boolean answer;

    public static AckDTO makeDefault(Boolean answer) {
                    return builder()
                            .answer(answer)
                            .build();
            }
}
