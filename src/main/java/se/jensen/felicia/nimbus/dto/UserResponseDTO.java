package se.jensen.felicia.nimbus.dto;

public record UserResponseDTO(
        Long id,
        String username,
        String email,
        String password,
        String role,
        String displayName,
        String bio,
        String profileImagePath
) {
}
