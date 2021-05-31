package com.nazipov.merapet.entities;

import java.time.LocalDateTime;

import com.nazipov.merapet.utils.Gender;

public class MyUser {
    private final String username;
    private final String password;
    private final String email;
    private final LocalDateTime dateOfBirth;
    private final Gender gender;
}