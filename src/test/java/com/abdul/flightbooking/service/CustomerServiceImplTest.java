package com.abdul.flightbooking.service;

import com.abdul.flightbooking.dto.CreateCustomerRequest;
import com.abdul.flightbooking.entity.Customer;
import com.abdul.flightbooking.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void getAllCustomers_returnsList() {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setName("Alice");
        customer.setEmail("alice@example.com");

        when(customerRepository.findAll()).thenReturn(List.of(customer));

        List<Customer> result = customerService.getAllCustomers();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getCustomerId());
    }

    @Test
    void getCustomerById_notFound_throws() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> customerService.getCustomerById(99L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void createCustomer_persists() {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setName("Bob");
        request.setEmail("bob@example.com");

        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> {
            Customer toSave = invocation.getArgument(0);
            toSave.setCustomerId(10L);
            return toSave;
        });

        Customer saved = customerService.createCustomer(request);

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(captor.capture());
        assertEquals("Bob", captor.getValue().getName());
        assertEquals("bob@example.com", captor.getValue().getEmail());
        assertEquals(10L, saved.getCustomerId());
    }
}
