package com.nazipov.merapet.entities;

import java.util.Objects;

public class Contact {
    
    private final String name;
    private final String userId;
    private String contactId;

    public Contact(
            String name,
            String userId,
            String contactId) {
        this.name = name;
        this.userId = userId;
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
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
        if (obj.getClass() != Contact.class)
            return false;
        Contact other = (Contact) obj;
        return Objects.equals(contactId, other.contactId) && Objects.equals(name, other.name)
                && Objects.equals(userId, other.userId);
    }

    @Override
    public String toString() {
        return String.format("user id: %s, contact id: %s", userId, contactId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String name;
        private String userId;
        private String contactId;

        private Builder() {}

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setContactId(String contactId) {
            this.contactId = contactId;
            return this;
        }

        public Contact build() {
            return new Contact(name, userId, contactId);
        }
    }
}
