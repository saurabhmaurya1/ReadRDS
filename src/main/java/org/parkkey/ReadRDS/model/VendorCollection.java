package org.parkkey.ReadRDS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorCollection {
    private String vendorID;
    private double totalCollection;
}
