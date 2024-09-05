package org.parkkey.ReadRDS.queries.Vendor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FetchVendorAnalyticsData {

    public static FetchVendorAnalyticsDataOutput handleRequest(FetchVendorAnalyticsDataInput input, JdbcTemplate jdbcTemplate) {

        String query = "SELECT " +
                "(SELECT COUNT(*) FROM ParkingTicket WHERE DATE_FORMAT(entryDateTime, '%Y-%m') = ? AND vendorID = ?) AS current_month_bookings, " +
                "(SELECT COUNT(*) FROM ParkingTicket WHERE DATE_FORMAT(entryDateTime, '%Y-%m') = ? AND vendorID = ?) AS last_month_bookings, " +
                "(SELECT SUM(totalCharges) FROM ParkingTicket WHERE DATE_FORMAT(entryDateTime, '%Y-%m') = ? AND vendorID = ?) AS current_month_revenue, " +
                "(SELECT SUM(totalCharges) FROM ParkingTicket WHERE DATE_FORMAT(entryDateTime, '%Y-%m') = ? AND vendorID = ?) AS last_month_revenue, " +
                "(SELECT COUNT(*) FROM ParkingTicket WHERE DATE(entryDateTime) = ? AND vendorID = ?) AS todays_bookings, " +
                "(SELECT SUM(totalCharges) FROM ParkingTicket WHERE DATE(entryDateTime) = ? AND vendorID = ?) AS todays_revenue";

        // Calculate the current month, previous month, and current date
        String currentMonth = getCurrentMonth();
        String lastMonth = getLastMonth();
        String currentDate = getCurrentDate();

        // Execute the query with parameters
        return jdbcTemplate.queryForObject(query,
                new Object[]{currentMonth, input.getVendorID(), lastMonth, input.getVendorID(),
                        currentMonth, input.getVendorID(), lastMonth, input.getVendorID(),
                        currentDate, input.getVendorID(), currentDate, input.getVendorID()},
                FetchVendorAnalyticsData::mapRowToBookingStatistics);
    }

    private static FetchVendorAnalyticsDataOutput mapRowToBookingStatistics(ResultSet rs, int rowNum) throws SQLException {
        FetchVendorAnalyticsDataOutput stats = new FetchVendorAnalyticsDataOutput();
        stats.setCurrentMonthBookings(rs.getInt("current_month_bookings"));
        stats.setLastMonthBookings(rs.getInt("last_month_bookings"));
        stats.setCurrentMonthRevenue(rs.getDouble("current_month_revenue"));
        stats.setLastMonthRevenue(rs.getDouble("last_month_revenue"));
        stats.setTodaysBookings(rs.getInt("todays_bookings"));
        stats.setTodaysRevenue(rs.getDouble("todays_revenue"));

        // Calculate the percentage changes
        if (stats.getLastMonthBookings() != 0) {
            stats.setBookingsChangePercentage(((double) (stats.getCurrentMonthBookings() - stats.getLastMonthBookings()) / stats.getLastMonthBookings()) * 100);
        } else {
            stats.setBookingsChangePercentage(0); // or some default value if last month's bookings were zero
        }

        if (stats.getLastMonthRevenue() != 0) {
            stats.setRevenueChangePercentage(((stats.getCurrentMonthRevenue() - stats.getLastMonthRevenue()) / stats.getLastMonthRevenue()) * 100);
        } else {
            stats.setRevenueChangePercentage(0); // or some default value if last month's revenue was zero
        }

        return stats;
    }

    private static String getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        return String.format("%d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
    }

    private static String getLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return String.format("%d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
    }

    private static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FetchVendorAnalyticsDataOutput {
        private int currentMonthBookings;
        private int lastMonthBookings;
        private double currentMonthRevenue;
        private double lastMonthRevenue;
        private double bookingsChangePercentage;
        private double revenueChangePercentage;
        private int todaysBookings;
        private double todaysRevenue;

    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FetchVendorAnalyticsDataInput {
        private String vendorID;

    }
}
