package se.jensen.felicia.nimbus.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String text;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Post() {
    }

    public static void add(Post post) {
    }

    public static Post get(long index) {
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
