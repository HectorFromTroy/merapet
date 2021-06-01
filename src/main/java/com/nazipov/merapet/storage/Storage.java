package com.nazipov.merapet.storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import com.nazipov.merapet.entities.MyUser;

public class Storage {
    // String - user id
    private static HashMap<String, MyUser> users = new HashMap<>();
    // Key - user id, value - set of contact id's
    private static HashMap<String, HashSet<String>> contacts = new HashMap<>();

    public String getUUID() {
        String uuid = UUID.randomUUID().toString();
        while (users.containsKey(uuid)) {
            uuid = UUID.randomUUID().toString();
        }
        return uuid;
    }
}
