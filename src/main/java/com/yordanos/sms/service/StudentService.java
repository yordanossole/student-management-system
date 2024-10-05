package com.yordanos.sms.service;

import com.yordanos.sms.dto.StudentDto;
import com.yordanos.sms.exception.ResourceNotFoundException;
import com.yordanos.sms.model.Image;
import com.yordanos.sms.model.Student;
import com.yordanos.sms.repository.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepo studentRepo;
    private final ImageService imageService;

    private String generateUniqueId () {
        String generatedId = "";
        do {
            int fourDigitNum = new Random().nextInt(9000) + 1000;
            String year = String.valueOf(Year.now().getValue()).substring(2);
            generatedId = "STU/" + fourDigitNum + "/" + year;
        } while (studentRepo.existsById(generatedId));
        return generatedId;
    }

    public Student addStudent(StudentDto studentDto) {
        Image image = imageService.saveImage(studentDto.getImage());
        return studentRepo.save(createStudent(studentDto, image));
    }

    private Student createStudent(StudentDto studentDto, Image image) {
        Student student = new Student();
        student.setId(generateUniqueId());
        student.setName(studentDto.getName());
        student.setFatherName(studentDto.getFatherName());
        student.setGrandFatherName(studentDto.getGrandFatherName());
        student.setGender(studentDto.getGender());
        student.setStatus(studentDto.getStatus());
        student.setSection(studentDto.getSection());
        student.setGrade(studentDto.getGrade());
        student.setAddress(studentDto.getAddress());
        student.setImage(image);
        return student;
    }

    public Student updateStudent(Student student) {
        return studentRepo.save(student);
    }

    public void deleteStudent(Student student) {
        studentRepo.delete(student);
    }

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public Student getStudentById(String id) throws ResourceNotFoundException {
        return studentRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student with id " + id + " not found"));
    }

    public List<Student> getStudentByName(String name) throws ResourceNotFoundException {
        return studentRepo.findByName(name);
    }

    public List<Student> getStudentsByFathersName(String fathersName) {
        return studentRepo.findByFathersName(fathersName);
    }

    public Long countStudentsByStatus(String status) {
        return studentRepo.countByStatus(status);
    }

    public List<Student> getStudentByGender(String gender) {
        return studentRepo.findByGender(gender);
    }

    public Long countStudentsByGender(String gender, String status) {
        return studentRepo.countByGenderAndStatus(gender, status);
    }
}
