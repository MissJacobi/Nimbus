package se.jensen.felicia.nimbus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostRequestDTO(
        @NotBlank(message = "Texten får inte vara tom.")
        @Size(min = 3, max = 200)
        String text
) {
}
