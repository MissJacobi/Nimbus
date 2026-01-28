package se.jensen.felicia.nimbus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.felicia.nimbus.model.Post;

public interface PostRepository  extends JpaRepository<Post, Long> {
}
