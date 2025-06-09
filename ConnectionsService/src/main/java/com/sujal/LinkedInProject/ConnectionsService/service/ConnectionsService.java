package com.sujal.LinkedInProject.ConnectionsService.service;


import com.sujal.LinkedInProject.ConnectionsService.auth.AuthContextHolder;
import com.sujal.LinkedInProject.ConnectionsService.entity.Person;
import com.sujal.LinkedInProject.ConnectionsService.exceptions.BadRequestException;
import com.sujal.LinkedInProject.ConnectionsService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionsService {

    private final PersonRepository personRepository;

    public List<Person> getFirstDegreeConnectionsOfUser(Long userId){
        log.info("Getting first degree Connections of user with ID:{}",userId);

        return personRepository.getFirstDegreeConnections(userId);
    }

    public void sendConnectionRequest(Long receiverId){
        Long senderId = AuthContextHolder.getCurrentUserId();
        log.info("Sending connection request from user: {}, to receiver: {}",senderId,receiverId);

        if(senderId.equals(receiverId)){
            throw new BadRequestException( "Sender Id and Receiver Id cannot be same");
        }

        boolean alreadySentRequest = personRepository.connectionRequestExists(senderId,receiverId);
        if(alreadySentRequest){
            throw new BadRequestException("Connection request already exists,cannot send again");
        }
        boolean alreadyConnected = personRepository.alreadyConnected(senderId,receiverId);
        if(alreadyConnected){
            throw new BadRequestException("Already connected users, cannot add connection request");
        }

        log.info("Successfully sent the connection request");
        personRepository.addConnectionRequest(senderId,receiverId);

    }

    public void acceptConnectionRequest(Long senderId) {
        Long receiverId = AuthContextHolder.getCurrentUserId();

        log.info("Accepting connection request from senderId: {}, by receiverId: {}", senderId, receiverId);
        if (senderId.equals(receiverId)) {
            throw new BadRequestException("Sender Id and Receiver Id cannot be same");
        }

        boolean alreadyConnected = personRepository.alreadyConnected(senderId, receiverId);
        if (alreadyConnected) {
            throw new BadRequestException("Already connected users, cannot add connection request");
        }

        boolean alreadySentRequest = personRepository.connectionRequestExists(senderId, receiverId);
        if (!alreadySentRequest) {
            throw new BadRequestException("No Connection request exists,cannot accept the request");
        }

        personRepository.acceptConnectionRequest(senderId, receiverId);
        log.info("Successfully accepted the connection request from senderId: {},by receiverId: {}", senderId, receiverId);

    }

    public void rejectConnectionRequest(Long senderId){
        Long receiverId = AuthContextHolder.getCurrentUserId();
        log.info("Rejecting a connection request with senderId: {}, receiverId: {}",senderId,receiverId);

        if (senderId.equals(receiverId)) {
            throw new BadRequestException("Sender Id and Receiver Id cannot be same");
        }

        boolean alreadySentRequest = personRepository.connectionRequestExists(senderId, receiverId);
        if (!alreadySentRequest) {
            throw new BadRequestException("No Connection request exists,cannot reject the request");
        }

        personRepository.rejectConnectionRequest(senderId,receiverId);
        log.info("Successfully rejected the connection request from senderId: {},by receiverId: {}", senderId, receiverId);
    }
}