package com.nmerris.roboresumedb.repositories;

import com.nmerris.roboresumedb.models.EducationAchievement;
import org.springframework.data.repository.CrudRepository;

public interface EducationRepo extends CrudRepository<EducationAchievement, Long> {
}