package com.parquesoft.ecommerce.controller;

import com.parquesoft.ecommerce.entity.CustomerEntity;
import com.parquesoft.ecommerce.service.CustomerService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/api/customers")

public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerEntity>> getAll(){
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

}
