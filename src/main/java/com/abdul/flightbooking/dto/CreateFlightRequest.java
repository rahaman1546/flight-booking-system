package com.abdul.flightbooking.dto;

public class CreateFlightRequest {
    private String airline;
    private Integer totalSeats;

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    @Override
    public String toString() {
        return "CreateFlightRequest{" +
                "airline='" + airline + '\'' +
                ", totalSeats=" + totalSeats +
                '}';
    }
}
