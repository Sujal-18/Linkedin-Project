package com.sujal.LinkedInProject.notification_service.service;

import com.sujal.LinkedInProject.notification_service.entities.Notification;
import com.sujal.LinkedInProject.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    //to store notification in db
    public void addNotification(Notification notification){
        log.info("Adding notification to db, message: {}",notification.getMessage());
        notification = notificationRepository.save(notification);

        //also can use sendMailer to send email
        //FCM to send notification on android

    }
}
