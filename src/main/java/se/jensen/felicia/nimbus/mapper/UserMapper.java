package se.jensen.felicia.nimbus.mapper;

import org.springframework.stereotype.Component;
import se.jensen.felicia.nimbus.dto.UserRequestDTO;
import se.jensen.felicia.nimbus.dto.UserResponseDTO;
import se.jensen.felicia.nimbus.model.User;

@Component
public class UserMapper {

    public static User fromDto(UserRequestDTO dto){
        User user = new User();
        setUserValues(user,dto);
        return user;
    }

    public static UserResponseDTO toDto(User user){
        UserResponseDTO dto = new UserResponseDTO(user.getId(),user.getUsername(),user.getEmail(),user.getPassword(),user.getRole(),
                user.getDisplayName(),user.getBio(),user.getProfileImagePath());
        return dto;
    }

    private static void setUserValues(User user,  UserRequestDTO dto){
        user.setBio(dto.bio());
        user.setDisplayName(dto.displayName());
        user.setEmail(dto.email());
        user.setRole(dto.role());
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setProfileImagePath(dto.profileImagePath());
    }
}