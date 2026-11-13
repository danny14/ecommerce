package com.parquesoft.ecommerce.service;

import com.parquesoft.ecommerce.entity.ProductEntity;
import com.parquesoft.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductEntity> getAllActiveProducts() {
        return productRepository.findByIsActiveTrue();
    }

    @Transactional(readOnly = true)
    public Optional<ProductEntity> getBySku(String sku) {
        return productRepository.findBySku(sku);
    }

    @Transactional(readOnly = true)
    public Optional<ProductEntity> getById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<ProductEntity> searchByPriceRange(double min, double max) {
        return productRepository.searchByPriceRange(min, max);
    }

    @Transactional
    public ProductEntity createProduct(ProductEntity product) {
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio del producto debe ser mayor a cero.");
        }
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
        if (productRepository.existsBySku(product.getSku())) {
            throw new IllegalArgumentException("Ya existe un producto con el SKU: " + product.getSku());
        }
        product.setActive(true);
        return productRepository.save(product);
    }

    @Transactional
    public ProductEntity updateProduct(Long id, ProductEntity details) {
        ProductEntity existing = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));

        existing.setName(details.getName());
        existing.setPrice(details.getPrice());
        existing.setStock(details.getStock());
        if (details.getCategory() != null) {
            existing.setCategory(details.getCategory());
        }
        return productRepository.save(existing);
    }

    @Transactional
    public void deleteProduct(Long id) {
        ProductEntity existing = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));
        existing.setActive(false);
        productRepository.save(existing);
    }

    public String getStatusMessage() {
        return "El servicio ProductService esta funcionando bajo el contenedor de Spring Boot.";
    }

    public double calculateDiscount(double price, double percentage) {
        if (price <= 0 || percentage < 0) {
            throw new IllegalArgumentException("El precio y el porcentaje deben ser validos.");
        }
        return price - (price * (percentage / 100));
    }
}
