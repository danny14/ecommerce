package com.parquesoft.ecommerce.controller;

import com.parquesoft.ecommerce.entity.ProductEntity;
import com.parquesoft.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "2. Productos", description = "Endpoints para catalogo y administracion de productos")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Listar catalogo de productos activos", description = "Acceso publico utilizado en la tienda")
    @ApiResponse(responseCode = "200", description = "Catalogo obtenido exitosamente")
    public ResponseEntity<List<ProductEntity>> getAllActiveProducts() {
        return ResponseEntity.ok(productService.getAllActiveProducts());
    }

    @GetMapping("/sku/{sku}")
    @Operation(summary = "Buscar producto por codigo SKU")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductEntity> getBySku(@PathVariable String sku) {
        return productService.getBySku(sku)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar producto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductEntity> getById(@PathVariable Long id) {
        return productService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar productos por rango de precio (JPQL)", description = "Ejemplo: /api/products/search?min=50&max=300")
    public ResponseEntity<List<ProductEntity>> searchByPrice(
            @RequestParam(defaultValue = "0") double min,
            @RequestParam(defaultValue = "999999") double max
    ) {
        return ResponseEntity.ok(productService.searchByPriceRange(min, max));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo producto", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o SKU duplicado")
    })
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto por ID", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable Long id, @RequestBody ProductEntity product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desactivar producto (Borrado logico)", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    @Operation(summary = "Verificar estado del servicio de productos")
    public String checkStatus() {
        return productService.getStatusMessage();
    }

    @GetMapping("/discount")
    @Operation(summary = "Calcular descuento de producto")
    public String calculateDiscount(@RequestParam double price, @RequestParam double percentage) {
        double finalPrice = productService.calculateDiscount(price, percentage);
        return "Precio original: $" + price + " | Precio con " + percentage + "% de descuento: $" + finalPrice;
    }
}
