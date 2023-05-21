package com.hy.photogram.service;

import com.hy.photogram.domain.likes.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;

    @Transactional
    public void likes(Long imageId, Long principalId) {
        likesRepository.likes(imageId, principalId);
    }

    @Transactional
    public void unLikes(Long imageId, Long principalId) {
        likesRepository.unLikes(imageId, principalId);
    }
}
