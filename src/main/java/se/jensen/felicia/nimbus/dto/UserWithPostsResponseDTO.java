package se.jensen.felicia.nimbus.dto;

import java.util.List;

public record UserWithPostsResponseDTO(
        UserResponseDTO user,
        List<PostResponseDTO> posts
) {
}
