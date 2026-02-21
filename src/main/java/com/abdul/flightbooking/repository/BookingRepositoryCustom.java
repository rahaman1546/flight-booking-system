package com.abdul.flightbooking.repository;

import com.abdul.flightbooking.dto.RevenueReport;

import java.time.LocalDate;

public interface BookingRepositoryCustom {
    RevenueReport getRevenueReport(String airlineName, LocalDate startDate, LocalDate endDate);
}
