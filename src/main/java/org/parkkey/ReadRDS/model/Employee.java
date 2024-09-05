package org.parkkey.ReadRDS.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class Employee  {

    private String employeeID;
    private String createdDate;
    private String employeeName;
    private String employeeStatus;
    private String gender;
    private String mobileNo;
    private String updatedDate;
    private String parkingSpaceID;
    private String vendorID;


}