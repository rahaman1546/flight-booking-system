package com.abdul.flightbooking.service;

import com.abdul.flightbooking.dto.CreateBookingRequest;
import com.abdul.flightbooking.dto.RevenueReport;
import com.abdul.flightbooking.entity.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    List<Booking> getAllBookings();
    Booking getBookingById(Long id);
    List<Booking> getBookingsByCustomer(Long customerId);
    RevenueReport getRevenueReport(String airline, LocalDate startDate, LocalDate endDate);
    Booking createBooking(CreateBookingRequest request);
    void deleteBooking(Long id);
}
