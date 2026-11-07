package com.parquesoft.ecommerce.controller;

import com.parquesoft.ecommerce.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/status")
    public String checkStatus() {
        return productService.getStatusMessage();
    }


    // http://localhost:8081/api/products/discount?price=100&percentage=15
    @GetMapping("/discount")
    public String calculateDiscount(
            @RequestParam double price,
            @RequestParam double percentage
    ) {
        double finalPrice = productService.calculateDiscount(price, percentage);
        return "Precio original: $" + price + " | Precio con " + percentage + "% de descuento: $" + finalPrice;
    }
}
