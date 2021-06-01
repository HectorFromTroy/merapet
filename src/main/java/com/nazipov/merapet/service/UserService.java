package com.nazipov.merapet.service;

import java.util.Optional;

import com.nazipov.merapet.entities.MyUser;

public interface UserService {
    String saveUser(MyUser user) throws Exception;
}
