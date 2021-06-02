package com.nazipov.merapet.storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import com.nazipov.merapet.entities.MyUser;

import org.springframework.stereotype.Component;

@Component
public class Storage {
    
    private HashMap<String, HashMap<String, MyUser>> usersContacts = new HashMap<>();

    private String getUUID() {
        return UUID.randomUUID().toString();
    }

    public MyUser saveUser(MyUser user) {
        if (usersContacts.entrySet().stream()
            .map(entry -> entry.getValue().get(entry.getKey()))
            .anyMatch(user::equals)
        ) {
            // user already exists
            throw new IllegalArgumentException();
        }
        String userId = getUUID();
        user.setUserId(userId);
        usersContacts.computeIfAbsent(userId, k -> new HashMap<>()).put(userId, user);
        return user;
    }
}
