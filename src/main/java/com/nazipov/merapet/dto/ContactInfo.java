package com.nazipov.merapet.dto;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

public class ContactInfo implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String name;
    private String userId;
    private String contactId;

    @JsonCreator(mode = Mode.PROPERTIES)
    public ContactInfo(
            @JsonProperty("name") String name,
            @JsonProperty("userId") String userId,
            @JsonProperty("contactId") String contactId) {
        this.name = name;
        this.userId = userId;
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactId, name, userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj.getClass() != ContactInfo.class)
            return false;
        ContactInfo other = (ContactInfo) obj;
        return Objects.equals(contactId, other.contactId) && Objects.equals(name, other.name)
                && Objects.equals(userId, other.userId);
    }

    @Override
    public String toString() {
        return String.format("user id: %s, contact id: %s", userId, contactId);
    }

}
