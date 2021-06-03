package com.nazipov.merapet.service.impl;

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
        return Mono.just(storage.retrieveUser(userId))
            .doOnNext(signal -> logger.info(String.format("Retrieved user with id: %s", userId)));
    }

    @Override
    public Mono<MyUser> saveUser(MyUser user) {
        return Mono.just(storage.saveUser(user))
            .doOnNext(signal -> logger.info(String.format("Created new user: %s", user)));
    }

    @Override
    public Mono<MyUser> editUser(MyUser user) {
        return Mono.just(storage.editUser(user))
            .doOnNext(signal -> logger.info(String.format("Edited user: %s", user)));
    }
    
}
