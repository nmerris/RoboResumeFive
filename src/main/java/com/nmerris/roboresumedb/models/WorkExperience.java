package com.nmerris.roboresumedb.models;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Size(max = 50)
    private String jobTitle;


    @NotEmpty
    @Size(max = 50)
    private String company;

    // @Temporal required for validation
    // @DateTimeFormat will show the date as given, but ONLY when being pulled out of db, will still be stored
    // as full java.util.Date, which is what I want, for consistency
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "MMM d, yyyy")
    private Date dateStart;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "MMM d, yyyy")
    private Date dateEnd;

    @NotEmpty
    @Size(max = 50)
    private String dutyOne;

    // second duty is optional
    @Size(max = 50)
    private String dutyTwo;

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDutyOne() {
        return dutyOne;
    }

    public void setDutyOne(String dutyOne) {
        this.dutyOne = dutyOne;
    }

    public String getDutyTwo() {
        return dutyTwo;
    }

    public void setDutyTwo(String dutyTwo) {
        this.dutyTwo = dutyTwo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
