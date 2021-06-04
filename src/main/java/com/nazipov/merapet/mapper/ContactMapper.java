package com.nazipov.merapet.mapper;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<Contact> mapToContactFromContactInfo(List<ContactInfo> infos) {
        return infos.stream()
                .map(ContactMapper::mapToContactFromContactInfo)
                .collect(Collectors.toList());
    }
    
}
