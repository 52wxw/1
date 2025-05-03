package com.example.repository;

import com.example.entity.Submission;
import com.example.entity.Task;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByUser(User user);
    List<Submission> findByTask(Task task);
    List<Submission> findByUserAndTask(User user, Task task);
}    