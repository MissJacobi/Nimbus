package se.jensen.felicia.nimbus.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.felicia.nimbus.dto.*;
import se.jensen.felicia.nimbus.model.User;
import se.jensen.felicia.nimbus.service.PostService;
import se.jensen.felicia.nimbus.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final PostService postService;
    private List<User> users = new ArrayList<>();
    private final UserService userService;

    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUser(){

        List<UserResponseDTO> usersFromDB = userService.getAllUsers();
        return ResponseEntity.ok(usersFromDB);
    }

    @PostMapping("/{userId}/post")
    public ResponseEntity<PostResponseDTO> createPostForUser(@PathVariable Long userId, @Valid @RequestBody PostRequestDTO request){

        PostResponseDTO postRequestDTO = postService.createPost(userId,request );
        return ResponseEntity.status(HttpStatus.CREATED).body(postRequestDTO);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> addUser(@RequestBody UserRequestDTO dto){

        UserResponseDTO response = userService.addUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /* @GetMapping("/{index}")
    public ResponseEntity<UserResponseDTO> getUserByIndex(@PathVariable Long id,@RequestBody UserRequestDTO dto){
        List<UserResponseDTO> userFromDB = userService.getAllUsers();
        return ResponseEntity.ok(userFromDB);
    }*/

    @PutMapping
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id,@RequestBody UserRequestDTO dto ){
        UserResponseDTO user = userService.updateUser(dto, id);
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/{index}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        if(id < 0 || id >= users.size()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/with-posts")
    public ResponseEntity<UserWithPostsResponseDTO> getUserWithPosts(@PathVariable Long id){
        UserWithPostsResponseDTO response = userService.getUserWithPosts(id);
        return ResponseEntity.ok(response);
    }

}
