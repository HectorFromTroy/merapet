package com.nazipov.merapet.controller;

import javax.validation.Valid;

import com.nazipov.merapet.entities.MyUser;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class ContactsController {
    
    @GetMapping("/{userId}/contacts")
    public void retrieveContacts(@PathVariable("userId") String userId) {
        
    }

    @PostMapping("/{userId}/contacts")
    public void addContact(
        @PathVariable("userId") String userId,
        @Valid @RequestBody final Mono<MyUser> contact
    ) {

    }

    @DeleteMapping("/{userId}")
    public void deleteContact(
        @PathVariable("userId") String userId
    ) {
        
    }

}
