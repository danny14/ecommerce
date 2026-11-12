package com.parquesoft.ecommerce.service;

import com.parquesoft.ecommerce.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;


    @Test
    @DisplayName("generateSlug: Debe generar un slug en minusculas y separado por guiones")
    public void testGenerateSlug_Success(){
        String input = "Ropa Deportiva Hombre";

        String slug = categoryService.generateSlug(input);

        assertEquals("ropa-deportiva-hombre", slug);
    }


    @Test
    @DisplayName("generateSlug: Debe lanzar un IllegalArgumentException si el nombre es nulo o menor a 3 caracteres")
    public void testGenerateSlug_ThrowsExcepcion_WhenInvalid(){

        assertThrows(IllegalArgumentException.class, () -> categoryService.generateSlug(null));

        assertThrows(IllegalArgumentException.class, () -> categoryService.generateSlug("  ab  "));
    }


    @Test
    @DisplayName("applyCategoryDiscount: Debe aplicar el 30% para LIQUIDACION y 10% para TECNOLOGIA")
    public void applyCategoryDiscount_Success(){
        double basePrice = 100.0;

        double priceLiquidacion = categoryService.applyCategoryDiscount("LIQUIDACION", basePrice);
        double priceTecnologia = categoryService.applyCategoryDiscount("TECNOLOGIA", basePrice);
        double priceNormal = categoryService.applyCategoryDiscount("ROPA", basePrice);

        assertEquals(70.0, priceLiquidacion);
        assertEquals(90.0, priceTecnologia);
        assertEquals(100.0,priceNormal);

    }




}
