package com.nazipov.merapet.service;

import java.util.Collection;

import com.nazipov.merapet.entities.MyUser;

import reactor.core.publisher.Mono;

public interface UserService {
    Mono<MyUser> retrieveUser(String userId);
    Mono<Collection<MyUser>> retrieveUsers();
    Mono<MyUser> saveUser(MyUser user);
    Mono<MyUser> editUser(MyUser user);
    Mono<MyUser> deleteUser(String userId);
}
