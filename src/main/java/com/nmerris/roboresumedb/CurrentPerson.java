package com.nmerris.roboresumedb;

import org.springframework.stereotype.Component;

@Component
public class CurrentPerson {

    private long personId;

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }
}
