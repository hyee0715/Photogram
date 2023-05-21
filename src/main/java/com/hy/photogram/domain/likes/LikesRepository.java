package com.hy.photogram.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    
    @Modifying
    @Query(value = "INSERT INTO likes(image_id, user_id, create_date) VALUES(:image_id, :principal_id, now())", nativeQuery = true)
    int likes(@Param("image_id") Long image_id, @Param("principal_id") Long principal_id);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE image_id = :image_id AND user_id = :principal_id", nativeQuery = true)
    int unLikes(Long image_id, Long principal_id);
}
