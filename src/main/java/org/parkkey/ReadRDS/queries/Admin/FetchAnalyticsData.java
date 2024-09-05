package org.parkkey.ReadRDS.queries.Admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class FetchAnalyticsData {

    public static FetchAnalyticsData.FetchAnalyticsDataOutput handleRequest(FetchAnalyticsDataInput input,JdbcTemplate jdbcTemplate){

        String query = "SELECT " +
                "(SELECT COUNT(*) FROM ParkingTicket WHERE DATE_FORMAT(createdDate, '%Y-%m') = ? #vendorID# #parkingSpaceID# #employeeID#) AS current_month_bookings, " +
                "(SELECT COUNT(*) FROM ParkingTicket WHERE DATE_FORMAT(createdDate, '%Y-%m') = ? #vendorID# #parkingSpaceID# #employeeID#) AS last_month_bookings, " +
                "(SELECT SUM(totalCharges) FROM ParkingTicket WHERE DATE_FORMAT(createdDate, '%Y-%m') = ? #vendorID# #parkingSpaceID# #employeeID#) AS current_month_revenue, " +
                "(SELECT SUM(totalCharges) FROM ParkingTicket WHERE DATE_FORMAT(createdDate, '%Y-%m') = ? #vendorID# #parkingSpaceID# #employeeID#) AS last_month_revenue, " +
                "(SELECT COUNT(*) FROM ParkingTicket WHERE DATE(createdDate) = ? #vendorID# #parkingSpaceID# #employeeID#) AS todays_bookings, " +
                "(SELECT SUM(totalCharges) FROM ParkingTicket WHERE DATE(createdDate) = ? #vendorID# #parkingSpaceID# #employeeID#) AS todays_revenue";

        // Calculate the current month, previous month, and current date
        String currentMonth = getCurrentMonth();
        String lastMonth = getLastMonth();
        String currentDate = getCurrentDate();


        List<Object> params = new ArrayList<>();
        params.add(currentMonth);
        params.add(lastMonth);
        params.add(currentMonth);
        params.add(lastMonth);
        params.add(currentDate);
        params.add(currentDate);




        query = query.replaceAll("#vendorID#", input.getVendorID() != null && !input.getVendorID().isBlank() ? " AND ParkingTicket.vendorID = '"+input.getVendorID()+"'" : "")
                .replaceAll("#parkingSpaceID#", input.getParkingSpaceID() != null && !input.getParkingSpaceID().isBlank() ? " AND ParkingTicket.parkingSpaceID = '"+input.getParkingSpaceID()+"'" : "")
                .replaceAll("#employeeID#", input.getEmployeeID() != null && !input.getEmployeeID().isBlank() ? " AND ParkingTicket.employeeID = '"+input.getEmployeeID()+"'" : "");




        // Execute the query with parameters
        return jdbcTemplate.queryForObject(query, params.toArray(), FetchAnalyticsData::mapRowToBookingStatistics);
    }

    private static FetchAnalyticsDataOutput mapRowToBookingStatistics(ResultSet rs, int rowNum) throws SQLException {
        FetchAnalyticsDataOutput stats = new FetchAnalyticsDataOutput();
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

    @Setter@Getter@AllArgsConstructor@NoArgsConstructor
    public static class FetchAnalyticsDataOutput {
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
    public static class FetchAnalyticsDataInput {
        private String parkingSpaceID;
        private String vendorID;
        private String employeeID;

    }



}
