package com.yordanos.sms.controller;

import com.yordanos.sms.request.student.AddStudentRequest;
import com.yordanos.sms.enums.Gender;
import com.yordanos.sms.enums.StudentStatus;
import com.yordanos.sms.exception.ResourceNotFoundException;
import com.yordanos.sms.model.Student;
import com.yordanos.sms.request.student.UpdateStudentRequest;
import com.yordanos.sms.response.ApiResponse;
import com.yordanos.sms.response.StudentResponseDto;
import com.yordanos.sms.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/student/add")
    public ResponseEntity<ApiResponse> addStudent(@ModelAttribute AddStudentRequest addStudentRequest) {
        try {
            Student addedStudent = studentService.addStudent(addStudentRequest);
            StudentResponseDto studentResponseDto = studentService.convertToResponseDto(addedStudent);
            return ResponseEntity.ok(new ApiResponse("Added Successfully", studentResponseDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/student/update")
    public ResponseEntity<ApiResponse> updateStudent(@ModelAttribute UpdateStudentRequest updateStudentRequest) {
        try {
            Student updatedStudent = studentService.updateStudent(updateStudentRequest);
            StudentResponseDto studentResponseDto = studentService.convertToResponseDto(updatedStudent);
            return ResponseEntity.ok(new ApiResponse("Updated Successfully", studentResponseDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        List<StudentResponseDto> studentResponseDtos = studentService.convertToListOfResponseDto(students);
        return ResponseEntity.ok(new ApiResponse("Success", studentResponseDtos));
    }

    @DeleteMapping("/student")
    public ResponseEntity<ApiResponse> deleteStudent(@RequestBody StudentResponseDto student) {
        try {
            studentService.deleteStudent(student);
            return ResponseEntity.ok(new ApiResponse("Deleted Successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/student/id")
    public ResponseEntity<ApiResponse> getStudentById(@RequestParam String studentId) {
        try {
            Student student = studentService.getStudentById(studentId);
            StudentResponseDto studentResponseDto = studentService.convertToResponseDto(student);
            return ResponseEntity.ok(new ApiResponse("Success", studentResponseDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/student-name/{studentName}")
    public ResponseEntity<ApiResponse> getStudentsByName(@PathVariable String studentName) {
        List<Student> students = studentService.getStudentByName(studentName);
        List<StudentResponseDto> studentResponseDtos = studentService.convertToListOfResponseDto(students);
        return ResponseEntity.ok(new ApiResponse("Success", studentResponseDtos));
    }

    @GetMapping("/fathers-name/{fathersName}")
    public ResponseEntity<ApiResponse> getStudentsByFathersName(@PathVariable String fathersName) {
        List<Student> students = studentService.getStudentsByFathersName(fathersName);
        List<StudentResponseDto> studentResponseDtos = studentService.convertToListOfResponseDto(students);
        return ResponseEntity.ok(new ApiResponse("Success", studentResponseDtos));
    }

    @GetMapping("/gender/{gender}")
    public ResponseEntity<ApiResponse> getStudentsByGender(@PathVariable Gender gender) {
        List<Student> students = studentService.getStudentByGender(gender);
        List<StudentResponseDto> studentResponseDtos = studentService.convertToListOfResponseDto(students);
        return ResponseEntity.ok(new ApiResponse("Success", studentResponseDtos));
    }

    @GetMapping("/count/by/gender-and-status")
    public ResponseEntity<ApiResponse> countStudentsByGenderAndStatus(@RequestParam Gender gender, @RequestParam StudentStatus status) {
        Long num = studentService.countStudentsByGenderAndStatus(gender, status);
        return ResponseEntity.ok(new ApiResponse("Success", num));
    }

    @GetMapping("/count/by/status")
    public ResponseEntity<ApiResponse> countStudentsByStatus(@RequestParam StudentStatus status) {
        Long num = studentService.countStudentsByStatus(status);
        return ResponseEntity.ok(new ApiResponse("Success", num));
    }

}
