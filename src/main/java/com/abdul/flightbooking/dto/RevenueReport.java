package com.abdul.flightbooking.dto;

import java.math.BigDecimal;

public class RevenueReport {
    private BigDecimal totalRevenue;
    private Long ticketsSold;
    private BigDecimal avgTicketPrice;

    public RevenueReport() {
    }

    public RevenueReport(BigDecimal totalRevenue, Long ticketsSold, BigDecimal avgTicketPrice) {
        this.totalRevenue = totalRevenue;
        this.ticketsSold = ticketsSold;
        this.avgTicketPrice = avgTicketPrice;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Long getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(Long ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public BigDecimal getAvgTicketPrice() {
        return avgTicketPrice;
    }

    public void setAvgTicketPrice(BigDecimal avgTicketPrice) {
        this.avgTicketPrice = avgTicketPrice;
    }
}
