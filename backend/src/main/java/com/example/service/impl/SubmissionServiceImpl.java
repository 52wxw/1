package com.example.service.impl;

import com.example.entity.Submission;
import com.example.entity.Task;
import com.example.entity.TestCase;
import com.example.entity.User;
import com.example.repository.SubmissionRepository;
import com.example.repository.TaskRepository;
import com.example.repository.UserRepository;
import com.example.service.SubmissionService;
import com.example.dto.SubmissionRequest;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public SubmissionServiceImpl(SubmissionRepository submissionRepository,
                                UserRepository userRepository,
                                TaskRepository taskRepository) {
        this.submissionRepository = submissionRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Submission createSubmission(SubmissionRequest submissionRequest) {
        User user = userRepository.findById(submissionRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + submissionRequest.getUserId()));

        Task task = taskRepository.findById(submissionRequest.getTaskId())
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + submissionRequest.getTaskId()));

        Submission submission = new Submission();
        submission.setUser(user);
        submission.setTask(task);
        submission.setCode(submissionRequest.getCode());
        submission.setLanguage(submissionRequest.getLanguage());
        submission.setPassed(false);
        submission.setScore(0);
        submission.setResult("Pending execution");

        return submissionRepository.save(submission);
    }

    @Override
    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    @Override
    public Optional<Submission> getSubmissionById(Long id) {
        return submissionRepository.findById(id);
    }

    @Override
    public List<Submission> getSubmissionsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return submissionRepository.findByUser(user);
    }

    @Override
    public List<Submission> getSubmissionsByTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        return submissionRepository.findByTask(task);
    }

    @Override
    public Submission executeSubmission(Submission submission) {
        Task task = submission.getTask();
        List<TestCase> testCases = task.getTestCases();
        
        int passedCount = 0;
        StringBuilder resultBuilder = new StringBuilder();
        
        for (TestCase testCase : testCases) {
            ExecutionResult executionResult = executeCode(submission.getCode(), 
                                                          submission.getLanguage(), 
                                                          testCase.getInput());
            
            boolean passed = executionResult.getOutput().trim().equals(testCase.getExpectedOutput().trim());
            if (passed) {
                passedCount++;
                resultBuilder.append("Test case passed: ").append(testCase.getInput()).append("\n");
            } else {
                resultBuilder.append("Test case failed: Input - ").append(testCase.getInput())
                             .append(", Expected - ").append(testCase.getExpectedOutput())
                             .append(", Got - ").append(executionResult.getOutput()).append("\n");
            }
            
            if (!executionResult.getError().isEmpty()) {
                resultBuilder.append("Error: ").append(executionResult.getError()).append("\n");
            }
        }
        
        submission.setPassed(passedCount == testCases.size());
        submission.setScore((int) Math.round((double) passedCount / testCases.size() * 100));
        submission.setResult(resultBuilder.toString());
        
        return submissionRepository.save(submission);
    }
    
    private ExecutionResult executeCode(String code, String language, String input) {
        String output = "";
        String error = "";
        
        try {
            // 实际项目中应使用更安全的代码执行方式
            // 这里仅作示例，实际生产环境需要沙箱隔离
            ProcessBuilder processBuilder;
            
            if (language.equalsIgnoreCase("java")) {
                // 保存Java代码到临时文件
                String className = "Solution";
                java.io.File tempDir = java.io.File.createTempFile("code", "");
                tempDir.delete();
                tempDir.mkdir();
                
                java.io.File javaFile = new java.io.File(tempDir, className + ".java");
                java.io.FileWriter writer = new java.io.FileWriter(javaFile);
                writer.write(code);
                writer.close();
                
                // 编译Java代码
                processBuilder = new ProcessBuilder("javac", javaFile.getAbsolutePath());
                processBuilder.directory(tempDir);
                Process compileProcess = processBuilder.start();
                compileProcess.waitFor();
                
                // 检查编译错误
                BufferedReader compileErrorReader = new BufferedReader(
                    new InputStreamReader(compileProcess.getErrorStream()));
                StringBuilder compileError = new StringBuilder();
                String line;
                while ((line = compileErrorReader.readLine()) != null) {
                    compileError.append(line).append("\n");
                }
                
                if (compileError.length() > 0) {
                    error = "Compilation error:\n" + compileError.toString();
                    return new ExecutionResult(output, error);
                }
                
                // 执行Java代码
                processBuilder = new ProcessBuilder("java", "-cp", tempDir.getAbsolutePath(), className);
                processBuilder.directory(tempDir);
            } else if (language.equalsIgnoreCase("python")) {
                // 保存Python代码到临时文件
                java.io.File tempFile = java.io.File.createTempFile("code", ".py");
                java.io.FileWriter writer = new java.io.FileWriter(tempFile);
                writer.write(code);
                writer.close();
                
                // 执行Python代码
                processBuilder = new ProcessBuilder("python3", tempFile.getAbsolutePath());
            } else {
                error = "Unsupported language: " + language;
                return new ExecutionResult(output, error);
            }
            
            // 设置输入
            processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);
            Process process = processBuilder.start();
            
            // 提供输入
            java.io.OutputStream stdin = process.getOutputStream();
            stdin.write(input.getBytes());
            stdin.close();
            
            // 获取输出
            BufferedReader outputReader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
            StringBuilder outputBuilder = new StringBuilder();
	    String line;
            while ((line = outputReader.readLine()) != null) {
                outputBuilder.append(line).append("\n");
            }
            output = outputBuilder.toString();
            
            // 获取错误
            BufferedReader errorReader = new BufferedReader(
                new InputStreamReader(process.getErrorStream()));
            StringBuilder errorBuilder = new StringBuilder();
            while ((line = errorReader.readLine()) != null) {
                errorBuilder.append(line).append("\n");
            }
            error = errorBuilder.toString();
            
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            error = "Execution error: " + e.getMessage();
        }
        
        return new ExecutionResult(output, error);
    }
    
    private static class ExecutionResult {
        private final String output;
        private final String error;
        
        public ExecutionResult(String output, String error) {
            this.output = output;
            this.error = error;
        }
        
        public String getOutput() {
            return output;
        }
        
        public String getError() {
            return error;
        }
    }
}    
