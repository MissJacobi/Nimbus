package se.jensen.felicia.nimbus.service;

import org.springframework.stereotype.Service;
import se.jensen.felicia.nimbus.dto.PostRequestDTO;
import se.jensen.felicia.nimbus.dto.PostResponseDTO;
import se.jensen.felicia.nimbus.model.Post;
import se.jensen.felicia.nimbus.model.User;
import se.jensen.felicia.nimbus.repository.PostRepository;
import se.jensen.felicia.nimbus.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
@Service
public class PostService {
        private final UserRepository userRepository;
        private final PostRepository postRepository;

    public PostService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

        public PostResponseDTO createPost(Long userId, PostRequestDTO postDto){
        Post post = new Post();
        post.setText(postDto.text());
        post.setCreatedAt(LocalDateTime.now());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + userId));
        post.setUser(user);

        Post savedPost = postRepository.save(post);
        return new PostResponseDTO(savedPost.getId(), savedPost.getText(), savedPost.getCreatedAt());
    }
}
