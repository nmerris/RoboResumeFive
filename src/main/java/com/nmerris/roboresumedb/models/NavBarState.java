package com.nmerris.roboresumedb.models;

public class NavBarState {
    
    private boolean highlightPersonNav;
    private boolean highlightEdNav;
    private boolean highlightWorkNav;
    private boolean highlightSkillNav;
    private boolean highlightEditNav;
    private boolean highlightFinalNav;
    
//    private boolean disablePersonLink;
    private boolean disableAddEdLink;
    private boolean disableAddSkillLink;
    private boolean disableAddWorkExpLink;
    private boolean disableEditDetailsLink;
    private boolean disableShowFinalLink;

    private long numEdAchievements;
    private long numWorkExps;
    private long numSkills;

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

//    public boolean getDisablePersonLink() {
//        return disablePersonLink;
//    }

//    public void setDisablePersonLink(boolean disablePersonLink) {
//        this.disablePersonLink = disablePersonLink;
//    }

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
}
