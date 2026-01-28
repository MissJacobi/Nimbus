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

    private List<Post> post = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> addPost(@Valid @RequestBody PostRequestDTO dto){
        LocalDateTime now = LocalDateTime.now();
        Post post = new Post();
        post.setId(0L);
        post.setText(dto.text());
        post.setCreatedAt(now);

        Post.add(post);
        PostResponseDTO response = new PostResponseDTO(post.getId(),post.getText(),post.getCreatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{index}")
    public ResponseEntity<PostResponseDTO> getPostByIndex(@PathVariable long index){
        if (index < 0 || index >= post.size()){
            return ResponseEntity.notFound().build();
        }
        Post post =  Post.get(index);
        PostResponseDTO response = new PostResponseDTO(0L,post.getText(),post.getCreatedAt());
        return ResponseEntity.ok(response);
    }

    @PutMapping("{index}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable long index,
                                                      @Valid @RequestBody PostRequestDTO dto){
        if (index < 0 || index >= post.size()){
            return ResponseEntity.notFound().build();
        }
        LocalDateTime now = LocalDateTime.now();
        Post post = Post.get(index);
        post.setText(dto.text());
        post.setCreatedAt(now);

        PostResponseDTO response = new PostResponseDTO(index, post.getText(), post.getCreatedAt());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{index}")
    public ResponseEntity<Void> deletePost(@PathVariable long index){
        if (index < 0 || index >= post.size()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
