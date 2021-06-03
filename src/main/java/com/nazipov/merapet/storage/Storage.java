package com.nazipov.merapet.storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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

    public MyUser retrieveUser(String userId) {
        MyUser existingUser = allUsersById.get(userId);
        if (existingUser == null) {
            throw new IllegalArgumentException("There is no such user :(((");
        }
        return existingUser;
    }

    public Collection<MyUser> retrieveUsers() {
        return allUsersById.values();
    }

    public MyUser saveUser(MyUser user) {
        String username = user.getUsername();
        if (usernames.contains(username)) {
            throw new IllegalArgumentException();
        }
        String userId = getUUID();
        user.setUserId(userId);
        allUsersById.put(user.getUserId(), user);
        usernames.add(username);
        return user;
    }

    public MyUser editUser(MyUser user) {
        MyUser existingUser = allUsersById.get(user.getUserId());
        if (existingUser == null) {
            throw new IllegalArgumentException();
        }
        allUsersById.put(user.getUserId(), user);
        return user;
    }

    public MyUser deleteUser(String userId) {
        MyUser userToDelete = allUsersById.get(userId);
        if (userToDelete == null) {
            throw new IllegalArgumentException("There is no such user :(((");
        }
        allUsersById.remove(userId);
        usernames.remove(userToDelete.getUsername());
        return userToDelete;
    }

}
