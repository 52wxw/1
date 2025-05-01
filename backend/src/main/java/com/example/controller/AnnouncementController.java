package com.example.controller;

import com.example.dto.request.AnnouncementRequest;
import com.example.dto.response.ApiResponse;
import com.example.dto.response.AnnouncementResponse;
import com.example.entity.Announcement;
import com.example.service.AnnouncementService;
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
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AnnouncementResponse> createAnnouncement(
            @Valid @RequestBody AnnouncementRequest request,
            @CurrentUser UserPrincipal currentUser
    ) {
        Announcement announcement = announcementService.createAnnouncement(request, currentUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AnnouncementResponse(announcement));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AnnouncementResponse> getAnnouncementById(@PathVariable Long id) {
        Announcement announcement = announcementService.getAnnouncementById(id);
        return ResponseEntity.ok(new AnnouncementResponse(announcement));
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<AnnouncementResponse>> getAllAnnouncements() {
        List<Announcement> announcements = announcementService.getAllAnnouncements();
        List<AnnouncementResponse> responses = announcements.stream()
                .map(AnnouncementResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AnnouncementResponse> updateAnnouncement(
            @PathVariable Long id,
            @Valid @RequestBody AnnouncementRequest request,
            @CurrentUser UserPrincipal currentUser
    ) {
        Announcement announcement = announcementService.updateAnnouncement(
                id, request, currentUser.getId()
        );
        return ResponseEntity.ok(new AnnouncementResponse(announcement));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<?>> deleteAnnouncement(
            @PathVariable Long id,
            @CurrentUser UserPrincipal currentUser
    ) {
        announcementService.deleteAnnouncement(id, currentUser.getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "公告删除成功"));
    }
}
