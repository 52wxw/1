package com.example.controller;

import com.example.dto.request.UpdateUserRequest;
import com.example.dto.response.ApiResponse;
import com.example.dto.response.UserResponse;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.security.CurrentUser;
import com.example.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserResponse> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        User user = userService.getUserById(currentUser.getId());
        return ResponseEntity.ok(new UserResponse(user));
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(new UserResponse(user));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponse> responses = users.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request,
            @CurrentUser UserPrincipal currentUser
    ) {
        // 只有管理员或用户本人可以修改
        if (!currentUser.getId().equals(id) && !currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return new ResponseEntity<>(
                    new ApiResponse<>(false, "无权限修改此用户"),
                    HttpStatus.FORBIDDEN
            );
        }

        User updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(new UserResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "用户删除成功"));
    }
}
