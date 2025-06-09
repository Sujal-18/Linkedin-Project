package com.sujal.LinkedInProject.ConnectionsService.controller;



import com.sujal.LinkedInProject.ConnectionsService.entity.Person;
import com.sujal.LinkedInProject.ConnectionsService.service.ConnectionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
@Slf4j
public class ConnectionsController {
    private final ConnectionsService connectionsService;

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<Person>> getFirstDegreeConnections(@PathVariable Long userId, @RequestHeader("X-User-Id") String userIdFromHeader){
        log.info("User id is: {}",userId);
        log.info("User id from Header: {}",userIdFromHeader);
        List<Person> personList = connectionsService.getFirstDegreeConnectionsOfUser(Long.valueOf(userIdFromHeader));
        System.out.println(personList);
        return ResponseEntity.ok(personList);
    }

    @PostMapping("/request/{receiverId}")
    public ResponseEntity<Void> sendConnectionRequest(@PathVariable Long receiverId){
        connectionsService.sendConnectionRequest(receiverId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/accept/{senderId}")
    public ResponseEntity<Void> acceptConnectionRequest(@PathVariable Long senderId){
        connectionsService.acceptConnectionRequest(senderId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reject/{senderId}")
    public ResponseEntity<Void> rejectConnectionRequest(@PathVariable Long senderId){
        connectionsService.rejectConnectionRequest(senderId);
        return ResponseEntity.noContent().build();
    }
}
