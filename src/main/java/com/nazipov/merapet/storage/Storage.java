package com.nazipov.merapet.storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import com.nazipov.merapet.entities.MyUser;

import org.springframework.stereotype.Component;

@Component
public class Storage {
    
    //private HashMap<String, HashMap<String, MyUser>> usersContacts = new HashMap<>();

    private HashMap<String, MyUser> allUsersById = new HashMap<>();
    private HashSet<String> usernames = new HashSet<>();
    private HashMap<String, HashSet<String>> userContacts = new HashMap<>();


    private String getUUID() {
        return UUID.randomUUID().toString();
    }

    public Optional<MyUser> retrieveUser(String userId) {
        return Optional.ofNullable(allUsersById.get(userId));
    }

    public boolean isUserExist(String username) {
        return usernames.contains(username);
    }

    public Collection<MyUser> retrieveUsers() {
        return allUsersById.values();
    }

    public MyUser saveUser(MyUser user) {
        String userId = getUUID();
        user.setUserId(userId);
        allUsersById.put(user.getUserId(), user);
        usernames.add(user.getUsername());
        return user;
    }

    public MyUser editUser(MyUser user) {
        allUsersById.put(user.getUserId(), user);
        return user;
    }

    public MyUser deleteUser(MyUser user) {
        allUsersById.remove(user.getUserId());
        usernames.remove(user.getUsername());
        return user;
    }

}
