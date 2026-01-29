package se.jensen.felicia.nimbus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "Fältet får inte lämnas tom.")
        String username,
        @NotBlank(message = "Fältet får inte lämnas tomt.")
        String email,
        @NotBlank(message = "Fältet får inte lämnas tomt.")
        @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Endast bokstäver och siffror tillåtna.")
        @Size(min = 8, max = 20)
        String password,
        @NotBlank(message = "Fältet får inte lämnas tomt.")
        String role,
        @NotBlank(message = "Fältet får inte lämnas tomt.")
        String displayName,
        @NotBlank(message = "Fältet får inte lämnas tomt.")
        String bio,
        String profileImagePath
) {
}
