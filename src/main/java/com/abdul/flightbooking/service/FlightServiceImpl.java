package com.abdul.flightbooking.service;

import com.abdul.flightbooking.dto.CreateFlightRequest;
import com.abdul.flightbooking.entity.Flight;
import com.abdul.flightbooking.repository.FlightRepository;
import com.abdul.flightbooking.repository.SeatAvailabilityView;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Override
    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));
    }

    @Override
    public Flight createFlight(CreateFlightRequest request) {
        Flight flight = new Flight();
        flight.setAirline(request.getAirline());
        flight.setTotalSeats(request.getTotalSeats());
        return flightRepository.save(flight);
    }

    @Override
    public Flight updateFlight(Long id, CreateFlightRequest request) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));
        flight.setAirline(request.getAirline());
        flight.setTotalSeats(request.getTotalSeats());
        return flightRepository.save(flight);
    }

    @Override
    public void deleteFlight(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));
        flightRepository.delete(flight);
    }

    @Override
    public SeatAvailabilityView getSeatAvailability(Long id) {
        SeatAvailabilityView availability = flightRepository.findSeatAvailability(id);
        if (availability == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found");
        }
        return availability;
    }
}
