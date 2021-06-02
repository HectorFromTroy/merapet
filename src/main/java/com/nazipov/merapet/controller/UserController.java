package com.nazipov.merapet.controller;

import javax.validation.Valid;

import com.nazipov.merapet.dto.UserInfo;
import com.nazipov.merapet.entities.MyUser;
import com.nazipov.merapet.mapper.UserMapper;
import com.nazipov.merapet.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{userId}")
    public void retrieveUser(@PathVariable("userId") String userId) {
        
    }

    // only admin, in future
    @GetMapping("/users/all")
    public String retrieveUsers() {
        return "ssai";
    }

    @PostMapping("/users")
    public Mono<MyUser> createUser(
        @Valid @RequestBody final Mono<UserInfo> user
    ) {
        return user
            .map(UserMapper::mapToMyUserFromUserInfo)
            .flatMap(userService::saveUser).onErrorResume(e -> {
                logger.error(e.toString());
                return Mono.empty();
            });
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
