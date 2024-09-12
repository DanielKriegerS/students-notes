package com.danielks.Students_Notes.entities.mappers;

import com.danielks.Students_Notes.entities.Course;
import com.danielks.Students_Notes.entities.Student;
import com.danielks.Students_Notes.entities.dtos.CourseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "maxStudents", target = "maxStudents")
    @Mapping(source = "students", target = "studentsIds")
    CourseDTO toDto(Course course);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "maxStudents", target = "maxStudents")
    @Mapping(target = "students", ignore = true)
    Course toEntity(CourseDTO dto);

    default List<UUID> mapStudentsToIds(List<Student> students) {
        return students != null
                ? students.stream().map(Student::getId).collect(Collectors.toList())
                : new ArrayList<>();
    }
}
