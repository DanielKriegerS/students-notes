package com.danielks.Students_Notes.controllers;

import com.danielks.Students_Notes.entities.dtos.CourseDTO;
import com.danielks.Students_Notes.entities.dtos.StudentDTO;
import com.danielks.Students_Notes.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return service.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable UUID id) {
        StudentDTO student = service.getStudentById(id);
        return student != null ? ResponseEntity.ok(student) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO createdCourse = service.createStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable UUID id, @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = service.updateStudent(id, studentDTO);
        return ResponseEntity.ok(updatedStudent);
    }
}
