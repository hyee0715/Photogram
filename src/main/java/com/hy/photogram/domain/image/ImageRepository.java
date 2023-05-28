package com.hy.photogram.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long>{

    /* 스토리 페이지(구독한 사용자의 게시글 출력) */
    @Query(value = "SELECT * FROM image WHERE user_id IN (SELECT to_user_id FROM subscribe WHERE from_user_id = :principal_id) ORDER BY id DESC", nativeQuery = true)
    Page<Image> story(@Param("principal_id") Long principal_id, Pageable pageable);

    /* 인기 페이지(좋아요 수에 따라 인기 게시글 출력) */
    @Query(value = "SELECT i.* FROM image i INNER JOIN (SELECT image_id, COUNT(image_id) like_count FROM likes GROUP BY image_id) c ON i.id = c.image_id ORDER BY like_count DESC", nativeQuery = true)
    List<Image> popular();
}