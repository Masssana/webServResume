package ru.college.resumesserv.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import ru.college.resumesserv.CRUD.Resume;
import ru.college.resumesserv.repository.ResumeRepository;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;


@RestController
@RequestMapping("/api/resume")
//@CrossOrigin(origins = "*")
public class ResumeController {
    @Autowired
    private final ResumeRepository resumeRepository;

    @GetMapping("/test")
    public ResponseEntity<String> testConnection() {
        return ResponseEntity.ok("Connection established");
    }

    public ResumeController(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    // 1. Получить все резюме (с поддержкой фильтрации)
    @GetMapping("/getAll")
    public ResponseEntity<List<Resume>> getAllResumes(
            @RequestParam(required = false) Integer course,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String group) {

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

        return ResponseEntity.ok(resumes);
    }

    //  Получить одно резюме по ID
    @GetMapping("/{id}")
    public ResponseEntity<Resume> getResumeById(@PathVariable Long id) {
        return resumeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //  Создать новое резюме
    @PostMapping("/add")
    public ResponseEntity<?> createResume(@RequestBody Resume resume) {
        if (resume.getId() != null && resume.getId() != 0) {
            return ResponseEntity.badRequest().body("ID should not be provided for new resumes");
        }
        resume.setId(null); // явно удаляем ID
        return ResponseEntity.ok(resumeRepository.save(resume));
    }

    // 4. Обновить существующее резюме
    @PutMapping("/update/{id}")
    public ResponseEntity<Resume> updateResume(@PathVariable Long id, @RequestBody Resume updatedResume) {
        return resumeRepository.findById(id)
                .map(existingResume -> {
                    existingResume.setCourse(updatedResume.getCourse());
                    existingResume.setName(updatedResume.getName());
                    existingResume.setMidName(updatedResume.getMidName());
                    existingResume.setLastName(updatedResume.getLastName());
                    existingResume.setSpecialty(updatedResume.getSpecialty());
                    existingResume.setGroup(updatedResume.getGroup());
                    existingResume.setDescription(updatedResume.getDescription());

                    Resume savedResume = resumeRepository.save(existingResume);
                    return ResponseEntity.ok(savedResume);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    //  Удалить резюме
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable Long id) {
        if (resumeRepository.existsById(id)) {
            resumeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
//    @PostMapping("/save")
//    public ResponseEntity<Resume> save(@RequestParam("name") String name,
//                                       @RequestParam("midName") String midName,
//                                       @RequestParam("lastName") String lastName,
//                                       @RequestParam("description") String description,
//                                       @RequestParam("email") String email) {
//        Resume resume = new Resume(null, name, midName, lastName, description, email);
//        resume = resumeRepository.save(resume);
//
//        return ResponseEntity.ok(resume);
//    }

//    @GetMapping("/resumes")
//    public String getResumes(Model model) {
//        List<Resume> resumes = resumeRepository.findAll();
//        model.addAttribute("resumes", resumes);
//        return "resumes";
//    }


