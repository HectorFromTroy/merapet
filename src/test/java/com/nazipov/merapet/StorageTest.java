package com.nazipov.merapet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Optional;

import com.nazipov.merapet.entities.Contact;
import com.nazipov.merapet.entities.MyUser;
import com.nazipov.merapet.storage.Storage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Storage.class)
public class StorageTest extends BaseTest {

    private final Storage storage = new Storage();

    private MyUser generateAndSaveUser() {
        MyUser user = generateUser();
        return storage.saveUser(user);
    }

    private Contact generateAndSaveContact(MyUser user, MyUser contactUser) {
        Contact contact = Contact.builder()
            .setName("qwe")
            .setUserId(contactUser.getUserId())
            .build();
        return storage.saveContact(user.getUserId(), contact);
    }
    
    @Test
    public void testUserSavingAndRetrieving() {
        MyUser userToSave = generateAndSaveUser();
        Optional<MyUser> retrievedUserOpt = storage.retrieveUser(userToSave.getUserId());
        assertTrue(retrievedUserOpt.isPresent());
        assertEquals(userToSave.getUserId(), retrievedUserOpt.get().getUserId());
    }

    @Test
    public void testNonExistingUserRetrieving() {
        Optional<MyUser> retrievedUserOpt = storage.retrieveUser("non-existing-user-id");
        assertTrue(retrievedUserOpt.isEmpty());
    }

    @Test
    public void testAllUsersRetrieving() {
        generateAndSaveUser();
        Collection<MyUser> retrievedUsers = storage.retrieveUsers();
        assertEquals(1, retrievedUsers.size());
    }

    @Test
    public void testUserEditing() {
        MyUser userToEdit = generateAndSaveUser();
        MyUser userAfterEdit = generateUser();
        userAfterEdit.setUserId(userToEdit.getUserId());
        userAfterEdit = storage.editUser(userAfterEdit);

        assertEquals(userToEdit.getUserId(), userAfterEdit.getUserId());
        assertNotEquals(userToEdit, userAfterEdit);
    }

    @Test
    public void testUserDeleting() {
        MyUser userToDelete = generateAndSaveUser();
        storage.deleteUser(userToDelete);
        assertTrue(storage.retrieveUser(userToDelete.getUserId()).isEmpty());
    }

    @Test
    public void testContactAddingAndRetrieving() {
        MyUser user = generateAndSaveUser();
        Contact contact = generateAndSaveContact(user, generateAndSaveUser());

        Optional<Contact> retrievedContact = storage.retrieveContact(user.getUserId(), contact.getContactId());
        assertTrue(retrievedContact.isPresent());
        assertEquals(contact.getContactId(), retrievedContact.get().getContactId());
    }

    @Test
    public void testAllContactsRetrieving() {
        MyUser user = generateAndSaveUser();
        generateAndSaveContact(user, generateAndSaveUser());

        Collection<Contact> retrievedContacts = storage.retrieveContacts(user.getUserId());
        assertEquals(1, retrievedContacts.size());
    }

    @Test
    public void testContactEditing() {
        MyUser user = generateAndSaveUser();
        MyUser contactUser = generateAndSaveUser();
        Contact contact = generateAndSaveContact(user, contactUser);

        Contact contactAfterEdit = Contact.builder()
            .setName("zxczxc")
            .setContactId(contact.getContactId())
            .setUserId(contactUser.getUserId())
            .build();

        contactAfterEdit = storage.editContact(user.getUserId(), contactAfterEdit);

        assertEquals(contact.getContactId(), contactAfterEdit.getContactId());
        assertNotEquals(contact, contactAfterEdit);
    }

    @Test
    public void testContactDeleting() {
        MyUser user = generateAndSaveUser();
        Contact contact = generateAndSaveContact(user, generateAndSaveUser());

        storage.deleteContact(user.getUserId(), contact.getContactId());
        assertTrue(storage.retrieveContact(user.getUserId(), contact.getContactId()).isEmpty());
    }

}
