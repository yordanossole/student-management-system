package com.yordanos.sms.service;

import com.yordanos.sms.dto.StudentDto;
import com.yordanos.sms.dto.TeacherDto;
import com.yordanos.sms.exception.ResourceNotFoundException;
import com.yordanos.sms.model.Image;
import com.yordanos.sms.model.Student;
import com.yordanos.sms.model.Teacher;
import com.yordanos.sms.repository.StudentRepo;
import com.yordanos.sms.repository.TeacherRepo;
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

    public Teacher addTeacher(TeacherDto teacherDto) {
        Image image = imageService.saveImage(teacherDto.getImage());
        return teacherRepo.save(createStudent(teacherDto, image));
    }

    private Teacher createStudent(TeacherDto teacherDto, Image image) {
        Teacher teacher = new Teacher();

        teacher.setId(generateUniqueId());
        teacher.setName(teacherDto.getName());
        teacher.setFatherName(teacherDto.getFatherName());
        teacher.setGrandFatherName(teacherDto.getGrandFatherName());
        teacher.setGender(teacherDto.getGender());
        teacher.setStatus(teacherDto.getStatus());
        teacher.setAddress(teacherDto.getAddress());
        teacher.setImage(image);

        return teacher;
    }
    public Teacher updateTeacher(Teacher teacher) {
        return teacherRepo.save(teacher);
    }

    public void deleteTeacher(Teacher teacher) {
        teacherRepo.delete(teacher);
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
}
