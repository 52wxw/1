package com.example.service;

import com.example.dto.request.AnnouncementRequest;
import com.example.dto.response.AnnouncementResponse;
import com.example.entity.Announcement;
import com.example.entity.User;
import com.example.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Override
    public AnnouncementResponse createAnnouncement(AnnouncementRequest request, User author) {
        Announcement announcement = Announcement.builder()
            .title(request.title())
            .content(request.content())
            .isPublic(request.isPublic())
            .author(author)
            .build();

        Announcement savedAnnouncement = announcementRepository.save(announcement);
        return new AnnouncementResponse(savedAnnouncement);
    }

    @Override
    public AnnouncementResponse getAnnouncementById(Long id) {
        Announcement announcement = announcementRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("公告不存在"));
        return new AnnouncementResponse(announcement);
    }

    @Override
    public List<AnnouncementResponse> getAllAnnouncements() {
        return announcementRepository.findAll().stream()
            .map(AnnouncementResponse::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementResponse> getAnnouncementsByAuthor(User author) {
        return announcementRepository.findByAuthor(author).stream()
            .map(AnnouncementResponse::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementResponse> getPublicAnnouncements() {
        return announcementRepository.findByIsPublic(true).stream()
            .map(AnnouncementResponse::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementResponse> getLatestAnnouncements() {
        return announcementRepository.findTop10ByOrderByCreatedAtDesc().stream()
            .map(AnnouncementResponse::new)
            .collect(Collectors.toList());
    }

    @Override
    public AnnouncementResponse updateAnnouncement(Long id, AnnouncementRequest request, User currentUser) {
        Announcement announcement = announcementRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("公告不存在"));

        if (!announcement.getAuthor().getId().equals(currentUser.getId())) {
            throw new IllegalStateException("只有公告作者可以更新公告");
        }

        if (request.title() != null) {
            announcement.setTitle(request.title());
        }

        if (request.content() != null) {
            announcement.setContent(request.content());
        }

        if (request.isPublic() != null) {
            announcement.setIsPublic(request.isPublic());
        }

        Announcement updatedAnnouncement = announcementRepository.save(announcement);
        return new AnnouncementResponse(updatedAnnouncement);
    }

    @Override
    public void deleteAnnouncement(Long id, User currentUser) {
        Announcement announcement = announcementRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("公告不存在"));

        if (!announcement.getAuthor().getId().equals(currentUser.getId())) {
            throw new IllegalStateException("只有公告作者可以删除公告");
        }

        announcementRepository.delete(announcement);
    }
}
