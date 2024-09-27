package com.danielks.Students_Notes.services;

import com.danielks.Students_Notes.entities.Student;
import com.danielks.Students_Notes.entities.dtos.StudentDTO;
import com.danielks.Students_Notes.entities.mappers.StudentMapper;
import com.danielks.Students_Notes.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final ValidationService validator;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper, ValidationService validator) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.validator = validator;
    }

    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    public StudentDTO getStudentById(UUID id) {
        return studentRepository.findById(id)
                .map(studentMapper::toDto)
                .orElse(null);
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = studentMapper.toEntity(studentDTO);

        validator.validateNewStudent(student);

        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    public StudentDTO updateStudent(UUID id, StudentDTO studentDTO) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Student not found with id " + id));

        if (studentDTO.name() != null || studentDTO.name().trim().isEmpty()){
            existingStudent.setName(studentDTO.name());
        }

        if (studentDTO.age() > 15) {
            existingStudent.setAge(studentDTO.age());
        }

        Student updateStudent = studentRepository.save(existingStudent);
        return studentMapper.toDto(updateStudent);
    }
}
