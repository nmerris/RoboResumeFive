package com.nmerris.roboresumedb.repositories;

import com.nmerris.roboresumedb.models.Person;
import com.nmerris.roboresumedb.models.WorkExperience;
import org.springframework.data.repository.CrudRepository;

public interface WorkExperienceRepo extends CrudRepository<WorkExperience, Long> {

    // returns all the records associated with currentPerson
    Iterable<WorkExperience> findAllByMyPersonIs(Person currentPerson);

    // returns the count of all the records associated with currentPerson
    long countAllByMyPersonIs(Person currentPerson);

    // returns the number of removed records
    long removeAllByMyPersonIs(Person currentPerson);

}