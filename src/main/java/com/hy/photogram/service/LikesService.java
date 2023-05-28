package com.hy.photogram.service;

import com.hy.photogram.domain.likes.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;

    /* 게시글 좋아요 */
    @Transactional
    public void likes(Long imageId, Long principalId) {
        likesRepository.likes(imageId, principalId);
    }

    /* 게시글 좋아요 해제 */
    @Transactional
    public void unLikes(Long imageId, Long principalId) {
        likesRepository.unLikes(imageId, principalId);
    }
}
