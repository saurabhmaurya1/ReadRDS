package org.parkkey.ReadRDS.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class ParkingTicket{

    private String parkingTicketID;
    private double charges;
    private String createdDate;
    private String entryDate;
    private String entryDateTime;
    private String exitDate;
    private String exitDateTime;
    private double gatewayCharges;
    private int parkedDuration;
    private String parkingStatus;
    private String paymentStatus;
    private double totalCharges;
    private String updatedDate;
    private String employeeID;
    private String parkingSpaceID;
    private String userID;
    private String vehicleID;
    private String vendorID;


}