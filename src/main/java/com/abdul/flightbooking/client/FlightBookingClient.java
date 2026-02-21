package com.abdul.flightbooking.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class FlightBookingClient {

    public static void main(String[] args) {
        String baseUrl = args.length > 0 ? args[0] : "http://localhost:8080";
        String flightId = args.length > 1 ? args[1] : "1";
        String customerId = args.length > 2 ? args[2] : "1";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest flightRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/flights/" + flightId))
                .GET()
                .build();

        HttpRequest bookingsRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/bookings/customer/" + customerId))
                .GET()
                .build();

        CompletableFuture<String> flightFuture = client.sendAsync(flightRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() >= 400) {
                        throw new RuntimeException("Flight service error: " + response.statusCode() + " " + response.body());
                    }
                    return response.body();
                });

        CompletableFuture<String> bookingsFuture = client.sendAsync(bookingsRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() >= 400) {
                        throw new RuntimeException("Booking service error: " + response.statusCode() + " " + response.body());
                    }
                    return response.body();
                });

        CompletableFuture<String> aggregated = flightFuture.thenCombine(bookingsFuture, (flightJson, bookingsJson) -> {
            return "{\n" +
                    "  \"flight\": " + flightJson + ",\n" +
                    "  \"bookings\": " + bookingsJson + "\n" +
                    "}";
        });

        System.out.println(aggregated.join());
    }
}
