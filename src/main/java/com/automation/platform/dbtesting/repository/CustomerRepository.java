package com.automation.platform.dbtesting.repository;

import com.automation.platform.dbtesting.model.CustomerDetails;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

@Profile("dbtest")
public interface CustomerRepository extends CrudRepository<CustomerDetails, Integer> {
}
