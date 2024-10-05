package com.yordanos.sms.repository;

import com.yordanos.sms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, String> {
    List<Student> findByName(String name);
    List<Student> findByFathersName(String fathersName);
    List<Student> findByGender(String gender);
    Long countByStatus(String status);
    Long countByGenderAndStatus(String gender, String status);
}
