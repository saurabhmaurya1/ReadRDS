package org.parkkey.ReadRDS.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class BookingStatistics {
    private int currentMonthBookings;
    private int lastMonthBookings;
    private double currentMonthRevenue;
    private double lastMonthRevenue;
    private double bookingsChangePercentage;
    private double revenueChangePercentage;
    private int todaysBookings;
    private double todaysRevenue;
}
