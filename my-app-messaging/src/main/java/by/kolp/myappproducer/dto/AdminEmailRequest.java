package by.kolp.myappproducer.dto;

import lombok.Data;

@Data
public class AdminEmailRequest
{
    private String to;
    private String subject;
    private String message;
    private boolean isHtml = false;
}
