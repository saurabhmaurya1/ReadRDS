package org.parkkey.ReadRDS.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class VehicleDetails {

    private String vehicleNo;
    private String mobileNo;
    private String entryTime;
    private String exitTime;
    private String totalAmount;
    private String parkingName;
    private String employeeName;

}
