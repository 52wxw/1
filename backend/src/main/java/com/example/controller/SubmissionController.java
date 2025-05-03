package com.example.controller;

import com.example.dto.SubmissionRequest;
import com.example.entity.Submission;
import com.example.service.SubmissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @GetMapping
    public ResponseEntity<List<Submission>> getAllSubmissions() {
        List<Submission> submissions = submissionService.getAllSubmissions();
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmissionById(@PathVariable Long id) {
        return submissionService.getSubmissionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Submission> createSubmission(@RequestBody SubmissionRequest submissionRequest) {
        Submission submission = submissionService.createSubmission(submissionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(submission);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Submission>> getSubmissionsByUser(@PathVariable Long userId) {
        List<Submission> submissions = submissionService.getSubmissionsByUser(userId);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Submission>> getSubmissionsByTask(@PathVariable Long taskId) {
        List<Submission> submissions = submissionService.getSubmissionsByTask(taskId);
        return ResponseEntity.ok(submissions);
    }
}    