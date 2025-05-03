package com.example.service;

import com.example.entity.Submission;
import com.example.dto.SubmissionRequest;

import java.util.List;
import java.util.Optional;

public interface SubmissionService {
    Submission createSubmission(SubmissionRequest submissionRequest);
    List<Submission> getAllSubmissions();
    Optional<Submission> getSubmissionById(Long id);
    List<Submission> getSubmissionsByUser(Long userId);
    List<Submission> getSubmissionsByTask(Long taskId);
    Submission executeSubmission(Submission submission);
}    