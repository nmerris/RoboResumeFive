package com.nmerris.roboresumedb.repositories;

import com.nmerris.roboresumedb.models.Person;
import com.nmerris.roboresumedb.models.Skill;
import org.springframework.data.repository.CrudRepository;

public interface SkillRepo extends CrudRepository<Skill, Long> {

    // returns all the records associated with currentPerson
    Iterable<Skill> findAllByMyPersonIs(Person currentPerson);

    // returns the count of all the records associated with currentPerson
    long countAllByMyPersonIs(Person currentPerson);

    // returns the number of removed records
    long removeAllByMyPersonIs(Person currentPerson);

}