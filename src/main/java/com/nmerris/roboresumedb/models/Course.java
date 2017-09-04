package com.nmerris.roboresumedb.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Size(max = 50)
    private String title;

    @NotEmpty
    @Size(max = 50)
    private String instructor;

    @Min(0)
    private long credits;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Person> people;


    public Course() {
        setPeople(new HashSet<>());
    }


    // call this to remove a set of persons from a course, then save to courseRepo...
    // BEFORE you save this course back to repo, you MUST first remove this course from EVERY person it is attached to
    public void removePersons(Collection<Person> personCollection) {
        people.removeAll(personCollection);
    }

    public void removePerson(Person person) {
        people.remove(person);
    }

    // need to use @Transactional annotation on any method that calls this
    // call this before deleting an entire course ???
//    public void removeAllPersons() {
//        people.clear();
//    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public long getCredits() {
        return credits;
    }

    public void setCredits(long credits) {
        this.credits = credits;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }
}
