package ru.college.resumesserv.controllers;


import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import ru.college.resumesserv.models.Resume;
import ru.college.resumesserv.models.ResumeDTO;
import ru.college.resumesserv.repository.ResumeRepository;
import ru.college.resumesserv.services.ResumeService;

import java.util.List;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeRepository resumeRepository;
    private final ResumeService resumeService;

    @GetMapping("/getAll")
    public ResponseEntity<List<ResumeDTO>> getAllResumes(
            @RequestParam(required = false) Integer course,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String group) {

        return ResponseEntity.ok(resumeService.getAllResumes(course, specialty, group));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeDTO> getResumeById(@PathVariable Long id) {
        return ResponseEntity.ok(resumeService.getResumeById(id));
    }

    @PostMapping("/add")
    public void createResume(@RequestBody Resume resume) {
        resumeService.createResume(resume);
    }

    @PutMapping("/update/{id}")
    public void updateResume(@PathVariable Long id, @RequestBody Resume updatedResume) {
        resumeService.updateResume(id, updatedResume);
    }

    @DeleteMapping("/{id}")
    public void deleteResume(@PathVariable Long id) {
        resumeService.deleteResume(id);
    }
}



