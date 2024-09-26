package com.danielks.Students_Notes.entities.dtos;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record StudentDTO(
        UUID id,
        @NotEmpty(message = "name is blank") String name,
        @NotEmpty(message = "age is blank") int age,
        List<Double> notes
        ) {
}
