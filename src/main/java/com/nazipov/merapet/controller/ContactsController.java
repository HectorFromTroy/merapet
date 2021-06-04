package com.nazipov.merapet.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import com.nazipov.merapet.dto.ContactInfo;
import com.nazipov.merapet.entities.Contact;
import com.nazipov.merapet.entities.MyUser;
import com.nazipov.merapet.logging.LoggingStrings;
import com.nazipov.merapet.mapper.ContactMapper;
import com.nazipov.merapet.service.ContactsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class ContactsController {

    private final ContactsService contactsService;
    private static final Logger logger = LoggerFactory.getLogger(ContactsController.class);

    public ContactsController(ContactsService contactsService) {
        this.contactsService = contactsService;
    }
    
    @GetMapping("/{userId}/contacts")
    public Mono<Collection<Contact>> retrieveContacts(@PathVariable("userId") String userId) {
        return contactsService.retrieveContacts(userId)
            .onErrorResume(e -> {
                logger.error(LoggingStrings.errorString(e));
                return Mono.empty();
            });
    }

    @PostMapping("/{userId}/contact")
    public Mono<Contact> addContact(
        @PathVariable("userId") String userId,
        @Valid @RequestBody final Mono<ContactInfo> contact
    ) {
        return contact
            .map(ContactMapper::mapToContactFromContactInfo)
            .flatMap(cont -> contactsService.addContact(userId, cont)).onErrorResume(e -> {
                logger.error(LoggingStrings.errorString(e));
                return Mono.empty();
            });
    }

    @PostMapping("/{userId}/contacts")
    public Mono<List<Contact>> addContacts(
        @PathVariable("userId") String userId,
        @Valid @RequestBody final Mono<List<ContactInfo>> contactsList
    ) {
        return contactsList
            .map(ContactMapper::mapToContactFromContactInfo)
            .flatMap(conts -> contactsService.addContacts(userId, conts)).onErrorResume(e -> {
                logger.error(LoggingStrings.errorString(e));
                return Mono.empty();
            });
    }

    @PutMapping("/{userId}/contact")
    public Mono<Contact> editContact(
        @PathVariable("userId") String userId,
        @Valid @RequestBody final Mono<ContactInfo> contact
    ) {
        return contact
            .map(ContactMapper::mapToContactFromContactInfo)
            .flatMap(cont -> contactsService.editContact(userId, cont)).onErrorResume(e -> {
                logger.error(LoggingStrings.errorString(e));
                return Mono.empty();
            });
    }

    @PutMapping("/{userId}/contacts")
    public Mono<List<Contact>> editContacts(
        @PathVariable("userId") String userId,
        @Valid @RequestBody final Mono<List<ContactInfo>> contactsList
    ) {
        return contactsList
            .map(ContactMapper::mapToContactFromContactInfo)
            .flatMap(conts -> contactsService.editContacts(userId, conts)).onErrorResume(e -> {
                logger.error(LoggingStrings.errorString(e));
                return Mono.empty();
            });
    }

    @DeleteMapping("/{userId}/contact")
    public Mono<Contact> deleteContact(
        @PathVariable("userId") String userId,
        @Valid @RequestBody final Mono<ContactInfo> contact
    ) {
        return contact
            .map(ContactMapper::mapToContactFromContactInfo)
            .flatMap(cont -> contactsService.deleteContact(userId, cont)).onErrorResume(e -> {
                logger.error(LoggingStrings.errorString(e));
                return Mono.empty();
            });
    }

    @DeleteMapping("/{userId}/contacts")
    public Mono<List<Contact>> deleteContacts(
        @PathVariable("userId") String userId,
        @Valid @RequestBody final Mono<List<ContactInfo>> contactsList
    ) {
        return contactsList
            .map(ContactMapper::mapToContactFromContactInfo)
            .flatMap(conts -> contactsService.deleteContacts(userId, conts)).onErrorResume(e -> {
                logger.error(LoggingStrings.errorString(e));
                return Mono.empty();
            });
    }

}
