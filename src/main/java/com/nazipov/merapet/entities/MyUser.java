package com.nazipov.merapet.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import com.nazipov.merapet.utils.Gender;

public class MyUser {

    private String userId;
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

    public String getUserId() {
        return userId;
    }

    // id will be injected while saving in Storage class
    public void setUserId(String userId) {
        this.userId = userId;
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

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, dateOfBirth, gender);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof MyUser))
            return false;
        MyUser other = (MyUser) obj;
        return Objects.equals(username, other.username) 
                && Objects.equals(email, other.email)
                && Objects.equals(password, other.password)
                && Objects.equals(dateOfBirth, other.dateOfBirth)
                && Objects.equals(gender, other.gender);
    }

    @Override
    public String toString() {
        return String.format("username: %s, email: %s", username, email);
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