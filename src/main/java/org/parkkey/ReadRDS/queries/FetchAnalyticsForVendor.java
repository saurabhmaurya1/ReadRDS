package org.parkkey.ReadRDS.queries;

import org.parkkey.ReadRDS.model.BookingStatistics;
import org.parkkey.ReadRDS.model.VehicleDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class FetchAnalyticsForVendor {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public BookingStatistics getBookingStatisticsForVendor() {
        String query = "SELECT " +
                "(SELECT COUNT(*) FROM ParkingTicket WHERE DATE_FORMAT(entryDateTime, '%Y-%m') = ? AND vendorID=?) AS current_month_bookings, " +
                "(SELECT COUNT(*) FROM ParkingTicket WHERE DATE_FORMAT(entryDateTime, '%Y-%m') = ? AND vendorID=?) AS last_month_bookings, " +
                "(SELECT SUM(totalCharges) FROM ParkingTicket WHERE DATE_FORMAT(entryDateTime, '%Y-%m' AND vendorID=?) = ?) AS current_month_revenue, " +
                "(SELECT SUM(totalCharges) FROM ParkingTicket WHERE DATE_FORMAT(entryDateTime, '%Y-%m') = ? AND vendorID=?) AS last_month_revenue, " +
                "(SELECT COUNT(*) FROM ParkingTicket WHERE DATE(entryDateTime) = ? AND vendorID=?) AS todays_bookings, " +
                "(SELECT SUM(totalCharges) FROM ParkingTicket WHERE DATE(entryDateTime) = ? AND vendorID=?) AS todays_revenue";

        // Calculate the current month, previous month, and current date
        String currentMonth = getCurrentMonth();
        String lastMonth = getLastMonth();
        String currentDate = getCurrentDate();

        // Execute the query with parameters
        return jdbcTemplate.queryForObject(query, new Object[]{currentMonth, lastMonth, currentMonth, lastMonth, currentDate, currentDate}, this::mapRowToBookingStatistics);
    }

    private BookingStatistics mapRowToBookingStatistics(ResultSet rs, int rowNum) throws SQLException {
        BookingStatistics stats = new BookingStatistics();
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

    private String getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        return String.format("%d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
    }

    private String getLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return String.format("%d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
    }

    private String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }


    public List<VehicleDetails> getVehicleDetails() {
        String sql = "SELECT " +
                "v.vehicleNo, " +
                "u.mobileNo, " +
                "pt.entryDateTime AS entryTime, " +
                "pt.exitDateTime AS exitTime, " +
                "pt.totalCharges AS totalAmount, " +
                "ps.parkingName, " +
                "e.employeeName " +
                "FROM Vehicle v " +
                "JOIN ParkingTicket pt ON v.vehicleID = pt.vehicleID " +
                "JOIN User u ON v.userID = u.userID " +
                "JOIN ParkingSpace ps ON pt.parkingSpaceID = ps.parkingSpaceID " +
                "JOIN Employee e ON pt.employeeID = e.employeeID";

        return jdbcTemplate.query(sql, new VehicleDetailsRowMapper());
    }

    private static class VehicleDetailsRowMapper implements RowMapper<VehicleDetails> {
        @Override
        public VehicleDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
            VehicleDetails vehicleDetails = new VehicleDetails();
            vehicleDetails.setVehicleNo(rs.getString("vehicleNo"));
            vehicleDetails.setMobileNo(rs.getString("mobileNo"));
            vehicleDetails.setEntryTime(rs.getString("entryTime"));
            vehicleDetails.setExitTime(rs.getString("exitTime"));
            vehicleDetails.setTotalAmount(rs.getString("totalAmount"));
            vehicleDetails.setParkingName(rs.getString("parkingName"));
            vehicleDetails.setEmployeeName(rs.getString("employeeName"));
            return vehicleDetails;
        }
    }
}


