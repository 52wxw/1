package com.example.service;

import com.example.dto.request.AnnouncementRequest;
import com.example.dto.response.AnnouncementResponse;
import com.example.entity.Announcement;
import com.example.entity.User;

import java.util.List;

public interface AnnouncementService {

    AnnouncementResponse createAnnouncement(AnnouncementRequest request, User author);

    AnnouncementResponse getAnnouncementById(Long id);

    List<AnnouncementResponse> getAllAnnouncements();

    List<AnnouncementResponse> getAnnouncementsByAuthor(User author);

    List<AnnouncementResponse> getPublicAnnouncements();

    List<AnnouncementResponse> getLatestAnnouncements();

    AnnouncementResponse updateAnnouncement(Long id, AnnouncementRequest request, User currentUser);

    void deleteAnnouncement(Long id, User currentUser);
}
