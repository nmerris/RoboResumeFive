package com.nmerris.roboresumedb.repositories;

import com.nmerris.roboresumedb.models.WorkExperience;
import org.springframework.data.repository.CrudRepository;

public interface WorkExperienceRepo extends CrudRepository<WorkExperience, Long> {
}