package com.parquesoft.ecommerce.service;

import com.parquesoft.ecommerce.entity.CustomerEntity;
import com.parquesoft.ecommerce.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public List<CustomerEntity> getAllCustomer() {
        return customerRepository.findByIsActiveTrue();
    }

    @Transactional(readOnly = true)
    public Optional<CustomerEntity> getByEmail(String email) {
        return customerRepository.findByEmail(email.trim().toLowerCase());
    }


    @Transactional(readOnly = true)
    public List<CustomerEntity> getVipCustomers(int minPoints) {
        return customerRepository.findVipCustomers(minPoints);
    }

    @Transactional()
    public CustomerEntity createCustomer(CustomerEntity customer) {
        String cleanEmail = customer.getEmail().trim().toLowerCase();
        if(customerRepository.existsByEmail(cleanEmail)) {
            throw new IllegalArgumentException("Ya existe un email: " + cleanEmail);
        }

        customer.setEmail(cleanEmail);
        customer.setActive(true);

        return customerRepository.save(customer);
    }

    @Transactional()
    public CustomerEntity updateCustomer(Long id, CustomerEntity details) {
        CustomerEntity existing = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));

        existing.setFullName(details.getFullName());
        existing.setLoyalityPoints(details.getLoyalityPoints());

        return customerRepository.save(existing);
    }

    @Transactional()
    public void deleteCustomer(Long id) {
        CustomerEntity existing = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));

        existing.setActive(false);
        customerRepository.save(existing);
    }


}
