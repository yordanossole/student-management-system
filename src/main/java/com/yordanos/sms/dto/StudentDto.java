package com.yordanos.sms.dto;

import com.yordanos.sms.enums.Gender;
import com.yordanos.sms.enums.Section;
import com.yordanos.sms.enums.StudentStatus;
import com.yordanos.sms.model.Image;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StudentDto {
    private String name;
    private String fatherName;
    private String grandFatherName;

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
