package com.automation.platform.dbtesting.repository;

import com.automation.platform.dbtesting.model.EmergencyDetails;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

@Profile("dbtest")
public interface EmergencyRepository extends CrudRepository<EmergencyDetails, Integer> {
}
