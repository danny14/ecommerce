package com.parquesoft.ecommerce.controller;

import com.parquesoft.ecommerce.entity.CategoryEntity;
import com.parquesoft.ecommerce.service.CategoryService;
import com.parquesoft.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/status")
    public String checkStatus() {
        return categoryService.getStatus();
    }

    @GetMapping("/slug")
    public String getSlug(
            @RequestParam String name
    ) {
        return categoryService.generateSlug(name);
    }

    @GetMapping("/discount")
    public String applyCategoryDiscount(
            @RequestParam String name,
            @RequestParam double price
    ) {
        double finalPrice = categoryService.applyCategoryDiscount(name, price);
        return "Precio original: $" + price + " | Precio con el descuento de la categoria " + name + " es: " + finalPrice;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryEntity> getById(@PathVariable Long id){
        return categoryService.getCategoryId(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
