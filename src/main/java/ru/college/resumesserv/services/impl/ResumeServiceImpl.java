package ru.college.resumesserv.services.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.college.resumesserv.models.Resume;
import ru.college.resumesserv.models.ResumeDTO;
import ru.college.resumesserv.repository.ResumeRepository;
import ru.college.resumesserv.services.ResumeService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;

    @Override
    public List<ResumeDTO> getAllResumes(Integer course, String specialty, String group) {
        List<Resume> resumes;

        if (course != null || specialty != null || group != null) {
            resumes = resumeRepository.findAll((root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();

                if (course != null) {
                    predicates.add(criteriaBuilder.equal(root.get("course"), course));
                }
                if (specialty != null) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("specialty")), "%" + specialty.toLowerCase() + "%"));
                }
                if (group != null) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + group + "%"));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            });
        } else {
            resumes = resumeRepository.findAll();
        }

        return resumes.stream().map(ResumeDTO::toDTO).collect(Collectors.toList());
    }

    @Override
    public ResumeDTO getResumeById(Long id) {
        return resumeRepository.findById(id)
                .map(ResumeDTO::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Такого резюме не существует"));

    }

    @Override
    public void createResume(Resume resume) {
        if (resume == null) {
           throw new IllegalArgumentException("Пустое резюме");
        }

        resumeRepository.save(resume);
    }

    @Override
    public void updateResume(Long id, Resume resume) {
        resumeRepository.findById(id)
                .map(existingResume -> {
                    existingResume.setCourse(resume.getCourse());
                    existingResume.setName(resume.getName());
                    existingResume.setMidName(resume.getMidName());
                    existingResume.setLastName(resume.getLastName());
                    existingResume.setSpecialty(resume.getSpecialty());
                    existingResume.setGroup(resume.getGroup());
                    existingResume.setDescription(resume.getDescription());

                    return resumeRepository.save(existingResume);
                });
    }



    @Override
    public void deleteResume(Long id) {
        if (resumeRepository.existsById(id)) {
            resumeRepository.deleteById(id);
        }
    }
}
