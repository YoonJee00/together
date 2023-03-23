package com.together.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.together.domain.image.Image;
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
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100, unique=true)
    private String username;

    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    private String website;
    private String bio; //자기소개
    @Column(nullable = false)
    private String email;
    private String phone;
    private String gender;
    private String profileImageUrl;
    private String provider; //제공자 구글,페북,네이버
    private String role; //USER, ADMIN

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<Image> images;

    private LocalDateTime createDate;

    @PrePersist // DB에 insert 되기 직전에 실행, 나중에 DB에 값을 넣을 때 위에 값만 넣어주면 createDate는 자동으로 들어감.
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", website="
                + website + ", bio=" + bio + ", email=" + email + ", phone=" + phone + ", gender=" + gender
                + ", profileImageUrl=" + profileImageUrl + ", role=" + role +", createDate="
                + createDate + "]";
    }
}
