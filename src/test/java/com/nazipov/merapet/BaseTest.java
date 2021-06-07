package com.nazipov.merapet;

import com.nazipov.merapet.dto.ContactInfo;
import com.nazipov.merapet.dto.UserInfo;
import com.nazipov.merapet.entities.Contact;
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

    protected Contact generateContact() {
        return generator.nextObject(Contact.class);
    }

    protected ContactInfo generateContactInfo() {
        return generator.nextObject(ContactInfo.class);
    }

}
