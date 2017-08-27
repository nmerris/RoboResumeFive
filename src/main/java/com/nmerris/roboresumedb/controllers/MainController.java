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
        System.out.println("++++++++++++++++++++++++++++++ JUST ENTERED /login GET route ++++++++++++++++++");
        return "login";
    }


    @GetMapping("/logout")
    public String logout() {
        System.out.println("++++++++++++++++++++++++++++++ JUST ENTERED /logOUT GET route ++++++++++++++++++");
        return "login";
    }


    @GetMapping("/")
    public String indexPageGet() {
        System.out.println("++++++++++++++++++++++++++++++ JUST ENTERED /DEFAULT GET route ++++++++++++++++++");
        // redirect is like clicking a link on a web page, this route will not even show a view, it just redirects
        // the user to the addperson route
        return "redirect:/addperson";
    }


    // any time this route is called, all db tables are wiped out
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
        System.out.println("++++++++++++++++++++++++++++++ JUST ENTERED /addperson GET route ++++++++++++++++++");

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
        pageState.setHighlightPersonNav(true);
        model.addAttribute("pageState", pageState);

        return "addperson";
    }

    @PostMapping("/addperson")
    public String addPersonPost(@Valid @ModelAttribute("newPerson") Person person,
                                BindingResult bindingResult, Model model) {
        System.out.println("++++++++++++++++++++++++++++++ JUST ENTERED /addperson POST route ++++++++++++++++++");

        if(bindingResult.hasErrors()) {
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
//        System.out.println("++++++++++++++++++++++++++++++ JUST ENTERED /addeducation GET route ++++++++++++++++++");

        // disable the submit button if >= 10 records in db, it would never be possible for the user to click to get
        // here from the navi page if there were already >= 10 records, however they could manually type in the URL
        // so I want to disable the submit button if they do that and there are already 10 records
        model.addAttribute("disableSubmit", educationRepo.count() >= 10);
        model.addAttribute("currentNumRecords", educationRepo.count());

        NavBarState pageState = getPageLinkState();
        pageState.setHighlightEdNav(true);
        model.addAttribute("pageState", pageState);

        addPersonNameToModel(model);

        model.addAttribute("newEdAchievement", new EducationAchievement());

        return "addeducation";
    }

    @PostMapping("/addeducation")
    public String addEdPost(@Valid @ModelAttribute("newEdAchievement") EducationAchievement educationAchievement,
                            BindingResult bindingResult, Model model) {
//        System.out.println("++++++++++++++++++++++++++++++ JUST ENTERED /addeducation POST route ++++++++++++++++++ ");

        model.addAttribute("edAchievementJustAdded", educationAchievement);
        addPersonNameToModel(model);

        if(bindingResult.hasErrors()) {
            NavBarState pageState = getPageLinkState();
            pageState.setHighlightEdNav(true);
            model.addAttribute("pageState", pageState);

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
        // also need to set disableSubmit flag AFTER adding to db, or user will think they can add more than 10
        // because the 'add another' button will work, but then the entry form button will be disabled, this
        // way the user will not be confused
        model.addAttribute("disableSubmit", educationRepo.count() >= 10);

        // the navbar state depends on the db table counts in various ways, so always need to have an updated navbar
        // state after saving to the repo
        NavBarState pageState = getPageLinkState();
        pageState.setHighlightEdNav(true);
        model.addAttribute("pageState", pageState);

        return "addeducationconfirmation";
    }



    @GetMapping("/addworkexperience")
    public String addWorkGet(Model model) {
//        System.out.println("++++++++++++++++++++++++++++++ JUST ENTERED /addworkexperience GET route ++++++++++++++++++");

        NavBarState pageState = getPageLinkState();
        pageState.setHighlightWorkNav(true);
        model.addAttribute("pageState", pageState);

        addPersonNameToModel(model);
        model.addAttribute("disableSubmit", workExperienceRepo.count() >= 10);
        model.addAttribute("currentNumRecords", workExperienceRepo.count());
        model.addAttribute("newWorkExperience", new WorkExperience());

        return "addworkexperience";
    }

    @PostMapping("/addworkexperience")
    public String addWorkPost(@Valid @ModelAttribute("newWorkExperience") WorkExperience workExperience,
                            BindingResult bindingResult, Model model) {
//        System.out.println("++++++++++++++++++++++++++++++ JUST ENTERED /addworkexperience POST route ++++++++++++++++++ ");

        // add a placeholder for end date that is todays date, because why not?
        model.addAttribute("todaysDate", Utilities.getTodaysDateString());
        model.addAttribute("workExperienceJustAdded", workExperience);
        addPersonNameToModel(model);

        if(bindingResult.hasErrors()) {
            NavBarState pageState = getPageLinkState();
            pageState.setHighlightWorkNav(true);
            model.addAttribute("pageState", pageState);
            model.addAttribute("currentNumRecords", workExperienceRepo.count());
            model.addAttribute("disableSubmit", workExperienceRepo.count() >= 10);

            return "addworkexperience";
        }

        model.addAttribute("dateEndString", Utilities.getMonthDayYearFromDate(workExperience.getDateEnd()));

        // I'm being picky here, but it is possible for the user to refresh the page, which bypasses the form submit
        // button, and so they would be able to add more than 10 items, to avoid this, just condition the db save on count
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
    


    @GetMapping("/addskill")
    public String addSkillGet(Model model) {
//        System.out.println("++++++++++++++++++++++++++++++ JUST ENTERED /addskill GET route ++++++++++++++++++");

        addPersonNameToModel(model);
        model.addAttribute("currentNumRecords", skillRepo.count());
        model.addAttribute("newSkill", new Skill());
        model.addAttribute("disableSubmit", skillRepo.count() >= 20);

        NavBarState pageState = getPageLinkState();
        pageState.setHighlightSkillNav(true);
        model.addAttribute("pageState", pageState);

        return "addskill";
    }

    @PostMapping("/addskill")
    public String addSkillPost(@Valid @ModelAttribute("newSkill") Skill skill,
                              BindingResult bindingResult, Model model) {
//        System.out.println("++++++++++++++++++++++++++++++ JUST ENTERED /addskill POST route ++++++++++++++++++ ");

        addPersonNameToModel(model);
        model.addAttribute("skillJustAdded", skill);

        if(bindingResult.hasErrors()) {
            NavBarState pageState = getPageLinkState();
            pageState.setHighlightSkillNav(true);
            model.addAttribute("pageState", pageState);
            model.addAttribute("currentNumRecords", skillRepo.count());

            return "addskill";
        }

        // I'm being picky here, but it is possible for the user to refresh the page, which bypasses the form submit
        // button, and so they would be able to add more than 10 items, to avoid this, just condition the db save on co
        if(skillRepo.count() < 20) {
            skillRepo.save(skill);
        }

        NavBarState pageState = getPageLinkState();
        pageState.setHighlightSkillNav(true);
        model.addAttribute("pageState", pageState);

        model.addAttribute("currentNumRecords", skillRepo.count());
        model.addAttribute("disableSubmit", skillRepo.count() >= 20);

        return "addskillconfirmation";
    }



    @GetMapping("/editdetails")
    public String editDetails(Model model) {
        addPersonNameToModel(model);
        addDbContentsToModel(model);

        NavBarState pageState = getPageLinkState();
        pageState.setHighlightEditNav(true);
        model.addAttribute("pageState", pageState);

        return "editdetails";
    }


    // id is the id to delete
    // type is what table to delete from
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, @RequestParam("type") String type, Model model)
    {
        try {
            switch (type) {
                case "ed" :
                    educationRepo.delete(id);
                    break;
                case "person" :
                    personRepo.delete(id);
                    break;
                case "workexp" :
                    workExperienceRepo.delete(id);
                    break;
                case "skill" :
                    skillRepo.delete(id);
            }
        } catch (Exception e) {
            // need to catch an exception that may be thrown if user refreshes the page after deleting an item.
            // refreshing the page will attempt to delete the same ID from the db, which will not exist anymore if
            // they just deleted it.  catching the exception will prevent the app from crashing, and the same page
            // will simply be redisplayed
        }

        NavBarState pageState = getPageLinkState();
        pageState.setHighlightEditNav(true);
        model.addAttribute("pageState", pageState);

        addPersonNameToModel(model);
        addDbContentsToModel(model);

        // TODO would be nice to return with an anchor tag to the section user was just on, not as simple as it seems
        return "editdetails";
    }



    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") long id, @RequestParam("type") String type, Model model)
    {
        model.addAttribute("disableSubmit", false);
        addPersonNameToModel(model);

        NavBarState pageState = getPageLinkState();
        pageState.setHighlightEditNav(true);
        model.addAttribute("pageState", pageState);

        switch (type) {
            case "person" :
                model.addAttribute("newPerson",personRepo.findOne(id));
                pageState.setHighlightPersonNav(true);
                model.addAttribute("pageState", pageState);
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

        // should never happen, but need it to compile
        return"index";
    }



    @GetMapping("/finalresume")
    public String finalResumeGet(Model model) {

        NavBarState pageState = getPageLinkState();
        pageState.setHighlightFinalNav(true);
        model.addAttribute("pageState", pageState);

        // get the one and only person from the db
        // it is not possible to get here unless a Person exists, and the other resume data has been entered
        Person p = personRepo.findAll().iterator().next();

        // populate the empty ArrayLists in our single Person from data in other tables
        composePerson(p);

        model.addAttribute("person", p);

        return "finalresume";
    }

    

    private NavBarState getPageLinkState() {
        // the navi links are disabled depending on the number of records in the various db tables
        // note: the 'highlighted' nav bar choice is set individually in each route
        NavBarState pageState = new NavBarState();

        // add the current stat of the table counts, so the navbar badges know what to display
        pageState.setNumSkills(skillRepo.count());
        pageState.setNumWorkExps(workExperienceRepo.count());
        pageState.setNumEdAchievements(educationRepo.count());

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
     * the names in the template being used.
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
        person.setEducationAchievements(edsArrayList);

        ArrayList<WorkExperience> weArrayList = new ArrayList<>();
        for(WorkExperience item : workExperienceRepo.findAll()) {
            weArrayList.add(item);
        }
        person.setWorkExperiences(weArrayList);

        ArrayList<Skill> skillsArrayList = new ArrayList<>();
        for(Skill item : skillRepo.findAll()) {
            skillsArrayList.add(item);
        }
        person.setSkills(skillsArrayList);
    }


    /**
     * Adds an object (firstAndLastName) to model, that is a String of the first and last name of the Person for this
     * resume. If the Person table is empty, an appropriate message is added to the model that will indicate to the user
     * that they need to add start the resume by adding personal details.
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
