package com.yordanos.sms.controller;

import com.yordanos.sms.exception.ResourceNotFoundException;
import com.yordanos.sms.model.Teacher;
import com.yordanos.sms.repository.TeacherRepo;
import com.yordanos.sms.request.teacher.AddTeacherRequest;
import com.yordanos.sms.request.teacher.UpdateTeacherRequest;
import com.yordanos.sms.response.ApiResponse;
import com.yordanos.sms.response.TeacherResponseDto;
import com.yordanos.sms.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final TeacherRepo teacherRepo;

    @PostMapping("/teacher/add")
    public ResponseEntity<ApiResponse> addTeacher(@ModelAttribute AddTeacherRequest teacherRequest) {
        try {
            Teacher addedTeacher = teacherService.addTeacher(teacherRequest);
            TeacherResponseDto teacherResponseDto = teacherService.convertToResponseDto(addedTeacher);
            return ResponseEntity.ok(new ApiResponse("Added Successfully", teacherResponseDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/teacher/update")
    public ResponseEntity<ApiResponse> updateTeacher(@ModelAttribute UpdateTeacherRequest updateTeacherRequest) {
        Teacher updatedTeacher = teacherService.updateTeacher(updateTeacherRequest);
        return ResponseEntity.ok(new ApiResponse("Updated Successfully", updatedTeacher));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        List<TeacherResponseDto> teacherResponseDtos = teacherService.convertToListOfResponseDto(teachers);
        return ResponseEntity.ok(new ApiResponse("Success", teacherResponseDtos));
    }

    @DeleteMapping("/teacher")
    public ResponseEntity<ApiResponse> deleteTeacher(@RequestBody TeacherResponseDto teacherResponseDto) {
        try {
            teacherService.deleteTeacher(teacherResponseDto);
            return ResponseEntity.ok(new ApiResponse("Deleted Successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/teacher/id")
    public ResponseEntity<ApiResponse> getTeacherById(@RequestParam String teacherId) {
        try {
            Teacher teacher = teacherService.getTeacherById(teacherId);
            TeacherResponseDto teacherResponseDto = teacherService.convertToResponseDto(teacher);
            return ResponseEntity.ok(new ApiResponse("Success", teacherResponseDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/teacher-name/{teacherName}")
    public ResponseEntity<ApiResponse> getTeachersByName(@PathVariable String teacherName) {
        List<Teacher> teachers = teacherService.getTeacherByName(teacherName);
        List<TeacherResponseDto> teacherResponseDtos = teacherService.convertToListOfResponseDto(teachers);
        return ResponseEntity.ok(new ApiResponse("Success", teacherResponseDtos));
    }
}
