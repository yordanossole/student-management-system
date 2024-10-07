package com.yordanos.sms.repository;

import com.yordanos.sms.enums.Gender;
import com.yordanos.sms.enums.StudentStatus;
import com.yordanos.sms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, String> {
    List<Student> findByName(String name);
    List<Student> findByFathersName(String fathersName);
    List<Student> findByGender(Gender gender);
    Long countByStatus(StudentStatus status);
    Long countByGenderAndStatus(Gender gender, StudentStatus status);
}
