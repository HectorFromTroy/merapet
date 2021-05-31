package com.nazipov.merapet.controller;

import javax.validation.Valid;

import com.nazipov.merapet.dto.UserInfo;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import reactor.core.publisher.Mono;

public class UserController {

    @GetMapping("/{userId}")
    public void retrieveUser(@PathVariable("userId") String userId) {
        
    }

    @GetMapping("/{userId}/all")
    public void retrieveUsers(@PathVariable("userId") String userId) {
        
    }

    @PostMapping("/{userId}")
    public void addUser(
        @PathVariable("userId") String userId,
        @Valid @RequestBody final Mono<UserInfo> contact
    ) {

    }

    @PutMapping("/{userId}")
    public void editUser(
        @PathVariable("userId") String userId,
    ) {
        
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(
        @PathVariable("userId") String userId,
    ) {
        
    }
    
}
