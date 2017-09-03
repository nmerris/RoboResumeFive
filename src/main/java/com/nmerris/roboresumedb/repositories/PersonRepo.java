package com.nmerris.roboresumedb.repositories;

import com.nmerris.roboresumedb.models.Course;
import com.nmerris.roboresumedb.models.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepo extends CrudRepository<Person, Long> {

    Iterable<Person> findAllByCoursesIs(Course someCourse);

}