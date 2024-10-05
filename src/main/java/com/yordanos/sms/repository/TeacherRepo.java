package com.yordanos.sms.repository;

import com.yordanos.sms.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, String> {
    List<Teacher> findByName(String name);
}
