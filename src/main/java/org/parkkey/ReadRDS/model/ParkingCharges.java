package org.parkkey.ReadRDS.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class ParkingCharges {


    private String parkingSpaceID;
    private String createdDate;
    private Number fixedTime;
    private Number fixedTimeCharge;
    private Number tariffRate;
    private Number tariffTime;
    private String updatedDate;
}

