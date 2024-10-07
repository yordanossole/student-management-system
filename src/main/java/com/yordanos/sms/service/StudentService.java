package com.yordanos.sms.service;

import com.yordanos.sms.request.student.AddStudentRequest;
import com.yordanos.sms.enums.Gender;
import com.yordanos.sms.enums.StudentStatus;
import com.yordanos.sms.exception.ResourceNotFoundException;
import com.yordanos.sms.model.Image;
import com.yordanos.sms.model.Student;
import com.yordanos.sms.repository.StudentRepo;
import com.yordanos.sms.request.student.UpdateStudentRequest;
import com.yordanos.sms.response.StudentResponseDto;
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

    public Student addStudent(AddStudentRequest addStudentRequest) {
        Image image = imageService.saveImage(addStudentRequest.getImage());
        return studentRepo.save(createStudent(addStudentRequest, image));
    }

    private Student createStudent(AddStudentRequest addStudentRequest, Image image) {
        Student student = new Student();
        student.setId(generateUniqueId());
        student.setName(addStudentRequest.getName());
        student.setFathersName(addStudentRequest.getFathersName());
        student.setGrandFathersName(addStudentRequest.getGrandFathersName());
        student.setGender(addStudentRequest.getGender());
        student.setStatus(addStudentRequest.getStatus());
        student.setSection(addStudentRequest.getSection());
        student.setGrade(addStudentRequest.getGrade());
        student.setAddress(addStudentRequest.getAddress());
        student.setImage(image);
        return student;
    }

    public Student updateStudent(UpdateStudentRequest updateStudentRequest) {
        Image image = imageService.updateImage(updateStudentRequest.getImageFile(), updateStudentRequest.getImageId());
        return studentRepo.save(updateExistingStudent(updateStudentRequest, image));
    }

    private Student updateExistingStudent(UpdateStudentRequest updateStudentRequest, Image image) {
        Student student = new Student();

        student.setId(updateStudentRequest.getId());
        student.setName(updateStudentRequest.getName());
        student.setFathersName(updateStudentRequest.getFathersName());
        student.setGrandFathersName(updateStudentRequest.getGrandFathersName());
        student.setGender(updateStudentRequest.getGender());
        student.setStatus(updateStudentRequest.getStatus());
        student.setSection(updateStudentRequest.getSection());
        student.setGrade(updateStudentRequest.getGrade());
        student.setAddress(updateStudentRequest.getAddress());
        student.setImage(image);
        return student;
    }

    public void deleteStudent(StudentResponseDto student) {
        studentRepo.findById(student.getId())
                .ifPresentOrElse(studentRepo::delete,
                        () -> new ResourceNotFoundException("Student not found"));
    }

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public Student getStudentById(String id) throws ResourceNotFoundException {
        return studentRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student with id " + id + " not found"));
    }

    public List<Student> getStudentByName(String name) {
        return studentRepo.findByName(name);
    }

    public List<Student> getStudentsByFathersName(String fathersName) {
        return studentRepo.findByFathersName(fathersName);
    }

    public Long countStudentsByStatus(StudentStatus status) {
        return studentRepo.countByStatus(status);
    }

    public List<Student> getStudentByGender(Gender gender) {
        return studentRepo.findByGender(gender);
    }

    public Long countStudentsByGenderAndStatus(Gender gender, StudentStatus status) {
        return studentRepo.countByGenderAndStatus(gender, status);
    }

    public StudentResponseDto convertToResponseDto(Student student) {
        StudentResponseDto studentResponseDto = new StudentResponseDto();

        studentResponseDto.setId(student.getId());
        studentResponseDto.setName(student.getName());
        studentResponseDto.setFathersName(student.getFathersName());
        studentResponseDto.setGrandFathersName(student.getGrandFathersName());
        studentResponseDto.setGender(student.getGender());
        studentResponseDto.setStatus(student.getStatus());
        studentResponseDto.setSection(student.getSection());
        studentResponseDto.setGrade(student.getGrade());
        studentResponseDto.setAddress(student.getAddress());
        studentResponseDto.setImageId(student.getImage().getId());
        return studentResponseDto;
    }

    public List<StudentResponseDto> convertToListOfResponseDto(List<Student> students) {
        return students.stream().map(this::convertToResponseDto).toList();
    }
}
