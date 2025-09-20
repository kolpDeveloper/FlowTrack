package by.kolp.myappweb.dto;

import lombok.Builder;

@Builder
public record CategoryDTO(Integer id, String name) {
}
