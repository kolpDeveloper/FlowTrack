package by.kolp.notificationservice.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserEmailDTO<T> implements Serializable {

    @NotEmpty
    private List<T> content;

    @NotNull
    private int size;

    @NotNull
    private int totalElements;
}
