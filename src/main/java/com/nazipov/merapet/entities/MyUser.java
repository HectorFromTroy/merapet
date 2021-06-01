package com.nazipov.merapet.entities;

import java.time.LocalDateTime;

import com.nazipov.merapet.utils.Gender;

public class MyUser {
    private final String username;
    private final String password;
    private final String email;
    private final LocalDateTime dateOfBirth;
    private final Gender gender;

    public MyUser(
        String username,
        String password,
        String email,
        LocalDateTime dateOfBirth,
        Gender gender
    ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String username;
        private String password;
        private String email;
        private LocalDateTime dateOfBirth;
        private Gender gender;

        private Builder() {}

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setDateOfBirth(LocalDateTime dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder setGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public MyUser build() {
            return new MyUser(username, password, email, dateOfBirth, gender);
        }
    }
}