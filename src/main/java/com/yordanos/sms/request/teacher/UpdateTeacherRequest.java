package com.yordanos.sms.request.teacher;

import com.yordanos.sms.enums.Gender;
import com.yordanos.sms.enums.TeacherStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateTeacherRequest {
    private String id;
    private String name;
    private String fathersName;
    private String grandFathersName;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private TeacherStatus status;

    @Lob
    private String address;

    private Long imageId;
    private MultipartFile imageFile;
}
