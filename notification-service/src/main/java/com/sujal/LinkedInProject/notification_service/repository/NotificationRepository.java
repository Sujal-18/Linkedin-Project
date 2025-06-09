package com.sujal.LinkedInProject.notification_service.repository;

import com.sujal.LinkedInProject.notification_service.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
