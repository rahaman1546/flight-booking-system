CREATE TABLE IF NOT EXISTS customers (
    customer_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    email VARCHAR(255),
    PRIMARY KEY (customer_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS flights (
    flight_id BIGINT NOT NULL AUTO_INCREMENT,
    airline VARCHAR(255),
    total_seats INT,
    PRIMARY KEY (flight_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS bookings (
    booking_id BIGINT NOT NULL AUTO_INCREMENT,
    flight_id BIGINT,
    customer_id BIGINT,
    price DECIMAL(10, 2),
    booking_date DATE,
    PRIMARY KEY (booking_id),
    CONSTRAINT fk_bookings_flight
        FOREIGN KEY (flight_id) REFERENCES flights (flight_id),
    CONSTRAINT fk_bookings_customer
        FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
) ENGINE=InnoDB;

DROP PROCEDURE IF EXISTS GetRevenueReport;
CREATE PROCEDURE GetRevenueReport(
    IN airline_name VARCHAR(255),
    IN start_date DATE,
    IN end_date DATE,
    OUT total_revenue DECIMAL(10, 2),
    OUT tickets_sold BIGINT,
    OUT avg_ticket_price DECIMAL(10, 2)
)
SELECT
    COALESCE(SUM(b.price), 0),
    COUNT(b.booking_id),
    COALESCE(AVG(b.price), 0)
INTO total_revenue, tickets_sold, avg_ticket_price
FROM bookings b
JOIN flights f ON b.flight_id = f.flight_id
WHERE f.airline = airline_name
  AND b.booking_date BETWEEN start_date AND end_date;
