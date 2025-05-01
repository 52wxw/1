package com.example.service;

import com.example.entity.Announcement;

import java.util.List;

public interface AnnouncementService {

    List<Announcement> listAnnouncements();

    Announcement getAnnouncementById(Long id);
}    