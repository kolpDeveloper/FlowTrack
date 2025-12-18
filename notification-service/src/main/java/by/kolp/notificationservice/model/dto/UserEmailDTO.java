package by.kolp.notificationservice.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserEmailDTO<T> implements Serializable {
    private List<T> content;
    private int size;
    private int totalElements;
}
