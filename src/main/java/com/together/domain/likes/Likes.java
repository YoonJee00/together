package com.together.domain.likes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.together.domain.image.Image;
import com.together.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table (
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "likes_uk",
                        columnNames = {"imageId", "userId"}
                )
        }
)
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "imageId")
    @ManyToOne
    private Image image;

    @JoinColumn(name = "userId")
    @ManyToOne
    @JsonIgnoreProperties({"images"})
    private User user;
    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
