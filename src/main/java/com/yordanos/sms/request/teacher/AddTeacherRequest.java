package com.yordanos.sms.request.teacher;

import com.yordanos.sms.enums.Gender;
import com.yordanos.sms.enums.TeacherStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddTeacherRequest {
    private String name;
    private String fatherName;
    private String grandFatherName;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private TeacherStatus status;

    @Lob
    private String address;

    private MultipartFile image;
}
