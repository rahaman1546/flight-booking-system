package com.abdul.flightbooking.repository;

public interface SeatAvailabilityView {
    Long getFlightId();
    Integer getTotalSeats();
    Long getBookedSeats();
    Long getAvailableSeats();
}
