package se.jensen.felicia.nimbus.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(
        @NotBlank(message = "Fältet får inte lämnas tomt.")
        String username,
        @NotBlank(message = "Fältet får inte lämnas tomt.")
        String password
) {
}
