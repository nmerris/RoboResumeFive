package com.nmerris.roboresumedb.repositories;

import com.nmerris.roboresumedb.models.EducationAchievement;
import com.nmerris.roboresumedb.models.Person;
import org.springframework.data.repository.CrudRepository;

public interface EducationRepo extends CrudRepository<EducationAchievement, Long> {

    // returns all the records associated with currentPerson
    Iterable<EducationAchievement> findAllByMyPersonIs(Person currentPerson);

    // returns the count of all the records associated with currentPerson
    long countAllByMyPersonIs(Person currentPerson);

    // returns the number of removed records
    long removeAllByMyPersonIs(Person currentPerson);

}