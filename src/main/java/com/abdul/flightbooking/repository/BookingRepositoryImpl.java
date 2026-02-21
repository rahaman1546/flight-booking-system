package com.abdul.flightbooking.repository;

import com.abdul.flightbooking.dto.RevenueReport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public class BookingRepositoryImpl implements BookingRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public RevenueReport getRevenueReport(String airlineName, LocalDate startDate, LocalDate endDate) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("GetRevenueReport");

        query.registerStoredProcedureParameter("airline_name", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("start_date", java.sql.Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("end_date", java.sql.Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("total_revenue", BigDecimal.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("tickets_sold", Long.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("avg_ticket_price", BigDecimal.class, ParameterMode.OUT);

        query.setParameter("airline_name", airlineName);
        query.setParameter("start_date", java.sql.Date.valueOf(startDate));
        query.setParameter("end_date", java.sql.Date.valueOf(endDate));

        query.execute();

        BigDecimal totalRevenue = (BigDecimal) query.getOutputParameterValue("total_revenue");
        Number ticketsSoldValue = (Number) query.getOutputParameterValue("tickets_sold");
        Long ticketsSold = ticketsSoldValue != null ? ticketsSoldValue.longValue() : null;
        BigDecimal avgTicketPrice = (BigDecimal) query.getOutputParameterValue("avg_ticket_price");

        return new RevenueReport(totalRevenue, ticketsSold, avgTicketPrice);
    }
}
