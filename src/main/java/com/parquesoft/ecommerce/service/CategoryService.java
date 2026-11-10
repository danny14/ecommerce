package com.parquesoft.ecommerce.service;

import com.parquesoft.ecommerce.entity.CategoryEntity;
import com.parquesoft.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<CategoryEntity> getCategoryId(Long id){
        return categoryRepository.findById(id);
    }

    public Optional<CategoryEntity> getCategoryName(String name){
        return categoryRepository.findByName(name);
    }

    public String getStatus() {
        return "El servicio de categorías está activo y funcional.";
    }

    public String generateSlug(String categoryName){

        if (categoryName == null) {
            throw new IllegalArgumentException("El nombre de la categoría debe tener al menos 3 caracteres.");
        }

        String trimmedCategoryName = categoryName.trim();

        if (trimmedCategoryName.length() < 3 || trimmedCategoryName.isBlank()) {
            throw new IllegalArgumentException("El nombre de la categoría debe tener al menos 3 caracteres.");
        }


        return trimmedCategoryName.replace(" ", "-").toLowerCase();
    }

    public double applyCategoryDiscount(String categoryName, double basePrice) {
        double finalPrice;
        if (basePrice <= 0 ) {
            throw new IllegalArgumentException("El precio base debe ser mayor a cero.");
        }
        if (categoryName == null || categoryName.isBlank()) {
            throw new IllegalArgumentException("La categoria debe llenarse");
        }

        if (categoryName.equalsIgnoreCase("LIQUIDACION")){
            finalPrice = basePrice - (basePrice * 0.30);
        } else if (categoryName.equalsIgnoreCase("TECNOLOGIA")) {
            finalPrice = basePrice - (basePrice * 0.10);
        }else {
            finalPrice = basePrice;
        }

        return finalPrice;
    }
}
