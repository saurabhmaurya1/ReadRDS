package org.parkkey.ReadRDS.controller;

import org.parkkey.ReadRDS.queries.Admin.FetchAnalyticsData;
import org.parkkey.ReadRDS.queries.Admin.FetchVehicleDetails;
import org.parkkey.ReadRDS.queries.Vendor.FetchVendorAnalyticsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MainController {



//    @GetMapping("/stats")
//    public BookingStatistics getAllStatsForAdmin(){
//        return fetchAnalyticsForAdmin.getBookingStatistics();
//    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/fetch-analytics-for-admin")
    public FetchAnalyticsData.FetchAnalyticsDataOutput handler(@RequestBody FetchAnalyticsData.FetchAnalyticsDataInput input){
        return FetchAnalyticsData.handleRequest(input,jdbcTemplate);
    }

    @PostMapping("/fetch-vehicle-list")
    public FetchVehicleDetails.FetchVehicleDetailsOutput vehicleHandler(@RequestBody FetchVehicleDetails.FetchVehicleDetailsInput input) {
        return FetchVehicleDetails.handleRequest(input,jdbcTemplate);
    }

    @PostMapping("/fetch-analytics-for-vendor")
    public FetchVendorAnalyticsData.FetchVendorAnalyticsDataOutput handler(@RequestBody FetchVendorAnalyticsData.FetchVendorAnalyticsDataInput input){
        return FetchVendorAnalyticsData.handleRequest(input,jdbcTemplate);
    }




//    @GetMapping("/tickets")
//    public List<ParkingTicket> getAllParkingTickets() {
//        return fetchAnalyticsForAdmin.fetchAllParkingTickets();
//    }
//
//
//    @GetMapping("/countVehiclePerMonthVendor")
//    public Map<String, Integer> getAllParkedVehiclePerMonthVendor(@RequestParam int year, @RequestParam int month) {
//        return fetchAnalyticsForAdmin.countTotalVehicleParkedPerMonthVendor(year,month);
//    }
//
//
//    @GetMapping("/countVehiclePerDayVendor")
//    public Map<String, Integer> getAllParkedVehiclePerDayVendor(@RequestParam int year, @RequestParam int month ,@RequestParam int day) {
//        return fetchAnalyticsForAdmin.countTotalVehicleParkedPerDayVendor(year,month,day);
//    }
//
//    @GetMapping("/countVehiclePerMonthByEmployee")
//    public Map<String, Integer> getAllParkedVehiclePerMonthEmployee(@RequestParam int year, @RequestParam int month) {
//        return fetchAnalyticsForAdmin.countTotalVehicleParkedPerMonthEmployee(year,month);
//    }
//
//
//    @GetMapping("/countVehiclePerDayByEmployee")
//    public Map<String, Integer> getAllParkedVehiclePerDayEmployee(@RequestParam int year, @RequestParam int month ,@RequestParam int day) {
//        return fetchAnalyticsForAdmin.countTotalVehicleParkedPerDayEmployee(year,month,day);
//    }
//
//
//    @GetMapping("/totalRevenuePerDayVendor")
//    public Map<String, Double> getTotalRevenuePerDayVendor(@RequestParam int year, @RequestParam int month ,@RequestParam int day){
//        return fetchAnalyticsForAdmin.getTotalRevenuePerDayVendor(year, month, day);
//    }
//
//
//    @GetMapping("/totalRevenuePerMonthVendor")
//    public Map<String, Double> getTotalRevenuePerMonthVendor(@RequestParam int year, @RequestParam int month){
//        return fetchAnalyticsForAdmin.getTotalRevenuePerMonthVendor(year, month);
//    }
//
//    @GetMapping("/totalRevenuePerDayEmployee")
//    public Map<String, Double> getTotalRevenuePerDayEmployee(@RequestParam int year, @RequestParam int month ,@RequestParam int day){
//        return fetchAnalyticsForAdmin.getTotalRevenuePerDayEmployee(year, month, day);
//    }
//    @GetMapping("/totalRevenuePerMonthEmployee")
//    public Map<String, Double> getTotalRevenuePerMonthEmployee(@RequestParam int year, @RequestParam int month){
//        return fetchAnalyticsForAdmin.getTotalRevenuePerMonthEmployee(year, month);
//    }
//
//    @GetMapping("/totalChargesPerTicket")
//    public Double getTotalChargesPerTicket(@RequestParam String ticketID){
//        return fetchAnalyticsForAdmin.totalCharges(ticketID);
//    }
//    @GetMapping("/totalChargesAllTicket")
//    public Map<String, Double> getTotalChargesAllTicket(){
//        return fetchAnalyticsForAdmin.calculateChargesForAllTickets();
//    }
//
//    @GetMapping("/vendor-collection")
//    public List<VendorCollection> getVendorWiseTotalCollection(
//            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//        return fetchAnalyticsForAdmin.fetchVendorWiseTotalCollection(startDate, endDate);
//    }


}
