package org.parkkey.ReadRDS.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter@NoArgsConstructor@AllArgsConstructor

public class Vehicle {

    private String vehicleID;
    private String createdDate;
    private String parkingStatus;
    private String updatedDate;
    private String vehicleNo;
    private String vehicleType;
    private String userID;

    // getters and setters
}