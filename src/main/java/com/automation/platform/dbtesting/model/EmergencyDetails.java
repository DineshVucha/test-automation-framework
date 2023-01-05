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
@Table(name = "EMERGENCY_DETAILS")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Profile("dbtest")
public class EmergencyDetails {
    @Column(name="CUST_ID")
    private int emergencyId;

    @Id
    @Column(name="E_ID")
    private int eid;
    @Column(name="E_FIRST_NAME")
    public String eFirstName;
    @Column(name="E_LAST_NAME")
    public String eLastName;
    @Column(name="E_MOBILE_NUMBER")
    public int eMobileNumber;
    @Column(name="E_RELATIONSHIP")
    public String eRelationship;

    public EmergencyDetails copy(CustomerCsv csv) {
        EmergencyDetails details = new EmergencyDetails();
        try {
            BeanUtils.copyProperties(details, csv);
            return details;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BeanConversionException("Error during bean conversion");
        }
    }
}
