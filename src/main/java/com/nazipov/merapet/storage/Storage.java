package com.nazipov.merapet.storage;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import com.nazipov.merapet.dto.Gender;
import com.nazipov.merapet.entities.Contact;
import com.nazipov.merapet.entities.MyUser;

import org.springframework.stereotype.Component;

@Component
public class Storage {

    private HashMap<String, MyUser> allUsersById = new HashMap<>();
    private HashSet<String> usernames = new HashSet<>();
    // user owner id, contact id, contact
    private HashMap<String, HashMap<String, Contact>> userContacts = new HashMap<>();

    private String getUUID() {
        return UUID.randomUUID().toString();
    }

    public boolean isUserExistById(String userId) {
        return allUsersById.containsKey(userId);
    }

    public boolean isUserExistByUsername(String username) {
        return usernames.contains(username);
    }

    public boolean hasContact(String userId, String contactId) {
        return userContacts.get(userId).containsKey(contactId);
    }

    public Optional<MyUser> retrieveUser(String userId) {
        return Optional.ofNullable(allUsersById.get(userId));
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

    public Contact saveContact(String userId, Contact contact) {
        String contactId = getUUID();
        contact.setContactId(contactId);
        userContacts.computeIfAbsent(userId, k -> new HashMap<>()).put(contactId, contact);
        return contact;
    }

    public Optional<Contact> retrieveContact(String userId, String contactId) {
        return Optional.ofNullable(userContacts.get(userId).get(contactId));
    }

    public Collection<Contact> retrieveContacts(String userId) {
        HashMap<String, Contact> contacts = userContacts.get(userId);
        return contacts != null ? contacts.values() : Collections.emptyList();
    }

    public Contact editContact(String userId, Contact contact) {
        userContacts.get(userId).put(contact.getContactId(), contact);
        return contact;
    }

    public Contact deleteContact(String userId, String contactId) {
        return userContacts.get(userId).remove(contactId);
    }

}
