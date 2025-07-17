package com.geopoints.ms_auth.utils.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @Email(message = "El correo electrónico debe ser válido")
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    private String email;
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
}
