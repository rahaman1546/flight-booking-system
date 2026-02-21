package com.abdul.flightbooking.service;

import com.abdul.flightbooking.dto.CreateFlightRequest;
import com.abdul.flightbooking.entity.Flight;
import com.abdul.flightbooking.repository.FlightRepository;
import com.abdul.flightbooking.repository.SeatAvailabilityView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightServiceImplTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightServiceImpl flightService;

    @Test
    void createFlight_persists() {
        CreateFlightRequest request = new CreateFlightRequest();
        request.setAirline("Delta");
        request.setTotalSeats(180);

        when(flightRepository.save(any(Flight.class))).thenAnswer(invocation -> {
            Flight toSave = invocation.getArgument(0);
            toSave.setFlightId(5L);
            return toSave;
        });

        Flight saved = flightService.createFlight(request);

        ArgumentCaptor<Flight> captor = ArgumentCaptor.forClass(Flight.class);
        verify(flightRepository).save(captor.capture());
        assertEquals("Delta", captor.getValue().getAirline());
        assertEquals(180, captor.getValue().getTotalSeats());
        assertEquals(5L, saved.getFlightId());
    }

    @Test
    void getSeatAvailability_notFound_throws() {
        when(flightRepository.findSeatAvailability(42L)).thenReturn(null);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> flightService.getSeatAvailability(42L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void getSeatAvailability_returnsView() {
        SeatAvailabilityView view = new SeatAvailabilityView() {
            @Override
            public Long getFlightId() {
                return 1L;
            }

            @Override
            public Integer getTotalSeats() {
                return 180;
            }

            @Override
            public Long getBookedSeats() {
                return 5L;
            }

            @Override
            public Long getAvailableSeats() {
                return 175L;
            }
        };

        when(flightRepository.findSeatAvailability(1L)).thenReturn(view);

        SeatAvailabilityView result = flightService.getSeatAvailability(1L);

        assertEquals(1L, result.getFlightId());
        assertEquals(175L, result.getAvailableSeats());
    }
}
