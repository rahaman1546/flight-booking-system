package com.abdul.flightbooking.service;

import com.abdul.flightbooking.dto.CreateFlightRequest;
import com.abdul.flightbooking.entity.Flight;
import com.abdul.flightbooking.repository.SeatAvailabilityView;

import java.util.List;

public interface FlightService {
    List<Flight> getAllFlights();
    Flight getFlightById(Long id);
    Flight createFlight(CreateFlightRequest request);
    Flight updateFlight(Long id, CreateFlightRequest request);
    void deleteFlight(Long id);
    SeatAvailabilityView getSeatAvailability(Long id);
}
