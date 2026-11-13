package com.parquesoft.ecommerce.service;

import com.parquesoft.ecommerce.entity.CategoryEntity;
import com.parquesoft.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findByIsActiveTrue();
    }

    @Transactional(readOnly = true)
    public Optional<CategoryEntity> getCategoryId(Long id) {
        return categoryRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<CategoryEntity> getCategoryName(String name) {
        return categoryRepository.findByName(name);
    }

    @Transactional
    public CategoryEntity createCategory(CategoryEntity category) {
        if (category.getName() == null || category.getName().trim().isBlank()) {
            throw new IllegalArgumentException("El nombre de la categoria es obligatorio.");
        }
        if (categoryRepository.existsByName(category.getName().trim())) {
            throw new IllegalArgumentException("Ya existe una categoria con ese nombre.");
        }
        category.setName(category.getName().trim());
        category.setActive(true);
        return categoryRepository.save(category);
    }

    @Transactional
    public CategoryEntity updateCategory(Long id, CategoryEntity details) {
        CategoryEntity existing = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria no encontrada con ID: " + id));

        if (details.getName() != null && !details.getName().trim().isBlank()) {
            existing.setName(details.getName().trim());
        }
        existing.setDescription(details.getDescription());
        return categoryRepository.save(existing);
    }

    @Transactional
    public void deleteCategory(Long id) {
        CategoryEntity existing = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria no encontrada con ID: " + id));
        existing.setActive(false);
        categoryRepository.save(existing);
    }

    public String getStatus() {
        return "El servicio de categorias esta activo y funcional.";
    }

    public String generateSlug(String categoryName) {
        if (categoryName == null) {
            throw new IllegalArgumentException("El nombre de la categoria debe tener al menos 3 caracteres.");
        }

        String trimmedCategoryName = categoryName.trim();

        if (trimmedCategoryName.length() < 3 || trimmedCategoryName.isBlank()) {
            throw new IllegalArgumentException("El nombre de la categoria debe tener al menos 3 caracteres.");
        }

        return trimmedCategoryName.replace(" ", "-").toLowerCase();
    }

    public double applyCategoryDiscount(String categoryName, double basePrice) {
        double finalPrice;
        if (basePrice <= 0) {
            throw new IllegalArgumentException("El precio base debe ser mayor a cero.");
        }
        if (categoryName == null || categoryName.isBlank()) {
            throw new IllegalArgumentException("La categoria debe llenarse");
        }

        if (categoryName.equalsIgnoreCase("LIQUIDACION")) {
            finalPrice = basePrice - (basePrice * 0.30);
        } else if (categoryName.equalsIgnoreCase("TECNOLOGIA")) {
            finalPrice = basePrice - (basePrice * 0.10);
        } else {
            finalPrice = basePrice;
        }

        return finalPrice;
    }
}
