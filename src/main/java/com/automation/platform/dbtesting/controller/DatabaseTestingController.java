package com.automation.platform.dbtesting.controller;

import com.automation.platform.dbtesting.model.CustomerCsv;
import com.automation.platform.dbtesting.model.CustomerDetails;
import com.automation.platform.dbtesting.service.ComparatorService;
import com.automation.platform.dbtesting.service.CsvProcessorService;
import com.automation.platform.dbtesting.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This is only for Demo purpose. This can act as POC for any db testing. The idea is to use Jdbcrepository layer and use service layer for comparsion purpose
 * Due to lack of scope, the testing is tightly coupled with a single use case
 */
@RestController
@Profile("dbtest")
public class DatabaseTestingController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CsvProcessorService csvProcessorService;

    @Autowired
    private ComparatorService comparatorService;

    @GetMapping("/customer")
    private List<CustomerDetails> getAllCustomerDetail() {
        return customerService.getAllCustomer();
    }

    @GetMapping("/csvcustomer")
    private List<CustomerCsv> getAllCustomerDetailFromCsv() {
        return csvProcessorService.readCsvFile();
    }

    @GetMapping("/update")
    public List<CustomerDetails> updateAndGetAllCustomers() {
        List<CustomerDetails> detailsList = getAllCustomerDetail();
        return customerService.updateAndEncrypt(detailsList);
    }

    @GetMapping("/compare")
    private String runCompare() {
        return comparatorService.runComparison();
    }
}