package org.parkkey.ReadRDS.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class TransactionHistory  {
    private String paymentID;
    private double amount;
    private String createdDate;
    private String paymentStatus;
    private String updatedDate;
    private String userID;

}