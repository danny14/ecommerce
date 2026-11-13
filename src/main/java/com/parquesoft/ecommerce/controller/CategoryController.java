package com.parquesoft.ecommerce.controller;

import com.parquesoft.ecommerce.entity.CategoryEntity;
import com.parquesoft.ecommerce.service.CategoryService;
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
@RequestMapping("/api/categories")
@Tag(name = "1. Categorias", description = "Endpoints para gestion del catalogo de categorias")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(summary = "Listar categorias activas", description = "Acceso publico para cargar categorias en la tienda")
    @ApiResponse(responseCode = "200", description = "Lista obtenida con exito")
    public ResponseEntity<List<CategoryEntity>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar categoria por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada")
    })
    public ResponseEntity<CategoryEntity> getById(@PathVariable Long id) {
        return categoryService.getCategoryId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nueva categoria", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoria creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o nombre duplicado")
    })
    public ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryEntity category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(category));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categoria por ID", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<CategoryEntity> updateCategory(@PathVariable Long id, @RequestBody CategoryEntity category) {
        return ResponseEntity.ok(categoryService.updateCategory(id, category));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desactivar categoria (Borrado logico)", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    @Operation(summary = "Verificar estado del servicio")
    public String checkStatus() {
        return categoryService.getStatus();
    }

    @GetMapping("/slug")
    @Operation(summary = "Generar slug de categoria a partir del nombre")
    public String getSlug(@RequestParam String name) {
        return categoryService.generateSlug(name);
    }

    @GetMapping("/discount")
    @Operation(summary = "Calcular descuento aplicable por categoria")
    public String applyCategoryDiscount(@RequestParam String name, @RequestParam double price) {
        double finalPrice = categoryService.applyCategoryDiscount(name, price);
        return "Precio original: $" + price + " | Precio con el descuento de la categoria " + name + " es: " + finalPrice;
    }
}
