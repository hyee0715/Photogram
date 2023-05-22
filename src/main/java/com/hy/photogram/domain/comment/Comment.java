package com.hy.photogram.domain.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hy.photogram.domain.image.Image;
import com.hy.photogram.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String content;

    @JsonIgnoreProperties({"images"})
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "image_id")
    @ManyToOne
    private Image image;

    private LocalDateTime create_date;

    @PrePersist     //데이터베이스에 INSERT 되기 직전에 실행
    public void createDate() {
        this.create_date = LocalDateTime.now();
    }


}
