package by.kolp.myappdataapi.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ErrorDto(String error, @JsonProperty("error_description") String errorDescription) {
}
