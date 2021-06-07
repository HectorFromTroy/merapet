package com.nazipov.merapet;

import static org.mockito.Mockito.when;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.List;

import com.nazipov.merapet.dto.ContactInfo;
import com.nazipov.merapet.entities.Contact;
import com.nazipov.merapet.mapper.ContactMapper;
import com.nazipov.merapet.service.ContactsService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;

import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureWebTestClient(timeout = "10000")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContactsControllerTest extends BaseTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ContactsService contactsService;

    @Test
    public void testContactRetrieving() {
        String mockUserId = "qwe";
        List<Contact> contactsToRetrieve = List.of(generateContact());
        when(contactsService.retrieveContacts(mockUserId)).thenReturn(Mono.just(contactsToRetrieve));
        webTestClient.get()
            .uri("/" + mockUserId + "/contacts")
            .exchange()
            .expectStatus().isOk()
            .expectBody(List.class)
            .value(contactsList -> contactsList.size(), equalTo(contactsToRetrieve.size()));
    }

    @Test
    public void testContactAdding() {
        String mockUserId = "qwe";
        ContactInfo contactInfoToAdd = generateContactInfo();
        Contact contactToAdd = ContactMapper.mapToContactFromContactInfo(contactInfoToAdd);
        List<Contact> listOfContactsToAdd = List.of(contactToAdd);

        when(contactsService.addContact(mockUserId, contactToAdd)).thenReturn(Mono.just(contactToAdd));
        webTestClient.post()
            .uri("/" + mockUserId + "/contact")
            .bodyValue(contactInfoToAdd)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Contact.class)
            .value(c -> c.getName(), equalTo(contactInfoToAdd.getName()));

        when(contactsService.addContacts(mockUserId, listOfContactsToAdd)).thenReturn(Mono.just(listOfContactsToAdd));
        webTestClient.post()
            .uri("/" + mockUserId + "/contacts")
            .bodyValue(List.of(contactInfoToAdd))
            .exchange()
            .expectStatus().isOk()
            .expectBody(List.class)
            .value(conts -> conts.size(), equalTo(listOfContactsToAdd.size()));
    }

    @Test
    public void testContactEditing() {
        String mockUserId = "qwe";
        ContactInfo contactInfoToEdit = generateContactInfo();
        Contact contactToEdit = ContactMapper.mapToContactFromContactInfo(contactInfoToEdit);
        List<Contact> listOfContactsToEdit = List.of(contactToEdit);

        when(contactsService.editContact(mockUserId, contactToEdit)).thenReturn(Mono.just(contactToEdit));
        webTestClient.put()
            .uri("/" + mockUserId + "/contact")
            .bodyValue(contactInfoToEdit)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Contact.class)
            .value(c -> c.getName(), equalTo(contactInfoToEdit.getName()));

        when(contactsService.editContacts(mockUserId, listOfContactsToEdit)).thenReturn(Mono.just(listOfContactsToEdit));
        webTestClient.put()
            .uri("/" + mockUserId + "/contacts")
            .bodyValue(List.of(contactInfoToEdit))
            .exchange()
            .expectStatus().isOk()
            .expectBody(List.class)
            .value(conts -> conts.size(), equalTo(listOfContactsToEdit.size()));
    }

    @Test
    public void testContactDeleting() {
        String mockUserId = "qwe";
        ContactInfo contactInfoToDelete = generateContactInfo();
        String contactToDeleteId = contactInfoToDelete.getContactId();
        Contact contactToDelete = ContactMapper.mapToContactFromContactInfo(contactInfoToDelete);
        List<Contact> listOfContactsToDelete = List.of(contactToDelete);
        List<String> listOfContactIdsToDelete = List.of(contactToDeleteId);

        when(contactsService.deleteContact(mockUserId, contactToDeleteId)).thenReturn(Mono.just(contactToDelete));
        webTestClient.delete()
            .uri("/" + mockUserId + "/contact/" + contactToDeleteId)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Contact.class)
            .value(c -> c.getName(), equalTo(contactInfoToDelete.getName()));

        when(contactsService.deleteContacts(mockUserId, listOfContactIdsToDelete)).thenReturn(Mono.just(listOfContactsToDelete));
        webTestClient.delete()
            .uri("/" + mockUserId + "/contacts/" + contactToDeleteId)
            .exchange()
            .expectStatus().isOk()
            .expectBody(List.class)
            .value(conts -> conts.size(), equalTo(listOfContactsToDelete.size()));
    }
    
}
