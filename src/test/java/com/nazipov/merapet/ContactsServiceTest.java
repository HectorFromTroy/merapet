package com.nazipov.merapet;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.nazipov.merapet.dto.ContactInfo;
import com.nazipov.merapet.entities.Contact;
import com.nazipov.merapet.service.impl.ContactsServiceImpl;
import com.nazipov.merapet.storage.Storage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import reactor.test.StepVerifier;

@AutoConfigureWebTestClient(timeout = "10000")
@SpringBootTest(classes = ContactsServiceImpl.class)
public class ContactsServiceTest extends BaseTest {

    @SpyBean
    // TODO
    private ContactsServiceImpl contactsService;

    @MockBean
    private Storage storage;

    private String mockUserId = "qwe";

    @Test
    public void testContactsRetrieving() {
        List<Contact> contactsToRetrieve = List.of(generateContact());
        contactsService = new ContactsServiceImpl(storage);
        when(storage.isUserExistById(mockUserId)).thenReturn(true);
        when(storage.retrieveContacts(mockUserId)).thenReturn(contactsToRetrieve);
        StepVerifier.create(contactsService.retrieveContacts(mockUserId))
            .expectNextMatches(contacts -> contacts.size() == contactsToRetrieve.size())
            .verifyComplete();
    }

    @Test
    public void testContactsRetrievingOfNonExistingUser() {
        contactsService = new ContactsServiceImpl(storage);
        when(storage.isUserExistById(mockUserId)).thenReturn(false);
        StepVerifier.create(contactsService.retrieveContacts(mockUserId))
            .expectError(IllegalArgumentException.class)
            .verify();
    }

    @Test
    public void testContactsAdding() {
        Contact contactToAdd = generateContact();
        contactsService = new ContactsServiceImpl(storage);
        when(storage.isUserExistById(mockUserId)).thenReturn(true);

        when(storage.saveContact(mockUserId, contactToAdd)).thenReturn(contactToAdd);
        StepVerifier.create(contactsService.addContact(mockUserId, contactToAdd))
            .assertNext(elem -> System.out.println("Next: " + elem))
            .verifyComplete();

        List<Contact> contactsToAdd = List.of(contactToAdd);
        when(storage.saveContact(mockUserId, contactToAdd)).thenReturn(contactToAdd);
        StepVerifier.create(contactsService.addContacts(mockUserId, contactsToAdd))
            .expectNextMatches(contacts -> contacts.size() == contactsToAdd.size())
            .verifyComplete();
    }

    @Test
    public void testContactsAddingToNonExistingUser() {
        Contact contactToAdd = generateContact();
        contactsService = new ContactsServiceImpl(storage);
        when(storage.isUserExistById(mockUserId)).thenReturn(false);

        when(storage.saveContact(mockUserId, contactToAdd)).thenReturn(contactToAdd);
        StepVerifier.create(contactsService.addContact(mockUserId, contactToAdd))
            .expectError(IllegalArgumentException.class)
            .verify();

        List<Contact> contactsToAdd = List.of(contactToAdd);
        when(storage.saveContact(mockUserId, contactToAdd)).thenReturn(contactToAdd);
        StepVerifier.create(contactsService.addContacts(mockUserId, contactsToAdd))
            .expectError(IllegalArgumentException.class)
            .verify();
    }

    @Test
    public void testContactsEditing() {
        Contact contactToEdit = generateContact();
        contactsService = new ContactsServiceImpl(storage);
        when(storage.isUserExistById(mockUserId)).thenReturn(true);
        when(storage.hasContact(mockUserId, contactToEdit.getContactId())).thenReturn(true);
        when(storage.editContact(mockUserId, contactToEdit)).thenReturn(contactToEdit);

        StepVerifier.create(contactsService.editContact(mockUserId, contactToEdit))
            .assertNext(elem -> System.out.println("Next: " + elem))
            .verifyComplete();

        List<Contact> contactsToEdit = List.of(contactToEdit);
        StepVerifier.create(contactsService.editContacts(mockUserId, contactsToEdit))
            .expectNextMatches(contacts -> contacts.size() == contactsToEdit.size())
            .verifyComplete();
    }

    @Test
    public void testContactsEditingOfNonExistingUser() {
        Contact contactToEdit = generateContact();
        contactsService = new ContactsServiceImpl(storage);
        when(storage.isUserExistById(mockUserId)).thenReturn(false);
        when(storage.editContact(mockUserId, contactToEdit)).thenReturn(contactToEdit);

        StepVerifier.create(contactsService.editContact(mockUserId, contactToEdit))
            .expectError(IllegalArgumentException.class)
            .verify();

        List<Contact> contactsToEdit = List.of(contactToEdit);
        StepVerifier.create(contactsService.editContacts(mockUserId, contactsToEdit))
            .expectError(IllegalArgumentException.class)
            .verify();
    }

    @Test
    public void testContactsEditingOfNonExistingContact() {
        Contact contactToEdit = generateContact();
        contactsService = new ContactsServiceImpl(storage);
        when(storage.isUserExistById(mockUserId)).thenReturn(true);
        when(storage.hasContact(mockUserId, contactToEdit.getContactId())).thenReturn(false);
        when(storage.editContact(mockUserId, contactToEdit)).thenReturn(contactToEdit);

        StepVerifier.create(contactsService.editContact(mockUserId, contactToEdit))
            .expectError(IllegalArgumentException.class)
            .verify();

        List<Contact> contactsToEdit = List.of(contactToEdit);
        StepVerifier.create(contactsService.editContacts(mockUserId, contactsToEdit))
            .expectError(IllegalArgumentException.class)
            .verify();
    }

}
