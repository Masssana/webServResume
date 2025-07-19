package ru.college.resumesserv.models;

import jakarta.persistence.Column;
import lombok.*;
import org.checkerframework.checker.units.qual.A;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResumeDTO {
    private Integer course;

    private String name;

    private String midName;

    private String lastName;

    private String specialty;

    private String group;

    private String description;

    public static ResumeDTO toDTO(Resume resume) {
        return ResumeDTO.builder().course(resume.getCourse())
                .name(resume.getName())
                .midName(resume.getMidName())
                .lastName(resume.getLastName())
                .specialty(resume.getSpecialty())
                .group(resume.getGroup())
                .description(resume.getDescription())
                .build();
    }
}
