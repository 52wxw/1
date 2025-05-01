package com.example.repository;

import com.example.entity.Announcement;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findByAuthor(User author);

    List<Announcement> findByIsPublic(Boolean isPublic);

    List<Announcement> findTop10ByOrderByCreatedAtDesc();
}
