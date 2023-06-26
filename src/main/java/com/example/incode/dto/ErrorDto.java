package com.example.incode.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class ErrorDto {
    private final String error;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorDto errorDto = (ErrorDto) o;
        return Objects.equals(error, errorDto.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error);
    }
}
