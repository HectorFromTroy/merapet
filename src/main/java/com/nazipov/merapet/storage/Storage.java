package com.nazipov.merapet.storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import com.nazipov.merapet.entities.MyUser;

import org.springframework.stereotype.Component;

@Component
public class Storage {
    // String - user id
    private HashMap<String, MyUser> users = new HashMap<>();
    // Key - user id, value - set of contact id's
    private HashMap<String, HashSet<String>> contacts = new HashMap<>();

    private String getUUID() {
        String uuid = UUID.randomUUID().toString();
        while (users.containsKey(uuid)) {
            uuid = UUID.randomUUID().toString();
        }
        return uuid;
    }

    public String saveUser(MyUser user) throws Exception {
        if (users.values().stream().anyMatch(user::equals)) {
            // user already exists
            // TODO dedicated exception
            throw new Exception();
        }
        String userId = getUUID();
        user.setUserId(userId);
        users.put(userId, user);
        return userId;
    }
}
