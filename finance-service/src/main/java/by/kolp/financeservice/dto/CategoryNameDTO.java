package by.kolp.financeservice.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryNameDTO(@NotBlank String name) {
}
