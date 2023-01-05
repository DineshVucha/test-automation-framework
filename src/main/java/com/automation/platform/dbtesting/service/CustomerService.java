package com.automation.platform.dbtesting.service;

import com.automation.platform.dbtesting.model.CustomerDetails;
import com.automation.platform.dbtesting.model.EmergencyDetails;
import com.automation.platform.dbtesting.model.FamilyDetails;
import com.automation.platform.dbtesting.repository.CustomerRepository;
import com.automation.platform.dbtesting.repository.EmergencyRepository;
import com.automation.platform.dbtesting.repository.FamilyRepository;
import com.automation.platform.encryptdecrypt.AESEncryptDecryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.automation.platform.dbtesting.constants.ComparatorConstants.COMPARE_SECRET_KEY;

@Service
@Profile("dbtest")
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private EmergencyRepository emergencyRepository;

    public CustomerService(CustomerRepository customerRepository, FamilyRepository familyRepository, EmergencyRepository emergencyRepository) {
        this.customerRepository = customerRepository;
        this.familyRepository = familyRepository;
        this.emergencyRepository = emergencyRepository;
    }

    public List<CustomerDetails> getAllCustomer() {
        List<CustomerDetails> customerAllList = new ArrayList<>();
        List<CustomerDetails> customerDetails = new ArrayList<>();
        List<FamilyDetails> familyDetails = new ArrayList<>();
        List<EmergencyDetails> emergencyDetails = new ArrayList<>();
        customerRepository.findAll().forEach(customerDetails::add);
        familyRepository.findAll().forEach(familyDetails::add);
        emergencyRepository.findAll().forEach(emergencyDetails::add);
        for(CustomerDetails customer : customerDetails) {
            int cid = customer.getCid();
            customer.setEmergencyDetails(emergencyDetails.stream().filter(e->e.getEmergencyId() == cid).findFirst().get());
            customer.setFamilyDetails(familyDetails.stream().filter(e->e.getFamily_id() == cid).findFirst().get());
            customerAllList.add(customer);
        }
        return customerAllList;
    }

    public List<CustomerDetails> updateAndEncrypt(List<CustomerDetails> customerDetails) {
        List<CustomerDetails> updateDetailList = new ArrayList<>();
        customerDetails.forEach(customer -> {
            String encryptVal = AESEncryptDecryptUtil.encrypt(customer.getLastName(), COMPARE_SECRET_KEY);
            customer.setLastName(encryptVal);
            updateDetailList.add(customer);
        });
        customerRepository.saveAll(updateDetailList);
        return updateDetailList;

    }
}
