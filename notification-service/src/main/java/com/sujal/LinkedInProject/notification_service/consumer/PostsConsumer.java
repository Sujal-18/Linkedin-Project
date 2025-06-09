package com.sujal.LinkedInProject.notification_service.consumer;

import com.sujal.LinkedInProject.notification_service.entities.Notification;
import com.sujal.LinkedInProject.postService.event.PostCreated;
import com.sujal.LinkedInProject.postService.event.PostLiked;
import com.sujal.LinkedInProject.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostsConsumer {

    private  final NotificationService notificationService;

    @KafkaListener(topics = "post-created-topic")
    public void handlePostCreatedTopic(PostCreated postCreated){
        log.info("Received notification: {]",postCreated);

        String message = String.format("Your connection with id: %d has created the post: %s",postCreated.getPostOwnerUserId(),postCreated.getContent());

        Notification notification = Notification.builder()
                .userId(postCreated.getConnectionUserId()) //to store userId of person whom I am sending notification because you need to store notification of each user using its Id
                .message(message)
                .build();

        notificationService.addNotification(notification);

    }

    @KafkaListener(topics = "post-liked-topic")
    public void handlePostLikedTopic(PostLiked postLiked){
        log.info("handlePostLiked: {}",postLiked);

        String message = String.format("User with id: %d has liked your post with id: %d",postLiked.getLikedByUserId(),postLiked.getPostId());

        Notification notification = Notification.builder()
                .message(message)
                .userId(postLiked.getOwnerUserId())
                .build();
        notificationService.addNotification(notification);

    }
}
