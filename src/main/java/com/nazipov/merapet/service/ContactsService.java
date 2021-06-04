package com.nazipov.merapet.service;

import reactor.core.publisher.Mono;

import com.nazipov.merapet.entities.Contact;

import java.util.Collection;

public interface ContactsService {
    Mono<Collection<Contact>> retrieveContacts(String userId);
    Mono<Contact> addContact(String userId, Contact contact);
    Mono<Contact> editContact(String userId, Contact contact);
    Mono<Contact> deleteContact(String userId, String contactId);
}
