package com.nazipov.merapet.service;

import java.util.Optional;

import com.nazipov.merapet.entities.MyUser;

import reactor.core.publisher.Mono;

public interface UserService {
    Mono<MyUser> retrieveUser(String userId);
    Mono<MyUser> saveUser(MyUser user);
    Mono<MyUser> editUser(MyUser user);
}
