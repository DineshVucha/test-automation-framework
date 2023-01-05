package com.automation.platform.dbtesting.repository;

import com.automation.platform.dbtesting.model.FamilyDetails;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

@Profile("dbtest")
public interface FamilyRepository extends CrudRepository<FamilyDetails, Integer> {
}
