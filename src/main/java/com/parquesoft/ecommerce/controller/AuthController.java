package com.parquesoft.ecommerce.controller;

import com.parquesoft.ecommerce.dto.AuthResponse;
import com.parquesoft.ecommerce.dto.LoginRequest;
import com.parquesoft.ecommerce.dto.RegisterRequest;
import com.parquesoft.ecommerce.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Access;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name="Autenticacion", description = "Endpoints publicos para el registro y el inicio de sesion con JWT")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar Sesion",description = "Valida credenciales contra la base de datos y retorna el JWT ")
    @ApiResponses({
                    @ApiResponse(responseCode = "200", description = "Autentificacion Exitosa"),
                    @ApiResponse(responseCode = "400", description = "Credenciales invalidas")
    })
    public ResponseEntity<AuthResponse> login(
            @Parameter(description = "Email", required = true, example = "test@parquesoft.com")
            @PathVariable String email,
            @Parameter(description = "Password", required = true, example = "*********")
            @PathVariable String password,
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Registrar un nuevo usuario",description = "Crear un nuevo usuario con contraseña cifrada en BCrypt y genera su token")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "El correo ya existe o los datos son invalidos")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
