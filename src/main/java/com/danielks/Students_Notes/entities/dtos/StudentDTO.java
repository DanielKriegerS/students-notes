package com.danielks.Students_Notes.entities.dtos;

import com.danielks.Students_Notes.entities.Course;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record StudentDTO(
        UUID id,
        @NotEmpty(message = "name is blank") String name,
        @NotEmpty(message = "age is blank") int age,
        @NotNull(message = "course is blank") CourseDTO course,
        List<Double> notes
        ) {
}
