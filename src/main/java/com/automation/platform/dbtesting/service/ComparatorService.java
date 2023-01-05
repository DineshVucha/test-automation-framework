package com.automation.platform.dbtesting.service;

import com.automation.platform.dbtesting.model.CustomerCsv;
import com.automation.platform.dbtesting.model.CustomerDetails;
import com.automation.platform.encryptdecrypt.AESEncryptDecryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.automation.platform.dbtesting.constants.ComparatorConstants.COMPARE_SECRET_KEY;
import static com.automation.platform.dbtesting.util.ComparatorUtils.compareEntities;

@Service
@Profile("dbtest")
public class ComparatorService {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CsvProcessorService csvProcessorService;

    public ComparatorService(CustomerService customerService, CsvProcessorService csvProcessorService) {
        this.customerService = customerService;
        this.csvProcessorService = csvProcessorService;
    }

    public String runComparison() {
        List<CustomerDetails> dbList = customerService.getAllCustomer();
        List<CustomerDetails> customerDBList = new ArrayList<>();

        //Decrypt the column
        dbList.forEach(customer -> {
            String decryptVal = AESEncryptDecryptUtil.decrypt(customer.getLastName(), COMPARE_SECRET_KEY);
            customer.setLastName(decryptVal);
            customerDBList.add(customer);
        });

        List<CustomerCsv> customerCsvList = csvProcessorService.readCsvFile();
        List<CustomerDetails> customerCsvToDBModel = new ArrayList<>();
        customerCsvList.forEach(e -> {
            CustomerDetails detail = new CustomerDetails().copy(e);
            customerCsvToDBModel.add(detail);
        });
        List<String> ignoreField = new ArrayList<>();

        String compareResult = compareEntities(customerDBList, customerCsvToDBModel,
                ignoreField, CustomerDetails.class);
        if (compareResult != null) return compareResult;
        return "Comparison is Successful";
    }


}
