package com.nazipov.merapet.storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import com.nazipov.merapet.entities.MyUser;

import org.springframework.stereotype.Component;

@Component
public class Storage {
    
    //private HashMap<String, HashMap<String, MyUser>> usersContacts = new HashMap<>();

    private HashMap<String, MyUser> allUsers = new HashMap<>();
    private HashMap<String, HashSet<String>> userContacts = new HashMap<>();


    private String getUUID() {
        return UUID.randomUUID().toString();
    }

    public MyUser saveUser(MyUser user) {
        String username = user.getUsername();
        if (allUsers.containsKey(username)) {
            throw new IllegalArgumentException();
        }
        String userId = getUUID();
        user.setUserId(userId);
        allUsers.put(username, user);
        return user;
    }


    
}
