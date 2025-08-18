package org.parkkey.ReadRDS.queries.customerApp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.parkkey.ReadRDS.util.Constants;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FetchPaymentHistory {


    public static FetchPaymentHistoryOutput handleRequest(FetchPaymentHistoryInput input, JdbcTemplate jdbcTemplate) {
        String sql = """
            SELECT amount, modeOfPayment, step, parkingName, Transactions.createdDate
            FROM Transactions
            LEFT JOIN ParkingSpace ON ParkingSpace.parkingSpaceID = Transactions.parkingSpaceID
            WHERE userID = ? AND amount > 0
        """;

        List<Object> params = new ArrayList<>();
        params.add(input.getUserID());

        List<PaymentHistory> paymentHistoryList = new ArrayList<>();
        Response response;

        try {
            paymentHistoryList = jdbcTemplate.query(sql, params.toArray(), new PaymentHistoryRowMapper());
            if (paymentHistoryList.isEmpty()) {
                response = new Response(Constants.NO_RECORDS_FOUND_CODE, "No records found for the provided user.");
            } else {
                response = new Response(Constants.SUCCESS_RESPONSE_CODE, Constants.SUCCESS_RESPONSE_MESSAGE);
            }
        } catch (DataAccessException e) {
            response = new Response(Constants.INVALID_INPUTS_RESPONSE_CODE, "Database error occurred: " + e.getMessage());
        } catch (Exception e) {
            response = new Response(Constants.INVALID_INPUTS_RESPONSE_CODE, "An unexpected error occurred: " + e.getMessage());
        }

        return new FetchPaymentHistoryOutput(response, paymentHistoryList);
    }

    private static class PaymentHistoryRowMapper implements RowMapper<PaymentHistory> {
        @Override
        public PaymentHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
            PaymentHistory paymentHistory = new PaymentHistory();

            paymentHistory.setModeOfPayment(rs.getString("modeOfPayment") != null ? rs.getString("modeOfPayment") : "");
            paymentHistory.setAmount(rs.getString("amount") != null ? rs.getString("amount") : "");
            paymentHistory.setStep(rs.getString("step") != null ? rs.getString("step") : "");
            paymentHistory.setParkingName(rs.getString("parkingName") != null ? rs.getString("parkingName") : "");
            paymentHistory.setCreatedDate(rs.getString("createdDate") != null ? rs.getString("createdDate") : "");

            return paymentHistory;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private int responseCode;
        private String message;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentHistory {
        private String amount;
        private String modeOfPayment;
        private String step;
        private String parkingName;
        private String createdDate;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FetchPaymentHistoryInput {
        private String userID;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FetchPaymentHistoryOutput {
        private Response response;
        private List<PaymentHistory> paymentHistoryList;
    }
}
