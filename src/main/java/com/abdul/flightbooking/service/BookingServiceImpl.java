package com.abdul.flightbooking.service;

import com.abdul.flightbooking.dto.CreateBookingRequest;
import com.abdul.flightbooking.dto.RevenueReport;
import com.abdul.flightbooking.entity.Booking;
import com.abdul.flightbooking.entity.Customer;
import com.abdul.flightbooking.entity.Flight;
import com.abdul.flightbooking.repository.BookingRepository;
import com.abdul.flightbooking.repository.CustomerRepository;
import com.abdul.flightbooking.repository.FlightRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final CustomerRepository customerRepository;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              FlightRepository flightRepository,
                              CustomerRepository customerRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
    }

    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerCustomerId(customerId);
    }

    @Override
    public RevenueReport getRevenueReport(String airline, LocalDate startDate, LocalDate endDate) {
        return bookingRepository.getRevenueReport(airline, startDate, endDate);
    }

    @Override
    public Booking createBooking(CreateBookingRequest request) {
        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setCustomer(customer);
        booking.setPrice(request.getPrice());
        booking.setBookingDate(request.getBookingDate());
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
        bookingRepository.delete(booking);
    }
}
