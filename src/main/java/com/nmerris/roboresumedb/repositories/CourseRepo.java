package com.nmerris.roboresumedb.repositories;

import com.nmerris.roboresumedb.models.Course;
import com.nmerris.roboresumedb.models.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepo extends CrudRepository<Course, Long> {

    Iterable<Course> findAllByPeopleIs(Person currentPerson);
//    Iterable<Course> findAllByPeopleIsNot(Person currentPerson);
    List<Course> findAllByPeopleIsNot(Person currentPerson);
}
