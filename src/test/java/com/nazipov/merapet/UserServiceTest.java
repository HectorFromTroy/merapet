package com.nazipov.merapet;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.nazipov.merapet.entities.MyUser;
import com.nazipov.merapet.service.impl.UserServiceImpl;
import com.nazipov.merapet.storage.Storage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import reactor.test.StepVerifier;

@AutoConfigureWebTestClient(timeout = "10000")
@SpringBootTest(classes = UserServiceImpl.class)
public class UserServiceTest extends BaseTest {

    @SpyBean
    private UserServiceImpl userService;

    @MockBean
    private Storage storage;
    
    @Test
    public void testUserRetrieving() {
        MyUser userToRetrieve = generateUser();
        Optional<MyUser> userToRetrieveOptional = Optional.of(userToRetrieve);
        userService = new UserServiceImpl(storage);
        when(storage.retrieveUser(userToRetrieve.getUserId())).thenReturn(userToRetrieveOptional);
        StepVerifier.create(userService.retrieveUser(userToRetrieve.getUserId()))
            .assertNext(elem -> System.out.println("Next: " + elem))
            .verifyComplete();
    }

    @Test
    public void testAllUsersRetrieving() {
        MyUser userToRetrieve = generateUser();
        List<MyUser> usersList = List.of(userToRetrieve);
        userService = new UserServiceImpl(storage);
        when(storage.retrieveUsers()).thenReturn(usersList);
        StepVerifier.create(userService.retrieveUsers())
            .expectNextMatches(users -> users.size() == usersList.size())
            .verifyComplete();
    }

    @Test
    public void testNonExistingUserRetrieving() {
        when(storage.retrieveUser("qwe")).thenReturn(Optional.empty());
        StepVerifier.create(userService.retrieveUser("qwe"))
            .expectError(IllegalArgumentException.class)
            .verify();
    }

    @Test
    public void testUserSaving() {
        MyUser userToSave = generateUser();
        userService = new UserServiceImpl(storage);
        when(storage.saveUser(userToSave)).thenReturn(userToSave);
        StepVerifier.create(userService.saveUser(userToSave))
            .assertNext(elem -> System.out.println("Next: " + elem))
            .verifyComplete();
    }

    @Test
    public void testExistingUserSaving() {
        MyUser userToSave = generateUser();
        userService = new UserServiceImpl(storage);
        when(storage.isUserExist(userToSave.getUsername())).thenReturn(true);
        StepVerifier.create(userService.saveUser(userToSave))
            .expectError(IllegalArgumentException.class)
            .verify();
    }

    @Test
    public void testUserEditing() {
        MyUser editedUser = generateUser();
        userService = new UserServiceImpl(storage);
        when(storage.retrieveUser(editedUser.getUserId())).thenReturn(Optional.of(editedUser));
        when(storage.editUser(editedUser)).thenReturn(editedUser);
        StepVerifier.create(userService.editUser(editedUser))
            .assertNext(elem -> System.out.println("Next: " + elem))
            .verifyComplete();
    }

    @Test
    public void testNonExistingUserEditing() {
        MyUser nonExistingUserToEdit = generateUser();
        userService = new UserServiceImpl(storage);
        when(storage.retrieveUser(nonExistingUserToEdit.getUserId())).thenReturn(Optional.empty());
        StepVerifier.create(userService.editUser(nonExistingUserToEdit))
            .expectError(IllegalArgumentException.class)
            .verify();
    }

    @Test
    public void testUserDeleting() {
        MyUser userToDelete = generateUser();
        userService = new UserServiceImpl(storage);
        when(storage.retrieveUser(userToDelete.getUserId())).thenReturn(Optional.of(userToDelete));
        when(storage.deleteUser(userToDelete)).thenReturn(userToDelete);
        StepVerifier.create(userService.deleteUser(userToDelete.getUserId()))
            .assertNext(elem -> System.out.println("Next: " + elem))
            .verifyComplete();
    }

    @Test
    public void testNonExistingUserDeleting() {
        MyUser userToDelete = generateUser();
        userService = new UserServiceImpl(storage);
        when(storage.retrieveUser(userToDelete.getUserId())).thenReturn(Optional.empty());
        StepVerifier.create(userService.deleteUser(userToDelete.getUserId()))
            .expectError(IllegalArgumentException.class)
            .verify();
    }

}
