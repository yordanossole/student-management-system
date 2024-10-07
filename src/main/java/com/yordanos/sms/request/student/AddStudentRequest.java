package com.yordanos.sms.request.student;

import com.yordanos.sms.enums.Gender;
import com.yordanos.sms.enums.Section;
import com.yordanos.sms.enums.StudentStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddStudentRequest {
    private String name;
    private String fathersName;
    private String grandFathersName;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private StudentStatus status;
    @Enumerated(EnumType.STRING)
    private Section section;

    private int grade;

    @Lob
    private String address;

    private MultipartFile image;
}
