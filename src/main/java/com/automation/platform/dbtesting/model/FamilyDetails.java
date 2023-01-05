package com.automation.platform.dbtesting.model;

import com.automation.platform.dbtesting.exception.BeanConversionException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Profile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.InvocationTargetException;

@Entity
@Table(name = "FAMILY_DETAILS")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Profile("dbtest")
public class FamilyDetails {
    @Column(name="CUST_ID")
    private int family_id;

    @Id
    @Column(name="S_ID")
    private int sid;
    @Column(name="S_FIRST_NAME")
    public String sFirstName;
    @Column(name="S_LAST_NAME")
    public String sLastName;
    @Column(name="S_DOB")
    public String sDob;
    @Column(name="S_GENDER")
    public String sGender;
    @Column(name="S_NATIONALITY")
    public String sNationality;
    @Column(name="S_MOBILE_NUMBER")
    public int sMobileNumber;
    @Column(name="S_EMAIL_ID")
    public String sEmailId;

    public FamilyDetails copy(CustomerCsv csv) {
        FamilyDetails details = new FamilyDetails();
        try {
            BeanUtils.copyProperties(details, csv);
            return details;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BeanConversionException("Error during bean conversion");
        }
    }
}
