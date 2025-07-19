package ru.college.resumesserv.services;

import ru.college.resumesserv.models.Resume;
import ru.college.resumesserv.models.ResumeDTO;

import java.util.List;

public interface ResumeService {
    List<ResumeDTO> getAllResumes(Integer course, String specialty, String group);

    ResumeDTO getResumeById(Long id);

    void createResume(Resume resume);

    void updateResume(Long id, Resume resume);

    void deleteResume(Long id);
}
