package com.nazipov.merapet;

import static org.mockito.Mockito.when;

import com.nazipov.merapet.entities.MyUser;
import com.nazipov.merapet.service.UserService;
import com.nazipov.merapet.service.impl.UserServiceImpl;
import com.nazipov.merapet.storage.Storage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

@AutoConfigureWebTestClient(timeout = "10000")
@SpringBootTest(classes = UserService.class)
public class UserServiceTest {

    @SpyBean
    private UserService userService;

    @MockBean
    private Storage storage;
    
    @Test
    public void testUserRetrieving() {
        MyUser userToRetrieve = 
        userService = new UserServiceImpl(storage);
        when(storage.retrieveUser())
    }

}
