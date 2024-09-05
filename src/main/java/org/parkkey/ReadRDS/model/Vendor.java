package org.parkkey.ReadRDS.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {

    private String vendorID;
    private String createdDate;
    private String mobileNo;
    private String updatedDate;
    private String vendorName;
    private String vendorStatus;

    // getters and setters
}