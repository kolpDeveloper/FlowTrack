package by.kolp.myappcore.model.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryNameDTO(@NotBlank String name) {
}
