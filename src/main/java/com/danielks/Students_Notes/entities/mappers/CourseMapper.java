package com.danielks.Students_Notes.entities.mappers;

import com.danielks.Students_Notes.entities.Course;
import com.danielks.Students_Notes.entities.dtos.CourseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StudentMapper.class})
public interface CourseMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "maxStudents", target = "maxStudents")
    @Mapping(source = "students", target = "students")
    CourseDTO toDto(Course course);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "maxStudents", target = "maxStudents")
    @Mapping(source = "students", target = "students")
    Course toEntity(CourseDTO dto);
}
