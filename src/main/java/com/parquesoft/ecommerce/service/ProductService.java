package com.parquesoft.ecommerce.service;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

    public String getStatusMessage() {
        return "El servicio ProductService está funcionando bajo el contenedor de Spring Boot.";
    }

    public double calculateDiscount(double price, double percentage) {
        if (price <= 0 || percentage < 0) {
            throw new IllegalArgumentException("El precio y el porcentaje deben ser válidos.");
        }
        return price - (price * (percentage / 100));
    }
}
