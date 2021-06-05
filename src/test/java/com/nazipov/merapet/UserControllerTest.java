package com.nazipov.merapet;

import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nazipov.merapet.dto.UserInfo;
import com.nazipov.merapet.entities.MyUser;
import com.nazipov.merapet.mapper.UserMapper;
import com.nazipov.merapet.service.UserService;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;

@AutoConfigureWebTestClient(timeout = "10000")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    private EasyRandom generator = new EasyRandom();

    @Autowired
    private WebTestClient webTestClient;

    @SpyBean
    private UserService userService;

    public UserInfo generateUserInfo() {
        return generator.nextObject(UserInfo.class);
    }

    public MyUser generateUser() {
        return generator.nextObject(MyUser.class);
    }
    
    @Test
    public void testCorrectUserCreating() throws JsonProcessingException {
        UserInfo userInfo = generateUserInfo();
        MyUser userToCreate = UserMapper.mapToMyUserFromUserInfo(userInfo);
        Mono<MyUser> userToCreateMono = Mono.just(userToCreate);
        when(userService.saveUser(userToCreate)).thenReturn(userToCreateMono);
        String createUserUrl = "/users";
        webTestClient.post()
            .uri(createUserUrl)
            .bodyValue(userInfo)
            .exchange()
            .expectStatus().isOk()
            .expectBody(MyUser.class)
            .value(u -> u.getUsername(), equalTo(userInfo.getUsername()));
    }

    @Test
    public void testUserRetrieving() {
        MyUser userToRetrieve = generateUser();
        String userToRetrieveId = userToRetrieve.getUserId();
        Mono<MyUser> userToRetrieveMono = Mono.just(userToRetrieve);
        when(userService.retrieveUser(userToRetrieveId)).thenReturn(userToRetrieveMono);
        webTestClient.get()
            .uri("/users/" + userToRetrieveId)
            .exchange()
            .expectStatus().isOk()
            .expectBody(MyUser.class)
            .value(u -> u.getUserId(), equalTo(userToRetrieveId));
    }

    @Test
    public void testAllUsersRetrieving() {
        Collection<MyUser> allUserToRetrieve = List.of(generateUser());
        Mono<Collection<MyUser>> allUsersToRetrieveMono = Mono.just(allUserToRetrieve);
        when(userService.retrieveUsers()).thenReturn(allUsersToRetrieveMono);
        webTestClient.get()
            .uri("/users/all")
            .exchange()
            .expectStatus().isOk()
            .expectBody(List.class)
            .value(usersList -> 1, equalTo(1));
    }

    @Test
    public void testUserEditing() {
        UserInfo userInfo = generateUserInfo();
        MyUser userToEdit = UserMapper.mapToMyUserFromUserInfo(userInfo);
        Mono<MyUser> userToEditMono = Mono.just(userToEdit);
        when(userService.editUser(userToEdit)).thenReturn(userToEditMono);
        webTestClient.put()
            .uri("/users")
            .bodyValue(userInfo)
            .exchange()
            .expectStatus().isOk()
            .expectBody(MyUser.class)
            .value(equalTo(userToEdit));
    }

    @Test
    public void testUserDeleting() {
        MyUser userToDelete = generateUser();
        String userToDeleteId = userToDelete.getUserId();
        Mono<MyUser> userToDeleteMono = Mono.just(userToDelete);
        when(userService.deleteUser(userToDeleteId)).thenReturn(userToDeleteMono);
        webTestClient.delete()
            .uri("/users/" + userToDeleteId)
            .exchange()
            .expectStatus().isOk()
            .expectBody(MyUser.class)
            .value(u -> u.getUserId(), equalTo(userToDeleteId));
    }

}
