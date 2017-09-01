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

    @OneToMany(mappedBy = "myPerson", cascade= CascadeType.ALL, fetch= FetchType.EAGER)
    private Set<EducationAchievement> educationAchievements;

    public Person(){
        setEducationAchievements(new HashSet<EducationAchievement>());
    }


    
    // data to store temporarily for this project
//    private ArrayList<EducationAchievement> educationAchievements = new ArrayList<EducationAchievement>();
    private ArrayList<WorkExperience> workExperiences = new ArrayList<WorkExperience>();
    private ArrayList<Skill> skills = new ArrayList<Skill>();






    public Set<EducationAchievement> getEducationAchievements() {
        return educationAchievements;
    }

    public void setEducationAchievements(Set<EducationAchievement> educationAchievements) {
        this.educationAchievements = educationAchievements;
    }

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

//    public ArrayList<EducationAchievement> getEducationAchievements() {
//        return educationAchievements;
//    }
//
//    public void setEducationAchievements(ArrayList<EducationAchievement> educationAchievements) {
//        this.educationAchievements = educationAchievements;
//    }

    public ArrayList<WorkExperience> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(ArrayList<WorkExperience> workExperiences) {
        this.workExperiences = workExperiences;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
