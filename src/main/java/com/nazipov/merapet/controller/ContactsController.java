package com.nazipov.merapet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactsController {
    
    @GetMapping("/{userId}/contacts")
    public void retrieveContacts(@PathVariable("userId") String userId) {
        
    }

    // Array of contacts (can be arr of 1 elem)
    @PostMapping("/{userId}/contacts")
    public void addContacts(
        @PathVariable("userId") String userId
        // TODO
    ) {

    }

    @DeleteMapping("/{userId}")
    public void deleteContacts(
        @PathVariable("userId") String userId
    ) {
        
    }

}
