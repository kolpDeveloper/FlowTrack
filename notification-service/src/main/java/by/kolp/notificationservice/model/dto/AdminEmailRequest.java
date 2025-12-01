package by.kolp.notificationservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminEmailRequest
{
    private Page<String> to;
    private String subject;
    private String message;
}
