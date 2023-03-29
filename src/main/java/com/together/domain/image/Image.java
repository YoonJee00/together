package com.together.domain.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.together.domain.comment.Comment;
import com.together.domain.likes.Likes;
import com.together.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String caption;
    private String postImageUrl;

    @JsonIgnoreProperties({"images"})
    @JoinColumn(name="userId")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    private LocalDateTime createDate;

    @Transient
    private boolean likeState;

    @Transient
    private int likeCount;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image")
    private List<Likes> likes;

    @OrderBy("id asc")
    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image")
    private List<Comment> comments;
}