package com.nazipov.merapet.service;

import reactor.core.publisher.Mono;

import com.nazipov.merapet.entities.Contact;

import java.util.Collection;
import java.util.List;

public interface ContactsService {
    Mono<Collection<Contact>> retrieveContacts(String userId);
    Mono<Contact> addContact(String userId, Contact contact);
    Mono<List<Contact>> addContacts(String userId, List<Contact> contacts);
    Mono<Contact> editContact(String userId, Contact contact);
    Mono<List<Contact>> editContacts(String userId, List<Contact> contacts);
    Mono<Contact> deleteContact(String userId, Contact contact);
    Mono<List<Contact>> deleteContacts(String userId, List<Contact> contacts);
}
