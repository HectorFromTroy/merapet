package com.nazipov.merapet.service.impl;

import java.util.Optional;

import com.nazipov.merapet.entities.MyUser;
import com.nazipov.merapet.service.UserService;
import com.nazipov.merapet.storage.Storage;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final Storage storage;

    public UserServiceImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public String saveUser(MyUser user) throws Exception {
        return storage.saveUser(user);
    }
    
}
