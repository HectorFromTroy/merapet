package com.nazipov.merapet.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public class UserController {
    @GetMapping("/users/{userId}")
    public void retrieveUser() {
        
    }
    @GetMapping("/users/all:qwe")
    public String retrieveUsers() {
        return "sssa";
    }
    @PostMapping("/users/add")
    public void addUser() {

    }
    @PutMapping("/users/{userId}")
    public void editUser() {
        
    }
    @DeleteMapping("/users/{userId}")
    public void deleteUser() {
        
    }
}
