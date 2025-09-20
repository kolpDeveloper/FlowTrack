package by.kolp.myappweb.dto;


import lombok.Builder;


@Builder
public record AckDTO(String message, Boolean answer) {
}
