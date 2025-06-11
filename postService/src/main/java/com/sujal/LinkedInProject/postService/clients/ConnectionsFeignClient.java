package com.sujal.LinkedInProject.postService.clients;

import com.sujal.LinkedInProject.postService.entities.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "connections-service",path = "/connections",url = "${CONNECTIONS_SERVICE_URI:}")
public interface ConnectionsFeignClient {

    @GetMapping("/core/{userId}/first-degree")
    List<Person> getFirstDegreeConnections(@PathVariable Long userId);
}
