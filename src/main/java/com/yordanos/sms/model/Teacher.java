package com.yordanos.sms.model;

import com.yordanos.sms.enums.Gender;
import com.yordanos.sms.enums.TeacherStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    @Id
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;
}
