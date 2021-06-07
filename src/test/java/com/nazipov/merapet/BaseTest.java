package com.nazipov.merapet;

import com.nazipov.merapet.dto.UserInfo;
import com.nazipov.merapet.entities.MyUser;

import org.jeasy.random.EasyRandom;

public class BaseTest {
    private EasyRandom generator = new EasyRandom();
    
    protected UserInfo generateUserInfo() {
        return generator.nextObject(UserInfo.class);
    }

    protected MyUser generateUser() {
        return generator.nextObject(MyUser.class);
    }
}
