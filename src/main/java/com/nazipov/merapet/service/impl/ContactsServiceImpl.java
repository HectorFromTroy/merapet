package com.nazipov.merapet.service.impl;

import com.nazipov.merapet.entities.Contact;
import com.nazipov.merapet.entities.MyUser;
import com.nazipov.merapet.service.ContactsService;
import com.nazipov.merapet.storage.Storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ContactsServiceImpl implements ContactsService {

    private final Storage storage;
    private static final Logger logger = LoggerFactory.getLogger(ContactsServiceImpl.class);

    public ContactsServiceImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Mono<Collection<Contact>> retrieveContacts(String userId) {
        return Mono.just(storage.retrieveContacts(userId))
            .doOnNext(signal -> logger.info(String.format("Retrieved all user id: %s contacts", userId)));
    }

    @Override
    public Mono<Contact> addContact(String userId, Contact contact) {
        return Mono.just(storage.saveContact(userId, contact))
            .doOnNext(c -> logger.info(String.format("Added contact: %s with name: '%s' for user: %s", 
                    c.getUserId(), 
                    c.getName(), 
                    userId
                )
            ));
    }

    @Override
    public Mono<List<Contact>> addContacts(String userId, List<Contact> contacts) {
        return Flux.fromIterable(contacts)
                .flatMap(c -> addContact(userId, c))
                .collectList()
                .doOnNext(c -> logger.info(String.format("Added list of contacts %s%n for user: '%s'", contacts, userId)));
    }

    @Override
    public Mono<Contact> editContact(String userId, Contact contact) {
        String contactId = contact.getContactId();
        if (!storage.hasContact(userId, contactId)) {
            return Mono.error(new IllegalArgumentException(String.format("User: %s%n don't have contact: %s", userId, contactId)));
        }
        return Mono.just(storage.editContact(userId, contact))
            .doOnNext(c -> logger.info(String.format("Edited contact: %s with name: '%s' for user: %s", 
                    c.getUserId(), 
                    c.getName(), 
                    userId
                )
            ));
    }

    @Override
    public Mono<List<Contact>> editContacts(String userId, List<Contact> contacts) {
        return Flux.fromIterable(contacts)
                .flatMap(c -> editContact(userId, c))
                .collectList()
                .doOnNext(c -> logger.info(String.format("Edited list of contacts %s%n for user: '%s'", contacts, userId)));
    }

    @Override
    public Mono<Contact> deleteContact(String userId, Contact contact) {
        Optional<MyUser> user = storage.retrieveUser(userId);
        if (user.isEmpty()) {
            return Mono.error(new IllegalArgumentException(String.format("User: %s doesn't exist", userId)));
        }
        
        String contactId = contact.getContactId();
        if (!storage.hasContact(userId, contactId)) {
            return Mono.error(new IllegalArgumentException(String.format("User: %s%n don't have contact: %s", userId, contactId)));
        }
        
        return Mono.just(storage.deleteContact(userId, contactId))
            .doOnNext(c -> logger.info(String.format("Deleted contact: %s with name: '%s' for user: %s", 
                    c.getUserId(), 
                    c.getName(), 
                    userId
                )
            ));
    }

    @Override
    public Mono<List<Contact>> deleteContacts(String userId, List<Contact> contacts) {
        return Flux.fromIterable(contacts)
                .flatMap(c -> deleteContact(userId, c))
                .collectList()
                .doOnNext(c -> logger.info(String.format("Deleted list of contacts %s%n for user: '%s'", contacts, userId)));
    }

}
