package com.sujal.LinkedInProject.ConnectionsService.service;


import com.sujal.LinkedInProject.ConnectionsService.entity.Person;
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

}


