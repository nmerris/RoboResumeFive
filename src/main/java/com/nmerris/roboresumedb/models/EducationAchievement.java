package com.nmerris.roboresumedb.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class EducationAchievement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

//    @Size(min = 2, max = 64)
    @NotEmpty
    @Size(max = 50)
    private String major;

//    @Size(min = 2, max = 64)
    @NotEmpty
    @Size(max = 50)
    private String school;

    @Min(1900)
    private long graduationYear;

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSchool() {
        return school;
}

    public void setSchool(String school) {
        this.school = school;
    }

    public long getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(long graduationYear) {
        this.graduationYear = graduationYear;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
