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

public class FetchVehicleDetails {

    public static FetchVehicleDetailsOutput handleRequest(FetchVehicleDetailsInput input ,JdbcTemplate jdbcTemplate) {
        String sql = "SELECT Vehicle.vehicleNo, " +
                "User.mobileNo, " +
                "ParkingTicket.entryDateTime, " +
                "ParkingTicket.exitDateTime, " +
                "ParkingTicket.parkingStatus, " +
                "ParkingSpace.parkingName, " +
                "Employee.employeeName " +
                "FROM ParkingTicket " +
                "LEFT JOIN Vehicle ON Vehicle.vehicleID = ParkingTicket.vehicleID " +
                "LEFT JOIN User ON User.userID = Vehicle.userID " +
                "LEFT JOIN ParkingSpace ON ParkingSpace.parkingSpaceID = ParkingTicket.parkingSpaceID " +
                "LEFT JOIN Employee ON Employee.employeeID = ParkingTicket.employeeID ";

        String whereClause = "Where 1=1 ";
        List<Object> params = new ArrayList<>();

        if (input.getSearch() != null && !input.getSearch().isBlank()) {
            whereClause+=("AND (Vehicle.vehicleNo = ? OR User.mobileNo = ?) ");
            params.add(input.getSearch());
            params.add(input.getSearch());
        }

        if (input.getParkingID() != null && !input.getParkingID().isBlank()) {
            whereClause+=("AND ParkingTicket.parkingSpaceID = ? ");
            params.add(input.getParkingID());
        }

        if (input.getVendorID() != null && !input.getVendorID().isBlank()) {
            whereClause+=("AND ParkingTicket.vendorID = ? ");
            params.add(input.getVendorID());
        }
        if (input.getEmployeeID() != null && !input.getEmployeeID().isBlank()) {
            whereClause+=("AND ParkingTicket.employeeID = ? ");
            params.add(input.getEmployeeID());
        }

        sql+=whereClause;
        sql+="Order by ParkingTicket.updatedDate desc ";


        List<VehicleDetails> vehicleDetailsList = jdbcTemplate.query(sql,params.toArray(), new VehicleDetailsRowMapper());

        if (vehicleDetailsList.isEmpty()) {
            return new FetchVehicleDetailsOutput(
                    new Response(Constants.NO_RECORDS_FOUND_CODE, "No records found for the provided search criteria."),
                    vehicleDetailsList);
        }
        return new FetchVehicleDetailsOutput(new Response(Constants.SUCCESS_RESPONSE_CODE,Constants.SUCCESS_RESPONSE_MESSAGE),vehicleDetailsList);
    }

    private static class VehicleDetailsRowMapper implements RowMapper<VehicleDetails> {
        @Override
        public VehicleDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
            VehicleDetails vehicleDetails = new VehicleDetails();

            vehicleDetails.setVehicleNo(rs.getString("vehicleNo") != null ? rs.getString("vehicleNo") : "");
            vehicleDetails.setMobileNo(rs.getString("mobileNo") != null ? rs.getString("mobileNo") : "");
            vehicleDetails.setEntryTime(rs.getString("entryDateTime") != null ? rs.getString("entryDateTime") : "");
            vehicleDetails.setExitTime(rs.getString("exitDateTime") != null ? rs.getString("exitDateTime") : "");
            vehicleDetails.setParkingName(rs.getString("parkingName") != null ? rs.getString("parkingName") : "");
            vehicleDetails.setEmployeeName(rs.getString("employeeName") != null ? rs.getString("employeeName") : "");

            return vehicleDetails;
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VehicleDetails {
        private String vehicleNo;
        private String mobileNo;
        private String entryTime;
        private String exitTime;
        private String parkingName;
        private String employeeName;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FetchVehicleDetailsInput {
        private String search;
        private String parkingID;
        private String vendorID;
        private String employeeID;

    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FetchVehicleDetailsOutput {
        private Response response;
        private List<VehicleDetails> vehicleDetailsList;
    }

    @Getter@Setter@NoArgsConstructor@AllArgsConstructor
    public  static class Response{
        int responseCode;
        String message;
    }
}
