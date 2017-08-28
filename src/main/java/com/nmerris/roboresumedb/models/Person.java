package com.nmerris.roboresumedb.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;

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

    // the date the resume was created, automatically generated, not a user input
    // TODO remove this and update the db
    private Date resumeCreationDate;
    
    // data to store temporarily for this project
    private ArrayList<EducationAchievement> educationAchievements = new ArrayList<EducationAchievement>();
    private ArrayList<WorkExperience> workExperiences = new ArrayList<WorkExperience>();
    private ArrayList<Skill> skills = new ArrayList<Skill>();

    public Date getResumeCreationDate() {
        return resumeCreationDate;
    }

    public void setResumeCreationDate(Date resumeCreationDate) {
        this.resumeCreationDate = resumeCreationDate;
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

    public ArrayList<EducationAchievement> getEducationAchievements() {
        return educationAchievements;
    }

    public void setEducationAchievements(ArrayList<EducationAchievement> educationAchievements) {
        this.educationAchievements = educationAchievements;
    }

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
