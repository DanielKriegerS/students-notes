package com.danielks.Students_Notes.entities.mappers;

import com.danielks.Students_Notes.entities.Student;
import com.danielks.Students_Notes.entities.dtos.StudentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface StudentMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "course", target = "course")
    @Mapping(source = "notes", target = "notes")
    StudentDTO toDto(Student student);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "course", target = "course")
    @Mapping(source = "notes", target = "notes")
    Student toEntity(StudentDTO dto);
}