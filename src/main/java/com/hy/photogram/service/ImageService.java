package com.hy.photogram.service;

import com.hy.photogram.config.auth.PrincipalDetails;
import com.hy.photogram.domain.image.Image;
import com.hy.photogram.domain.image.ImageRepository;
import com.hy.photogram.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public void upload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        } catch(Exception e) {
            e.printStackTrace();
        }

        Image image = imageUploadDto.toEntity(principalDetails.getUser(), imageFileName);
        imageRepository.save(image);
    }

    @Transactional(readOnly = true)
    public Page<Image> story(Long principalId, Pageable pageable) {
        Page<Image> images = imageRepository.story(principalId, pageable);

        images.forEach((image) -> {
            image.setLikesCount(image.getLikes().size());
            image.getLikes().forEach((like) -> {
                if (like.getUser().getId() == principalId)
                    image.setLikesState(true);
            });
        });

        return images;
    }

    @Transactional(readOnly = true)
    public List<Image> popular() {
        return imageRepository.popular();
    }
}