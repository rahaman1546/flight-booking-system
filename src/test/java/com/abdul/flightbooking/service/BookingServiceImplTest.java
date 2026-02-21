package com.abdul.flightbooking.service;

import com.abdul.flightbooking.dto.CreateBookingRequest;
import com.abdul.flightbooking.dto.RevenueReport;
import com.abdul.flightbooking.entity.Booking;
import com.abdul.flightbooking.entity.Customer;
import com.abdul.flightbooking.entity.Flight;
import com.abdul.flightbooking.repository.BookingRepository;
import com.abdul.flightbooking.repository.CustomerRepository;
import com.abdul.flightbooking.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    void createBooking_persistsWithReferences() {
        Flight flight = new Flight();
        flight.setFlightId(1L);
        flight.setAirline("Delta");
        flight.setTotalSeats(180);

        Customer customer = new Customer();
        customer.setCustomerId(2L);
        customer.setName("Alice");
        customer.setEmail("alice@example.com");

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(customerRepository.findById(2L)).thenReturn(Optional.of(customer));

        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking toSave = invocation.getArgument(0);
            toSave.setBookingId(99L);
            return toSave;
        });

        CreateBookingRequest request = new CreateBookingRequest();
        request.setFlightId(1L);
        request.setCustomerId(2L);
        request.setPrice(new BigDecimal("199.99"));
        request.setBookingDate(LocalDate.of(2026, 2, 20));

        Booking saved = bookingService.createBooking(request);

        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);
        verify(bookingRepository).save(captor.capture());
        assertEquals(1L, captor.getValue().getFlight().getFlightId());
        assertEquals(2L, captor.getValue().getCustomer().getCustomerId());
        assertEquals(new BigDecimal("199.99"), captor.getValue().getPrice());
        assertEquals(LocalDate.of(2026, 2, 20), captor.getValue().getBookingDate());
        assertEquals(99L, saved.getBookingId());
    }

    @Test
    void getRevenueReport_returnsRepositoryResult() {
        RevenueReport report = new RevenueReport(
                new BigDecimal("400.00"),
                4L,
                new BigDecimal("100.00")
        );

        when(bookingRepository.getRevenueReport(
                "Delta",
                LocalDate.of(2026, 2, 1),
                LocalDate.of(2026, 2, 28))
        ).thenReturn(report);

        RevenueReport result = bookingService.getRevenueReport(
                "Delta",
                LocalDate.of(2026, 2, 1),
                LocalDate.of(2026, 2, 28)
        );

        assertEquals(report.getTotalRevenue(), result.getTotalRevenue());
        assertEquals(report.getTicketsSold(), result.getTicketsSold());
        assertEquals(report.getAvgTicketPrice(), result.getAvgTicketPrice());
    }
}
