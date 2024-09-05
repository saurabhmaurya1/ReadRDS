package org.parkkey.ReadRDS.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class Wallet  {

    private String userID;
    private String createdDate;
    private String updatedDate;
    private double walletAmount;

}