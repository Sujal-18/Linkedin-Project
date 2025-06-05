package com.sujal.linkedInProject.postService.clients;

import com.sujal.linkedInProject.postService.entities.Person;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "connections-service",path = "/connections")
public interface ConnectionsFeignClient {

    @GetMapping("/core/{userId}/first-degree")
    List<Person> getFirstDegreeConnections(@PathVariable Long userId);
}
