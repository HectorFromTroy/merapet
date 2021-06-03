package com.nazipov.merapet.service.impl;

import java.util.Collection;
import java.util.Optional;

import com.nazipov.merapet.entities.MyUser;
import com.nazipov.merapet.service.UserService;
import com.nazipov.merapet.storage.Storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private final Storage storage;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Mono<MyUser> retrieveUser(String userId) {
        Optional<MyUser> retrievedUser = storage.retrieveUser(userId);
        if (retrievedUser.isEmpty()) {
            // error emitted here doesn't get captured :(
            throw new IllegalArgumentException("There is no such user :(((");
        }
        return Mono.just(retrievedUser.get())
            .doOnNext(signal -> logger.info(String.format("Retrieved user with id: %s", userId)));
    }

    @Override
    public Mono<Collection<MyUser>> retrieveUsers() {
        return Mono.just(storage.retrieveUsers())
            .doOnNext(signal -> logger.info("Retrieved all users"));
    }

    @Override
    public Mono<MyUser> saveUser(MyUser user) {
        if (storage.isUserExist(user.getUsername())) {
            throw new IllegalArgumentException();
        }
        return Mono.just(storage.saveUser(user))
            .doOnNext(signal -> logger.info(String.format("Created new user: %s", user)));
    }

    @Override
    public Mono<MyUser> editUser(MyUser user) {
        Optional<MyUser> existingUser = storage.retrieveUser(user.getUserId());
        if (existingUser.isEmpty()) {
            throw new IllegalArgumentException("There is no such user :(((");
        }
        return Mono.just(storage.editUser(user))
            .doOnNext(signal -> logger.info(String.format("Edited user: %s", user)));
    }

    @Override
    public Mono<MyUser> deleteUser(String userId) {
        Optional<MyUser> userToDelete = storage.retrieveUser(userId);
        if (userToDelete.isEmpty()) {
            throw new IllegalArgumentException("There is no such user :(((");
        }
        return Mono.just(storage.deleteUser(userToDelete.get()))
            .doOnNext(signal -> logger.info(String.format("Deleted user: %s", userId)));
    }
    
}
