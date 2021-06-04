package com.nazipov.merapet.mapper;

import com.nazipov.merapet.dto.ContactInfo;
import com.nazipov.merapet.entities.Contact;

public class ContactMapper {

    private ContactMapper() {}

    public static Contact mapToContactFromContactInfo(ContactInfo info) {
        return Contact.builder()
                .setContactId(info.getContactId())
                .setName(info.getName())
                .setUserId(info.getUserId())
                .build();
    }
    
}
