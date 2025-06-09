package com.sujal.LinkedInProject.ConnectionsService.consumer;

import com.sujal.LinkedInProject.ConnectionsService.service.PersonService;
import com.sujal.LinkedInProject.userService.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceConsumer {

   private final PersonService personService;

    @KafkaListener(topics = "user-created-topic")
    public void handleUserCreatedTopic(UserCreatedEvent userCreatedEvent){

        log.info("handlePersonCreated: {}",userCreatedEvent);
        personService.createPerson(userCreatedEvent.getUserId(),userCreatedEvent.getName()); //to create person in neoAuraDB
    }
}
