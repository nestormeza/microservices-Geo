package com.geopoints.ms_auth.utils.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdateProfile {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;
    @NotBlank(message = "El apellido no puede estar vacío")
    private String lastname;
    @Email(message = "El correo electrónico debe ser válido")
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    private String email;
    @NotBlank(message = "El usuario no puede estar vacío")
    private String username;
}
