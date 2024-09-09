package com.danielks.Students_Notes.entities.dtos;

import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record CourseDTO(
        UUID id,
        @NotEmpty(message = "name is blank") String name,
        int maxStudents

) {
}
