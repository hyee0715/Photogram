package com.hy.photogram.web.dto.image;

import com.hy.photogram.domain.image.Image;
import com.hy.photogram.domain.user.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadDto {

    private MultipartFile file;
    private String caption;

    public Image toEntity(User user, String post_image_url) {
        return Image.builder()
                .user(user)
                .post_image_url(post_image_url)
                .caption(caption)
                .build();
    }
}
