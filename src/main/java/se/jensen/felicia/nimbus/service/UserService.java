package se.jensen.felicia.nimbus.service;
import org.springframework.stereotype.Service;
import se.jensen.felicia.nimbus.dto.*;
import se.jensen.felicia.nimbus.mapper.UserMapper;
import se.jensen.felicia.nimbus.model.User;
import se.jensen.felicia.nimbus.repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(),
                        user.getPassword(), user.getRole(), user.getDisplayName(), user.getBio(), user.getProfileImagePath())).toList();
    }

    public UserResponseDTO addUser(UserRequestDTO userDTO) {
        User user = userMapper.fromDto(userDTO);

        boolean exist = userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (exist) {
            throw new IllegalArgumentException("A user with this username or email dose already exist.");
        }
        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    public UserResponseDTO updateUser(UserRequestDTO dto, Long id) {
        Optional<User> existing = userRepository.findById(id);
        if (existing.isPresent()) {
            User user = existing.get();
            user.setBio(dto.bio());
            user.setDisplayName(dto.displayName());
            user.setEmail(dto.email());
            user.setRole(dto.role());
            user.setUsername(dto.username());
            user.setPassword(dto.password());
            user.setProfileImagePath(dto.profileImagePath());
            User updated = userRepository.save(user);
            return userMapper.toDto(updated);
        } else {
            throw new NoSuchElementException("Ingen user i databasen med id: " + id);
        }
    }

    public UserWithPostsResponseDTO getUserWithPosts(Long id) {
        User user = userRepository.findUserWithPosts(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        List<PostResponseDTO> post = user.getPosts()
                .stream()
                .map(p -> new PostResponseDTO(
                        p.getId(),
                        p.getText(),
                        p.getCreatedAt()
                ))
                .toList();
        UserResponseDTO dto = new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getDisplayName(),
                user.getBio(),
                user.getProfileImagePath());

        UserWithPostsResponseDTO dtoToReturn =
                new UserWithPostsResponseDTO(dto, post);
        return dtoToReturn;

    }

    public UserResponseDTO getUserWithID(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        return userMapper.toDto(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

}
