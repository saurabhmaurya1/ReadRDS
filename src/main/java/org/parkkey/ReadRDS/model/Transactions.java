package org.parkkey.ReadRDS.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class Transactions {

    private String transactionID;
    private double amount;
    private String createdDate;
    private String modeOfPayment;
    private String transactionType;
    private String updatedDate;

    private String parkingSpaceID;

    private String parkingTicketID;
    private String userID;
    private String vendorID;

    // getters and setters
}