package com.hy.photogram.domain.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hy.photogram.domain.likes.Likes;
import com.hy.photogram.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption;
    private String post_image_url;	//사진을 전달받아서 서버의 특정 폴더에 저장할 것이므로 사진이 저장된 경로를 저장

    @JsonIgnoreProperties({"user"})
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image")
    private List<Likes> likes;

    @Transient	//데이터베이스에 컬럼을 생성하지 않음
    private boolean likesState;

    @Transient
    private int likesCount;

    private LocalDateTime create_date;

    @PrePersist
    public void createDate() {
        create_date = LocalDateTime.now();
    }
}