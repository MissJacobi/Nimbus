package se.jensen.felicia.nimbus.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.felicia.nimbus.dto.PostRequestDTO;
import se.jensen.felicia.nimbus.dto.PostResponseDTO;
import se.jensen.felicia.nimbus.model.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private List<Post> posts = new ArrayList<>();
    private long nextId = 1;

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        return ResponseEntity.ok(posts);
    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> addPost(@Valid @RequestBody PostRequestDTO dto){
        LocalDateTime now = LocalDateTime.now();
        Post post = new Post();
        post.setText(dto.text());
        post.setCreatedAt(now);

        post.setId(nextId++);
        posts.add(post);
        PostResponseDTO response = new PostResponseDTO(post.getId(),post.getText(),post.getCreatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostByIndex(@PathVariable long id){
        if (id < 0 || id >= posts.size()){
            return ResponseEntity.notFound().build();
        }
        Post post = posts.get((int) id);
        PostResponseDTO response = new PostResponseDTO(post.getId(),post.getText(),post.getCreatedAt());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable long id,
                                                      @Valid @RequestBody PostRequestDTO dto){
        if (id < 0 || id >= posts.size()){
            return ResponseEntity.notFound().build();
        }
        LocalDateTime now = LocalDateTime.now();
        Post post = posts.get((int) id);
        post.setText(dto.text());
        post.setCreatedAt(now);

        PostResponseDTO response = new PostResponseDTO(post.getId(), post.getText(), post.getCreatedAt());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id){
        if (id < 0 || id >= posts.size()) {
            return ResponseEntity.notFound().build();
        }
        posts.remove((int) id);
        return ResponseEntity.noContent().build();
    }

}
