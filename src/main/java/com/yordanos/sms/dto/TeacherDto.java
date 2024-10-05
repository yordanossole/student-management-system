package com.yordanos.sms.dto;

import com.yordanos.sms.enums.Gender;
import com.yordanos.sms.enums.TeacherStatus;
import com.yordanos.sms.model.Image;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TeacherDto {
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
