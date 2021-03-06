package com.nazipov.merapet;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import com.nazipov.merapet.entities.Contact;
import com.nazipov.merapet.service.impl.ContactsServiceImpl;
import com.nazipov.merapet.storage.Storage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import reactor.test.StepVerifier;

@AutoConfigureWebTestClient(timeout = "10000")
@SpringBootTest(classes = ContactsServiceImpl.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContactsServiceTest extends BaseTest {

    @SpyBean
    private ContactsServiceImpl contactsService;

    @MockBean
    private Storage storage;

    private String mockUserId = "qwe";

    @BeforeAll
    public void setUp() {
        contactsService = new ContactsServiceImpl(storage);
    }

    @Test
    public void testContactsRetrieving() {
        List<Contact> contactsToRetrieve = List.of(generateContact());
        when(storage.isUserExistById(mockUserId)).thenReturn(true);
        when(storage.retrieveContacts(mockUserId)).thenReturn(contactsToRetrieve);
        StepVerifier.create(contactsService.retrieveContacts(mockUserId))
            .expectNextMatches(contacts -> contacts.size() == contactsToRetrieve.size())
            .verifyComplete();
    }

    @Test
    public void testContactsRetrievingOfNonExistingUser() {
        when(storage.isUserExistById(mockUserId)).thenReturn(false);
        StepVerifier.create(contactsService.retrieveContacts(mockUserId))
            .expectError(IllegalArgumentException.class)
            .verify();
    }

    @Test
    public void testContactsAdding() {
        Contact contactToAdd = generateContact();
        when(storage.isUserExistById(mockUserId)).thenReturn(true);
        when(storage.saveContact(mockUserId, contactToAdd)).thenReturn(contactToAdd);

        StepVerifier.create(contactsService.addContact(mockUserId, contactToAdd))
            .assertNext(elem -> System.out.println("Next: " + elem))
            .verifyComplete();

        List<Contact> contactsToAdd = List.of(contactToAdd);
        verify(storage, times(contactsToAdd.size())).saveContact(eq(mockUserId), any(Contact.class));
        StepVerifier.create(contactsService.addContacts(mockUserId, contactsToAdd))
            .expectNextMatches(contacts -> contacts.size() == contactsToAdd.size())
            .verifyComplete();
    }

    @Test
    public void testContactsAddingToNonExistingUser() {
        Contact contactToAdd = generateContact();
        when(storage.isUserExistById(mockUserId)).thenReturn(false);

        when(storage.saveContact(mockUserId, contactToAdd)).thenReturn(contactToAdd);
        StepVerifier.create(contactsService.addContact(mockUserId, contactToAdd))
            .expectError(IllegalArgumentException.class)
            .verify();

        List<Contact> contactsToAdd = List.of(contactToAdd);
        StepVerifier.create(contactsService.addContacts(mockUserId, contactsToAdd))
            .expectError(IllegalArgumentException.class)
            .verify();
    }

    @Test
    public void testContactsEditing() {
        Contact contactToEdit = generateContact();
        when(storage.isUserExistById(mockUserId)).thenReturn(true);
        when(storage.hasContact(mockUserId, contactToEdit.getContactId())).thenReturn(true);
        when(storage.editContact(mockUserId, contactToEdit)).thenReturn(contactToEdit);

        StepVerifier.create(contactsService.editContact(mockUserId, contactToEdit))
            .assertNext(elem -> System.out.println("Next: " + elem))
            .verifyComplete();

        List<Contact> contactsToEdit = List.of(contactToEdit);
        verify(storage, times(contactsToEdit.size())).editContact(eq(mockUserId), any(Contact.class));
        StepVerifier.create(contactsService.editContacts(mockUserId, contactsToEdit))
            .expectNextMatches(contacts -> contacts.size() == contactsToEdit.size())
            .verifyComplete();
    }

    @Test
    public void testContactsEditingOfNonExistingUser() {
        Contact contactToEdit = generateContact();
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
    public void testContactsEditingWithNonExistingContact() {
        Contact contactToEdit = generateContact();
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

    @Test
    public void testContactsDeleting() {
        Contact contactToDelete = generateContact();
        String contactIdToDelete = contactToDelete.getContactId();
        when(storage.isUserExistById(mockUserId)).thenReturn(true);
        when(storage.hasContact(mockUserId, contactIdToDelete)).thenReturn(true);
        when(storage.deleteContact(mockUserId, contactIdToDelete)).thenReturn(contactToDelete);

        StepVerifier.create(contactsService.deleteContact(mockUserId, contactIdToDelete))
            .assertNext(elem -> System.out.println("Next: " + elem))
            .verifyComplete();

        List<String> contactsToDelete = List.of(contactIdToDelete);
        verify(storage, times(contactsToDelete.size())).deleteContact(eq(mockUserId), any(String.class));
        StepVerifier.create(contactsService.deleteContacts(mockUserId, contactsToDelete))
            .expectNextMatches(contacts -> contacts.size() == contactsToDelete.size())
            .verifyComplete();
    }

    @Test
    public void testContactsDeletingOfNonExistingUser() {
        Contact contactToDelete = generateContact();
        String contactIdToDelete = contactToDelete.getContactId();
        when(storage.isUserExistById(mockUserId)).thenReturn(false);
        when(storage.deleteContact(mockUserId, contactIdToDelete)).thenReturn(contactToDelete);

        StepVerifier.create(contactsService.deleteContact(mockUserId, contactIdToDelete))
            .expectError(IllegalArgumentException.class)
            .verify();

        List<String> contactsToDelete = List.of(contactIdToDelete);
        StepVerifier.create(contactsService.deleteContacts(mockUserId, contactsToDelete))
            .expectError(IllegalArgumentException.class)
            .verify();
    }

    @Test
    public void testContactsDeletingWithNonExistingContact() {
        Contact contactToDelete = generateContact();
        String contactIdToDelete = contactToDelete.getContactId();
        when(storage.isUserExistById(mockUserId)).thenReturn(true);
        when(storage.hasContact(mockUserId, contactToDelete.getContactId())).thenReturn(false);
        when(storage.deleteContact(mockUserId, contactIdToDelete)).thenReturn(contactToDelete);

        StepVerifier.create(contactsService.deleteContact(mockUserId, contactIdToDelete))
            .expectError(IllegalArgumentException.class)
            .verify();

        List<String> contactsToDelete = List.of(contactIdToDelete);
        StepVerifier.create(contactsService.deleteContacts(mockUserId, contactsToDelete))
            .expectError(IllegalArgumentException.class)
            .verify();
    }

}
