package com.abdul.flightbooking.service;

import com.abdul.flightbooking.dto.CreateCustomerRequest;
import com.abdul.flightbooking.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long id);
    Customer createCustomer(CreateCustomerRequest request);
    Customer updateCustomer(Long id, CreateCustomerRequest request);
    void deleteCustomer(Long id);
}
