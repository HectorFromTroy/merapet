package com.nazipov.merapet.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String email;
    private LocalDateTime dateOfBirth;
    private Gender gender;

    @JsonCreator(mode = Mode.PROPERTIES)
    public UserInfo(
        @JsonProperty("username") String username,
        @JsonProperty("password") String password,
        @JsonProperty("email") String email,
        @JsonProperty("dateOfBirth") LocalDateTime dateOfBirth,
        @JsonProperty("gender") Gender gender
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, dateOfBirth, gender);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof UserInfo))
            return false;
        UserInfo other = (UserInfo) obj;
        return Objects.equals(username, other.username);
    }

    @Override
    public String toString() {
        return String.format("username: %s, email: %s", username, email);
    }
    
}
