package org.parkkey.ReadRDS.queries.Admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.parkkey.ReadRDS.util.Constants;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FetchEmployeeDetails {

    public static FetchEmployeeDetailsOutput handleRequest(FetchEmployeeDetailsInput input, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT Employee.employeeID, " +
                "Employee.employeeName, " +
                "ParkingSpace.parkingName, " +
                "COUNT(ParkingTicket.parkingTicketID) AS todaysBooking " +
                "FROM Employee " +
                "LEFT JOIN ParkingSpace ON Employee.parkingSpaceID = ParkingSpace.parkingSpaceID " +
                "LEFT JOIN ParkingTicket ON Employee.employeeID = ParkingTicket.employeeID " +
                "AND ParkingTicket.createdDate = CURDATE() ";

        String whereClause = "";
        List<Object> params = new ArrayList<>();

        // Only applying the where clause on parkingSpaceID
        if (input.getParkingSpaceID() != null && !input.getParkingSpaceID().isBlank()) {
            whereClause += "WHERE Employee.parkingSpaceID = ? ";
            params.add(input.getParkingSpaceID());
        }

        sql += whereClause;
        sql += "GROUP BY Employee.employeeID, Employee.employeeName, ParkingSpace.parkingName ";

        List<EmployeeDetails> employeeDetailsList = jdbcTemplate.query(sql, params.toArray(), new EmployeeDetailsRowMapper());

        if (employeeDetailsList.isEmpty()) {
            return new FetchEmployeeDetailsOutput(
                    new Response(Constants.NO_RECORDS_FOUND_CODE, "No employee records found for the provided parking space ID."),
                    employeeDetailsList);
        }

        return new FetchEmployeeDetailsOutput(new Response(Constants.SUCCESS_RESPONSE_CODE, Constants.SUCCESS_RESPONSE_MESSAGE), employeeDetailsList);
    }

    private static class EmployeeDetailsRowMapper implements RowMapper<EmployeeDetails> {
        @Override
        public EmployeeDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
            EmployeeDetails employeeDetails = new EmployeeDetails();

            employeeDetails.setEmployeeID(rs.getString("employeeID") != null ? rs.getString("employeeID") : "");
            employeeDetails.setEmployeeName(rs.getString("employeeName") != null ? rs.getString("employeeName") : "");
            employeeDetails.setParkingName(rs.getString("parkingName") != null ? rs.getString("parkingName") : "");
            employeeDetails.setTodaysBooking(rs.getInt("todaysBooking"));

            return employeeDetails;
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmployeeDetails {
        private String employeeID;
        private String employeeName;
        private String parkingName;
        private int todaysBooking;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FetchEmployeeDetailsInput {
        private String parkingSpaceID;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FetchEmployeeDetailsOutput {
        private Response response;
        private List<EmployeeDetails> employeeDetailsList;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private int responseCode;
        private String message;
    }
}
