package com.yordanos.sms.response;

import com.yordanos.sms.enums.Gender;
import com.yordanos.sms.enums.TeacherStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class TeacherResponseDto {
    private String id;
    private String name;
    private String fatherName;
    private String grandFatherName;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private TeacherStatus status;

    @Lob
    private String address;

    private Long imageId;
}
