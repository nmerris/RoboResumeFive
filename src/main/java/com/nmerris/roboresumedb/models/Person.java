package com.nmerris.roboresumedb.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @NotEmpty
    @Size(max = 50)
    private String nameFirst;

    @NotEmpty
    @Size(max = 50)
    private String nameLast;

    @NotEmpty
    @Email
    @Size(max = 50)
    private String email;


    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<EducationAchievement> edAchievements;

    // a convenience method to add an EdAchievement
    // this will 1. associate this Person with the incoming ea, and
    // 2. add the incoming ea to this Person's edAchivements list
    // this is all necessary for JPA to work properly
    public void addEdAchievement(EducationAchievement ea) {
        // if I don't include ea.setPerson here, I would just need to do it
        // manually in the controller before I save a new ea
        ea.setPerson(this); // I am not sure if I want this line????
        edAchievements.add(ea);
    }

    public long getEdAchievementCount() {
        return edAchievements.size();
    }

    // in order to delete and employee, you must first remove it from it's department's Set of employees
    public void removeEdAchievement(EducationAchievement ea) {
        edAchievements.remove(ea);
    }



    // need to instantiate new Sets when a new Person is created
    public Person() {
        edAchievements = new HashSet<>();
        // need other set instantiations here
    }






    // GETTERS AND SETTERS ===================================================

    public String getNameFirst() {
        return nameFirst;
    }

    public void setNameFirst(String nameFirst) {
        this.nameFirst = nameFirst;
    }

    public String getNameLast() {
        return nameLast;
    }

    public void setNameLast(String nameLast) {
        this.nameLast = nameLast;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<EducationAchievement> getEdAchievements() {
        return edAchievements;
    }

    public void setEdAchievements(Set<EducationAchievement> edAchievements) {
        this.edAchievements = edAchievements;
    }
}
