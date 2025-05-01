package com.example.controller;

import com.example.common.Result;
import com.example.entity.Announcement;
import com.example.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/list")
    public Result listAnnouncements() {
        List<Announcement> announcements = announcementService.listAnnouncements();
        return Result.success(announcements);
    }

    @GetMapping("/{id}")
    public Result getAnnouncement(@PathVariable Long id) {
        Announcement announcement = announcementService.getAnnouncementById(id);
        if (announcement != null) {
            return Result.success(announcement);
        } else {
            return Result.error("公告不存在");
        }
    }
}    