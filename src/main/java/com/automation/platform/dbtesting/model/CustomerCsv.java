package com.automation.platform.dbtesting.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class CustomerCsv {

    private int cid;
    private String title;
    private String firstName;
    private String lastName;
    private String dob;
    private String gender;
    private int phoneNumber;
    private String emailId;

    public String eFirstName;
    public String eLastName;
    public int eMobileNumber;
    public String eRelationship;

    public String sFirstName;
    public String sLastName;
    public String sDob;
    public int sMobileNumber;
    public String sEmailId;
}
