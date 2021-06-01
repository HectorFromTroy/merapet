package com.nazipov.merapet.controller;

import javax.validation.Valid;

import com.nazipov.merapet.dto.UserInfo;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class UserController {

    @GetMapping("/users/{userId}")
    public void retrieveUser(@PathVariable("userId") String userId) {
        
    }

    // only admin, in future
    @GetMapping("/users/all")
    public String retrieveUsers() {
        return "ssai";
    }

    @PostMapping("/users")
    public void createUser(
        @PathVariable("userId") String userId,
        @Valid @RequestBody final Mono<UserInfo> user
    ) {

    }

    @PutMapping("/users/{userId}")
    public void editUser(
        @PathVariable("userId") String userId
    ) {
        
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(
        @PathVariable("userId") String userId
    ) {
        
    }
    
}
