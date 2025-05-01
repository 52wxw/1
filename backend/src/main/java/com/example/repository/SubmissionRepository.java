package com.example.repository;

import com.example.model.Submission;
import com.example.model.Task;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findByUser(User user);

    List<Submission> findByTask(Task task);

    Optional<Submission> findFirstByUserAndTaskOrderBySubmissionTimeDesc(User user, Task task);

    List<Submission> findByUserAndCorrectIsTrue(User user);
}