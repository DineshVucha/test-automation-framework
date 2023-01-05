package com.automation.platform.dbtesting.model;

import com.automation.platform.dbtesting.exception.BeanConversionException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Profile;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;

@Entity
@Table(name = "CUSTOMER_DETAILS")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Profile("dbtest")
public class CustomerDetails {

    @Id
    @Column(name="CUST_ID")
    private int cid;

    @Column(name="TITLE")
    private String title;
    @Column(name="FIRST_NAME")
    private String firstName;
    @Column(name="LAST_NAME")
    private String lastName;
    @Column(name="DOB")
    private String dob;
    @Column(name="GENDER")
    private String gender;
    @Column(name="MARITAL_STATUS")
    private String maritalStatus;
    @Column(name="NATIONALITY")
    private String nationality;
    @Column(name="ADDRESS_1")
    private String address1;
    @Column(name="ADDRESS_2")
    private String address2;
    @Column(name="ADDRESS_3")
    private String address3;
    @Column(name="CITY")
    private String city;
    @Column(name="STATE")
    private String state;
    @Column(name="COUNTRY")
    private String country;
    @Column(name="PINCODE")
    private int pincode;
    @Column(name="PHONE_NUMBER")
    private int phoneNumber;
    @Column(name="EMAIL_ID")
    private String emailId;

    @Transient
    private EmergencyDetails emergencyDetails;

    @Transient
    private FamilyDetails familyDetails;

    public CustomerDetails copy(CustomerCsv csv) {
        CustomerDetails details = new CustomerDetails();
        try {
            BeanUtils.copyProperties(details, csv);
            FamilyDetails familyData = new FamilyDetails().copy(csv);
            EmergencyDetails emergencyData = new EmergencyDetails().copy(csv);
            details.setFamilyDetails(familyData);
            details.setEmergencyDetails(emergencyData);
            return details;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BeanConversionException("Error during bean conversion");
        }
    }
}
