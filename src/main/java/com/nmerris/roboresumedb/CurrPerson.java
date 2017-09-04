package com.nmerris.roboresumedb;

import com.nmerris.roboresumedb.models.Course;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value="session")
public class CurrPerson implements Serializable {

    private long personId;

    private Set<Course> courses;


    public CurrPerson() {
        setCourses(new HashSet<>());
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }
}
