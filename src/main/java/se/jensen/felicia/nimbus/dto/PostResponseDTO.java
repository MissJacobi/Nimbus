package se.jensen.felicia.nimbus.dto;

import java.time.LocalDateTime;

public record PostResponseDTO(
        Long id,
        String text,
        LocalDateTime createdAt
) {
}
