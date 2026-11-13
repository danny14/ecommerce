package com.parquesoft.ecommerce.repository;

import com.parquesoft.ecommerce.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findBySku(String sku);

    boolean existsBySku(String sku);

    List<ProductEntity> findByIsActiveTrue();

    // Consulta JPQL: Busqueda por rango de precios
    @Query("SELECT p FROM ProductEntity p WHERE p.price BETWEEN :min AND :max AND p.isActive = true ORDER BY p.price ASC")
    List<ProductEntity> searchByPriceRange(@Param("min") double min, @Param("max") double max);

}
