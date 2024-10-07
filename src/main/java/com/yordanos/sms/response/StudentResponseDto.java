package com.yordanos.sms.response;

import com.yordanos.sms.enums.Gender;
import com.yordanos.sms.enums.Section;
import com.yordanos.sms.enums.StudentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class StudentResponseDto {
    private String id;
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

    private Long imageId;
}
