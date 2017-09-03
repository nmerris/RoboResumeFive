package com.nmerris.roboresumedb.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
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

    @OneToMany(mappedBy = "myPerson", cascade = CascadeType.ALL, fetch= FetchType.EAGER)
    private Set<EducationAchievement> educationAchievements;

    @OneToMany(mappedBy = "myPerson", cascade = CascadeType.ALL, fetch= FetchType.EAGER)
    private Set<WorkExperience> workExperiences;

    @OneToMany(mappedBy = "myPerson", cascade = CascadeType.ALL, fetch= FetchType.EAGER)
    private Set<Skill> skills;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Course> courses;

    public Person() {
        setEducationAchievements(new HashSet<>());
        setWorkExperiences(new HashSet<>());
        setSkills(new HashSet<>());
        setCourses(new HashSet<>());
    }


    // call this to add a set of Course to this Person, then save person to personRepo
    public void addCourses(Collection<Course> courseCollection) {
        courses.addAll(courseCollection);
    }

    // call this to remove a set of courses from this person, then save person to personRepo
    public void removeCourses(Collection<Course> courseCollection) {
        courses.removeAll(courseCollection);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    // TODO removeAllCourses, so we can delete a person?

    // in order to delete an ed, you must first remove it from it's parents collection
    public void removeEdAchievement(EducationAchievement ea) {
        educationAchievements.remove(ea);
    }

    // need to use @Transactional annotation on any method that calls this
    public void removeAllEdAchievements() {
        educationAchievements.clear();
    }

    public void removeWorkExperience(WorkExperience we) {
        workExperiences.remove(we);
    }

    public void removeAllWorkExperiences() {
        workExperiences.clear();
    }

    public void removeSkill(Skill skill) {
        skills.remove(skill);
    }

    public void removeAllSkills() {
        skills.clear();
    }


    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<WorkExperience> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(Set<WorkExperience> workExperiences) {
        this.workExperiences = workExperiences;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
