package com.yordanos.sms.service;

import com.yordanos.sms.request.teacher.AddTeacherRequest;
import com.yordanos.sms.exception.ResourceNotFoundException;
import com.yordanos.sms.model.Image;
import com.yordanos.sms.model.Teacher;
import com.yordanos.sms.repository.TeacherRepo;
import com.yordanos.sms.request.teacher.UpdateTeacherRequest;
import com.yordanos.sms.response.TeacherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class TeacherService {
    private final TeacherRepo teacherRepo;
    private final ImageService imageService;

    private String generateUniqueId() {
        String generatedId = "";
        do {
            int fourDigitNumber = new Random().nextInt(9000) + 1000;
            String year = String.valueOf(Year.now().getValue()).substring(2);
            generatedId = "TCH/" + fourDigitNumber + "/" + year;
        } while (teacherRepo.existsById(generatedId));
        return generatedId;
    }

    public Teacher addTeacher(AddTeacherRequest addTeacherRequest) {
        Image image = imageService.saveImage(addTeacherRequest.getImageFile());
        return teacherRepo.save(createTeacher(addTeacherRequest, image));
    }

    private Teacher createTeacher(AddTeacherRequest addTeacherRequest, Image image) {
        Teacher teacher = new Teacher();

        teacher.setId(generateUniqueId());
        teacher.setName(addTeacherRequest.getName());
        teacher.setFatherName(addTeacherRequest.getFatherName());
        teacher.setGrandFatherName(addTeacherRequest.getGrandFatherName());
        teacher.setGender(addTeacherRequest.getGender());
        teacher.setStatus(addTeacherRequest.getStatus());
        teacher.setAddress(addTeacherRequest.getAddress());
        teacher.setImage(image);

        return teacher;
    }

    public Teacher updateTeacher(UpdateTeacherRequest updateTeacherRequest) {
        Image image = imageService.updateImage(updateTeacherRequest.getImageFile(), updateTeacherRequest.getImageId());
        return teacherRepo.save(updateExistingTeacher(updateTeacherRequest, image));
    }

    private Teacher updateExistingTeacher(UpdateTeacherRequest updateTeacherRequest, Image image) {
        Teacher teacher = new Teacher();

        teacher.setId(updateTeacherRequest.getId());
        teacher.setName(updateTeacherRequest.getName());
        teacher.setFatherName(updateTeacherRequest.getFathersName());
        teacher.setGrandFatherName(updateTeacherRequest.getGrandFathersName());
        teacher.setGender(updateTeacherRequest.getGender());
        teacher.setStatus(updateTeacherRequest.getStatus());
        teacher.setAddress(updateTeacherRequest.getAddress());
        teacher.setImage(image);
        return teacher;
    }

    public void deleteTeacher(TeacherResponseDto teacherResponseDto) {
        teacherRepo.findById(teacherResponseDto.getId())
                .ifPresentOrElse(teacherRepo::delete,
                        () -> new ResourceNotFoundException("Teacher not found"));
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepo.findAll();
    }

    public Teacher getTeacherById(String id) throws ResourceNotFoundException {
        return teacherRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Teacher with id " + id + " not found"));
    }

    public List<Teacher> getTeacherByName(String name) {
        return teacherRepo.findByName(name);
    }

    public Long countTeachers() {
        return teacherRepo.count();
    }

    public TeacherResponseDto convertToResponseDto(Teacher teacher) {
        TeacherResponseDto teacherResponseDto = new TeacherResponseDto();

        teacherResponseDto.setId(teacher.getId());
        teacherResponseDto.setName(teacher.getName());
        teacherResponseDto.setFatherName(teacher.getFatherName());
        teacherResponseDto.setGrandFatherName(teacher.getGrandFatherName());
        teacherResponseDto.setGender(teacher.getGender());
        teacherResponseDto.setStatus(teacher.getStatus());
        teacherResponseDto.setAddress(teacher.getAddress());
        teacherResponseDto.setImageId(teacher.getImage().getId());
        return teacherResponseDto;
    }

    public List<TeacherResponseDto> convertToListOfResponseDto(List<Teacher> teachers) {
        return teachers.stream().map(this::convertToResponseDto).toList();
    }
}
