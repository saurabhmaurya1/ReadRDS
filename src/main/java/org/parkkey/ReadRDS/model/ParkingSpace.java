package org.parkkey.ReadRDS.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor

public class ParkingSpace  {

    private String parkingSpaceID;
    private int availableSpace;
    private String createdDate;
    private String latitude;
    private String location;
    private String longitude;
    private String updatedDate;
    private String parkingName;
    private String parkingSpaceStatus;
    private int rating;
    private String review;
    private int totalSpace;
    private String vendorID;

}