package com.nmerris.roboresumedb.repositories;

import com.nmerris.roboresumedb.models.EducationAchievement;
import com.nmerris.roboresumedb.models.Person;
import org.springframework.data.repository.CrudRepository;

public interface EducationRepo extends CrudRepository<EducationAchievement, Long> {

    Iterable<EducationAchievement> findAllByMyPersonIs(Person currentPerson);

}