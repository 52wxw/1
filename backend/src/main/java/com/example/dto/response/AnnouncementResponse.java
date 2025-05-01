package com.example.dto.response;

import com.example.entity.Announcement;

import java.time.LocalDateTime;

public record AnnouncementResponse(
    Long id,
    String title,
    String content,
    Boolean isPublic,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    UserResponse author
) {
    // 从Announcement实体转换
    public AnnouncementResponse(Announcement announcement) {
        this(
            announcement.getId(),
            announcement.getTitle(),
            announcement.getContent(),
            announcement.getIsPublic(),
            announcement.getCreatedAt(),
            announcement.getUpdatedAt(),
            new UserResponse(announcement.getAuthor())
        );
    }
}
