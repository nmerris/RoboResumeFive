package com.nmerris.roboresumedb.models;

public class NavBarState {

    // navbar fragment will use these to know which navbar item to highlight
    private boolean highlightPersonNav;
    private boolean highlightEdNav;
    private boolean highlightWorkNav;
    private boolean highlightSkillNav;
    private boolean highlightEditNav;
    private boolean highlightFinalNav;

    // navbar fragment will use these to know when to disable various links
    private boolean disableAddEdLink;
    private boolean disableAddSkillLink;
    private boolean disableAddWorkExpLink;
    private boolean disableEditDetailsLink;
    private boolean disableShowFinalLink;

    // navbar fragment will use these to know what number to put in the 'badges'
    private long numEdAchievements;
    private long numWorkExps;
    private long numSkills;

    // navbar fragment needs to know the id of the Person whose resume is currently
    // being entered, so that when you click the Personal link, it can take you to the
    // update page with the correct ID to use to save in the person repo
    private long personId;

    public boolean getHighlightPersonNav() {
        return highlightPersonNav;
    }

    public void setHighlightPersonNav(boolean highlightPersonNav) {
        this.highlightPersonNav = highlightPersonNav;
    }

    public boolean getHighlightEdNav() {
        return highlightEdNav;
    }

    public void setHighlightEdNav(boolean highlightEdNav) {
        this.highlightEdNav = highlightEdNav;
    }

    public boolean getHighlightWorkNav() {
        return highlightWorkNav;
    }

    public void setHighlightWorkNav(boolean highlightWorkNav) {
        this.highlightWorkNav = highlightWorkNav;
    }

    public boolean getHighlightSkillNav() {
        return highlightSkillNav;
    }

    public void setHighlightSkillNav(boolean highlightSkillNav) {
        this.highlightSkillNav = highlightSkillNav;
    }

    public boolean getHighlightEditNav() {
        return highlightEditNav;
    }

    public void setHighlightEditNav(boolean highlightEditNav) {
        this.highlightEditNav = highlightEditNav;
    }

    public boolean getHighlightFinalNav() {
        return highlightFinalNav;
    }

    public void setHighlightFinalNav(boolean highlightFinalNav) {
        this.highlightFinalNav = highlightFinalNav;
    }

    public boolean getDisableAddEdLink() {
        return disableAddEdLink;
    }

    public void setDisableAddEdLink(boolean disableAddEdLink) {
        this.disableAddEdLink = disableAddEdLink;
    }

    public boolean getDisableAddSkillLink() {
        return disableAddSkillLink;
    }

    public void setDisableAddSkillLink(boolean disableAddSkillLink) {
        this.disableAddSkillLink = disableAddSkillLink;
    }

    public boolean getDisableAddWorkExpLink() {
        return disableAddWorkExpLink;
    }

    public void setDisableAddWorkExpLink(boolean disableAddWorkExpLink) {
        this.disableAddWorkExpLink = disableAddWorkExpLink;
    }

    public boolean getDisableEditDetailsLink() {
        return disableEditDetailsLink;
    }

    public void setDisableEditDetailsLink(boolean disableEditDetailsLink) {
        this.disableEditDetailsLink = disableEditDetailsLink;
    }

    public boolean getDisableShowFinalLink() {
        return disableShowFinalLink;
    }

    public void setDisableShowFinalLink(boolean disableShowFinalLink) {
        this.disableShowFinalLink = disableShowFinalLink;
    }

    public long getNumEdAchievements() {
        return numEdAchievements;
    }

    public void setNumEdAchievements(long numEdAchievements) {
        this.numEdAchievements = numEdAchievements;
    }

    public long getNumWorkExps() {
        return numWorkExps;
    }

    public void setNumWorkExps(long numWorkExps) {
        this.numWorkExps = numWorkExps;
    }

    public long getNumSkills() {
        return numSkills;
    }

    public void setNumSkills(long numSkills) {
        this.numSkills = numSkills;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }
}
