package org.parkkey.ReadRDS.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter@Getter@NoArgsConstructor@AllArgsConstructor
public class User{
    private String userID;
    private String createdDate;
    private String updatedDate;
    private String mobileNo;


}