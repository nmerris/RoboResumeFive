package com.nmerris.roboresumedb.controllers;

import com.nmerris.roboresumedb.Utilities;
import com.nmerris.roboresumedb.models.*;
import com.nmerris.roboresumedb.repositories.EducationRepo;
import com.nmerris.roboresumedb.repositories.PersonRepo;
import com.nmerris.roboresumedb.repositories.SkillRepo;
import com.nmerris.roboresumedb.repositories.WorkExperienceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class MainController {

    @Autowired
    PersonRepo personRepo;
    @Autowired
    EducationRepo educationRepo;
    @Autowired
    SkillRepo skillRepo;
    @Autowired
    WorkExperienceRepo workExperienceRepo;


    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/logout")
    public String logout() {
        return "login";
    }

    // default route takes user to addperson, but since basic authentication security is enabled, they will have to
    // go through the login route first, then Spring will automatically take them to addperson
    @GetMapping("/")
    public String indexPageGet() {
        // redirect is like clicking a link on a web page, this route will not even show a view, it just redirects
        // the user to the addperson route
        return "redirect:/addperson";
    }


    // any time this route is called, all db tables are wiped out
    // there is a button to start a new resume in the startover.html view which this route displays
    @GetMapping("/startover")
    public String startOver() {
        personRepo.deleteAll();
        educationRepo.deleteAll();
        skillRepo.deleteAll();
        workExperienceRepo.deleteAll();
        return "startover";
    }


    @GetMapping("/addperson")
    public String addPersonGet(Model model) {
        if(personRepo.count() < 1) {
            // user has not yet entered personal details, so create a new Person and add it to the model so the user
            // can enter their new personal details
            model.addAttribute("newPerson", new Person());
        }
        else {
            // personRepo can only ever have one entry, although the id may change, so just get the existing single entry
            // which we can use to populate the personal details form
            model.addAttribute("newPerson", personRepo.findAll().iterator().next());
        }

        NavBarState pageState = getPageLinkState();
        // set the navbar to highlight the appropriate link
        pageState.setHighlightPersonNav(true);
        model.addAttribute("pageState", pageState);

        return "addperson";
    }


    @PostMapping("/addperson")
    public String addPersonPost(@Valid @ModelAttribute("newPerson") Person person,
                                BindingResult bindingResult, Model model) {

        // return the same view (now with validation error messages) if there were any validation problems
        if(bindingResult.hasErrors()) {
            // always need to set up the navbar, every time a view is returned
            NavBarState pageState = getPageLinkState();
            pageState.setHighlightPersonNav(true);
            model.addAttribute("pageState", pageState);

            return "addperson";
        }

        // TODO remove res creation date from Person model, recreate db
        // set the resume creation date to right now
//        person.setResumeCreationDate(new Date());

        // there is no need to check to see if the Person table already has an entry here, there is only ever one
        // entry, and the save method will automatically update the entry in question, it knows that if the id is the
        // same, it should update the entry instead of creating a new one, this is true here even if the user
        // refreshes the page.
        personRepo.save(person);

        // go to education section automatically, it's the most logical
        // since there is no confirmation page for addperson, we want to redirect here
        // redirect means that if this route gets to this point, it's not even going to return a view at all, which
        // is why no model stuff is needed here, redirect is basically like clicking on a link on a web page
        // you can redirect to any internal route, or any external URL
        return "redirect:/addeducation";
    }


    @GetMapping("/addeducation")
    public String addEdGet(Model model) {
        // disable the submit button if >= 10 records in db, it would never be possible for the user to click to get
        // here from the navi page if there were already >= 10 records, however they could manually type in the URL
        // so I want to disable the submit button if they do that and there are already 10 records
        model.addAttribute("disableSubmit", educationRepo.count() >= 10);

        // each resume section (except personal) shows a running count of the number of records currently in the db
        model.addAttribute("currentNumRecords", educationRepo.count());

        NavBarState pageState = getPageLinkState();
        pageState.setHighlightEdNav(true);
        model.addAttribute("pageState", pageState);

        // the users name is displayed at the top of each resume section (except personal details), we need to check
        // every time a view is returned, because the user can change their personal details at any time, and we want
        // to make sure the displayed name is always up to date
        addPersonNameToModel(model);

        // return a new ed achievement for the user to populate
        model.addAttribute("newEdAchievement", new EducationAchievement());

        return "addeducation";
    }

    
    @PostMapping("/addeducation")
    public String addEdPost(@Valid @ModelAttribute("newEdAchievement") EducationAchievement educationAchievement,
                            BindingResult bindingResult, Model model) {

        // the persons name is show at the top of each 'add' section AND each confirmation page, so we want to add
        // it to the model no matter which view is returned
        addPersonNameToModel(model);

        // return the same view (now with validation error messages) if there were any validation problems
        if(bindingResult.hasErrors()) {
            // update the navbar state and add it to our model
            NavBarState pageState = getPageLinkState();
            pageState.setHighlightEdNav(true);
            model.addAttribute("pageState", pageState);

            // disable the form submit button if there are 10 or more records in the education repo
            model.addAttribute("disableSubmit", educationRepo.count() >= 10);
            model.addAttribute("currentNumRecords", educationRepo.count());

            return "addeducation";
        }

        // I'm being picky here, but it is possible for the user to refresh the page, which bypasses the form submit
        // button, and so they would be able to add more than 10 items, to avoid this, just condition the db save on count
        if(educationRepo.count() < 10) {
            educationRepo.save(educationAchievement);
        }

        // need to get the count AFTER successfully adding to db, so it is up to date
        model.addAttribute("currentNumRecords", educationRepo.count());

        // add the EducationAchievement just entered to the model, so we can show a confirmation page
        model.addAttribute("edAchievementJustAdded", educationAchievement);
        
        // also need to set disableSubmit flag AFTER adding to db, or user will think they can add more than 10
        // because the 'add another' button will work, but then the entry form button will be disabled, this
        // way the user will not be confused... I am repurposing 'disableSubmit' here, it's actually being used to
        // disable the 'Add Another' button in the confirmation page
        model.addAttribute("disableSubmit", educationRepo.count() >= 10);

        // the navbar state depends on the db table counts in various ways, so always need to have an updated navbar
        // state after saving to the repo
        NavBarState pageState = getPageLinkState();
        pageState.setHighlightEdNav(true);
        model.addAttribute("pageState", pageState);

        return "addeducationconfirmation";
    }


    // logic in this route is identical to /addeducation, see /addeducation GetMapping for explanatory comments
    @GetMapping("/addworkexperience")
    public String addWorkGet(Model model) {
        NavBarState pageState = getPageLinkState();
        pageState.setHighlightWorkNav(true);
        model.addAttribute("pageState", pageState);

        addPersonNameToModel(model);
        model.addAttribute("disableSubmit", workExperienceRepo.count() >= 10);
        model.addAttribute("currentNumRecords", workExperienceRepo.count());
        model.addAttribute("newWorkExperience", new WorkExperience());

        return "addworkexperience";
    }
    
    
    // logic in this route is identical to /addeducation, see /addeducation PostMapping for explanatory comments
    @PostMapping("/addworkexperience")
    public String addWorkPost(@Valid @ModelAttribute("newWorkExperience") WorkExperience workExperience,
                            BindingResult bindingResult, Model model) {

        addPersonNameToModel(model);

        if(bindingResult.hasErrors()) {
            NavBarState pageState = getPageLinkState();
            pageState.setHighlightWorkNav(true);
            model.addAttribute("pageState", pageState);
            model.addAttribute("currentNumRecords", workExperienceRepo.count());
            model.addAttribute("disableSubmit", workExperienceRepo.count() >= 10);

            return "addworkexperience";
        }

        // work experience end date can be left null by user, in which case we want to show 'Present' in the
        // confirmation page
        model.addAttribute("dateEndString", Utilities.getMonthDayYearFromDate(workExperience.getDateEnd()));
        model.addAttribute("workExperienceJustAdded", workExperience);

        if(workExperienceRepo.count() < 10) {
            workExperienceRepo.save(workExperience);
        }

        NavBarState pageState = getPageLinkState();
        pageState.setHighlightWorkNav(true);
        model.addAttribute("pageState", pageState);
        model.addAttribute("currentNumRecords", workExperienceRepo.count());
        model.addAttribute("disableSubmit", workExperienceRepo.count() >= 10);

        return "addworkexperienceconfirmation";
    }

    
    // logic in this route is identical to /addeducation, see /addeducation GetMapping for explanatory comments
    @GetMapping("/addskill")
    public String addSkillGet(Model model) {
        addPersonNameToModel(model);
        model.addAttribute("currentNumRecords", skillRepo.count());
        model.addAttribute("newSkill", new Skill());
        model.addAttribute("disableSubmit", skillRepo.count() >= 20);

        NavBarState pageState = getPageLinkState();
        pageState.setHighlightSkillNav(true);
        model.addAttribute("pageState", pageState);

        return "addskill";
    }


    // logic in this route is identical to /addeducation, see /addeducation PostMapping for explanatory comments
    @PostMapping("/addskill")
    public String addSkillPost(@Valid @ModelAttribute("newSkill") Skill skill,
                              BindingResult bindingResult, Model model) {

        addPersonNameToModel(model);

        if(bindingResult.hasErrors()) {
            NavBarState pageState = getPageLinkState();
            pageState.setHighlightSkillNav(true);
            model.addAttribute("pageState", pageState);
            model.addAttribute("currentNumRecords", skillRepo.count());

            return "addskill";
        }

        if(skillRepo.count() < 20) {
            skillRepo.save(skill);
        }

        NavBarState pageState = getPageLinkState();
        pageState.setHighlightSkillNav(true);
        model.addAttribute("pageState", pageState);

        model.addAttribute("skillJustAdded", skill);
        model.addAttribute("currentNumRecords", skillRepo.count());
        model.addAttribute("disableSubmit", skillRepo.count() >= 20);

        return "addskillconfirmation";
    }


    // this route returns a view that shows ALL the records from every repo
    // every record can be edited by clicking a link next to it
    // every record (except the single personal details record) can also be deleted by clicking a link next to it
    @GetMapping("/editdetails")
    public String editDetails(Model model) {
        // add all the contents of every repo to the model, so the tables have data to display
        addDbContentsToModel(model);

        NavBarState pageState = getPageLinkState();
        pageState.setHighlightEditNav(true);
        model.addAttribute("pageState", pageState);

        return "editdetails";
    }


    // id is the id to delete
    // type is what table to delete from
    // this route is triggered when the user clicks on the 'delete' link next to a row in editdetails.html
    // no model is needed here because all the returned views are redirects
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, @RequestParam("type") String type)
    {
        try {
            switch (type) {
                case "ed" :
                    educationRepo.delete(id);
                    // return with an anchor tag so that the user is still at the same section after deleting
                    // this is not perfect, but it's better than jumping to the top of the page each time
                    // TODO have the page stay at the same position after deleting a record
                    return "redirect:/editdetails#education";
                case "person" :
                    personRepo.delete(id);
                    return "redirect:/editdetails#person";
                case "workexp" :
                    workExperienceRepo.delete(id);
                    return "redirect:/editdetails#workexperiences";
                case "skill" :
                    skillRepo.delete(id);
                    return "redirect:/editdetails#skills";
            }
        } catch (Exception e) {
            // need to catch an exception that may be thrown if user refreshes the page after deleting an item.
            // refreshing the page will attempt to delete the same ID from the db, which will not exist anymore if
            // they just deleted it.  catching the exception will prevent the app from crashing, and the same page
            // will simply be redisplayed
        }

        // should never happen, but need it to compile, better to redirect, just in case something does go wrong, at
        // least this way the app will not crash
        return "redirect:/editdetails";
    }


    // id is the id to update
    // type is what table to update
    // this route is triggered when the user clicks on the 'update' link next to a row in editdetails.html
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") long id, @RequestParam("type") String type, Model model)
    {
        // no matter what view is returned, we ALWAYS will allow the submit button to work, since the form that is
        // displays can only contain a record that already exists in a repo
        model.addAttribute("disableSubmit", false);
        addPersonNameToModel(model);

        NavBarState pageState = getPageLinkState();

        switch (type) {
            case "person" :
                // get the appropriate record from the repo
                model.addAttribute("newPerson",personRepo.findOne(id));
                // set the appropriate nav bar highlight
                pageState.setHighlightPersonNav(true);
                // add the navbar state object to the model
                model.addAttribute("pageState", pageState);
                // return the appropriate view
                return "addperson";
            case "ed" :
                model.addAttribute("newEdAchievement",educationRepo.findOne(id));
                model.addAttribute("currentNumRecords", educationRepo.count());
                pageState.setHighlightEdNav(true);
                model.addAttribute("pageState", pageState);
                return "addeducation";
            case "workexp" :
                model.addAttribute("newWorkExperience",workExperienceRepo.findOne(id));
                model.addAttribute("currentNumRecords", workExperienceRepo.count());
                pageState.setHighlightWorkNav(true);
                model.addAttribute("pageState", pageState);
                return "addworkexperience";
            case "skill" :
                model.addAttribute("newSkill",skillRepo.findOne(id));
                model.addAttribute("currentNumRecords", skillRepo.count());
                pageState.setHighlightSkillNav(true);
                model.addAttribute("pageState", pageState);
                return "addskill";
        }

        // should never happen, but need it to compile, better to redirect, just in case something does go wrong, at
        // least this way the app will not crash
        return"redirect:/editdetails";
    }


    @GetMapping("/finalresume")
    public String finalResumeGet(Model model) {
        NavBarState pageState = getPageLinkState();
        pageState.setHighlightFinalNav(true);
        model.addAttribute("pageState", pageState);

        // get the one and only person from the db
        // it is not possible to get here unless a Person exists, and at least one skill and ed achievement has been entered
        Person p = personRepo.findAll().iterator().next();

        // populate the empty ArrayLists in our single Person from data in other tables
        composePerson(p);

        model.addAttribute("person", p);

        return "finalresume";
    }

    /**
     * The navbar links are disabled depending on the number of records in the various db tables.  For example, we
     * do not want to allow the user to click the EditDetails link if there are no records in any db table.
     * Note: the 'highlighted' nav bar link is set individually in each route.  Also, the navbar links contain badges
     * that show the current counts for various db tables.  These counts are updated here and will always reflect the
     * current state of the db tables.
     * @return an updated NavBarState, but the highlighted navbar link must still be set individually
     */
    private NavBarState getPageLinkState() {

        NavBarState pageState = new NavBarState();

        // add the current stat of the table counts, so the navbar badges know what to display
        pageState.setNumSkills(skillRepo.count());
        pageState.setNumWorkExps(workExperienceRepo.count());
        pageState.setNumEdAchievements(educationRepo.count());

        // disable links as necessary... don't allow user to add any resume data until AFTER they have entered their
        // personal info, also don't allow them to click any links if the repos contain too many records
        pageState.setDisableAddEdLink(personRepo.count() == 0 || educationRepo.count() >= 10);
        pageState.setDisableAddSkillLink(personRepo.count() == 0 || skillRepo.count() >= 20);
        pageState.setDisableAddWorkExpLink(personRepo.count() == 0 || workExperienceRepo.count() >= 10);

        // disable edit link if no data has been entered yet
        pageState.setDisableEditDetailsLink(personRepo.count() == 0 && skillRepo.count() == 0 && educationRepo.count() == 0
                && workExperienceRepo.count() == 0);

        // disable show final resume link until at least one ed achievement, skill, and personal info has been entered
        pageState.setDisableShowFinalLink(personRepo.count() == 0 || skillRepo.count() == 0 || educationRepo.count() == 0);

        return pageState;
    }
    
    
    /**
     * Adds the entire contents of each database table to model. Note that the object names used here must match
     * the names in the template being used: 'persons', 'edAchievements', 'workExperiences', 'skills'
     *
     * @return model, now with the entire contents of each repo
     */
    private void addDbContentsToModel(Model model) {
        // there is only one person
        model.addAttribute("persons", personRepo.findAll());
        model.addAttribute("edAchievements", educationRepo.findAll());
        model.addAttribute("workExperiences", workExperienceRepo.findAll());
        model.addAttribute("skills", skillRepo.findAll());
    }


    /**
     * Composes a person using the data from the tables in the database.  All records are read out and lists are
     * populated in person for educational achievements, work experiences, and skills.  The person itself should already
     * contain a first and last name, and an email address.  After calling this function, person should contain
     * sufficient info to create a resume.
     * @param person the Person to compose
     */
    private void composePerson(Person person) {
        // get all the records from the db
        ArrayList<EducationAchievement> edsArrayList = new ArrayList<>();
        for(EducationAchievement item : educationRepo.findAll()) {
            edsArrayList.add(item);
        }
        // add it to our Person
//        person.setEducationAchievements(edsArrayList);

        ArrayList<WorkExperience> weArrayList = new ArrayList<>();
        for(WorkExperience item : workExperienceRepo.findAll()) {
            weArrayList.add(item);
        }
//        person.setWorkExperiences(weArrayList);

        ArrayList<Skill> skillsArrayList = new ArrayList<>();
        for(Skill item : skillRepo.findAll()) {
            skillsArrayList.add(item);
        }
//        person.setSkills(skillsArrayList);
    }


    /**
     * Adds an object (firstAndLastName) to model, that is a String of the first and last name of the Person for this
     * resume. If the Person table is empty, an appropriate message is added to the model that will indicate to the user
     * that they need to add start the resume by adding personal details.  This 'backup' String should never be seen..
     * unless the user manually types in, for example, /addskill in their browser BEFORE they have entered personal details.
     * NOTE: each template that uses this must refer to it as 'firstAndLastName'
     *
     * @return model, now with 'firstAndLastName' attribute already added and ready to use in a template
     */
    private void addPersonNameToModel(Model model) {
        try {
            // try to get the single Person from the db
            Person p = personRepo.findAll().iterator().next();
            // if there was a Person, add their full name to the model
            model.addAttribute("firstAndLastName", p.getNameFirst() + " " + p.getNameLast());
        } catch (Exception e) {
            // must not have found a Person in the db, so use a placeholder name
            // this is really convenient for testing, but it also makes the app less likely to crash
            // the only way this will be shown is if the user manually enters a route before completing the
            // personal details section, because the other resume section links are disabled until the user
            // has entered their personal info
            model.addAttribute("firstAndLastName", "Please start by entering personal details");
        }
    }


}
