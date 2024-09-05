package org.parkkey.ReadRDS.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Admin{

    private String adminID;
    private String adminName;
    private String adminStatus;
    private String createdDate;
    private String gender;
    private String mobileNo;
    private String updatedDate;


}