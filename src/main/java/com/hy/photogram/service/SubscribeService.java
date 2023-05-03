package com.hy.photogram.service;

import com.hy.photogram.domain.subscribe.SubscribeRepository;
import com.hy.photogram.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    @Transactional
    public void subs(Long from_user_id, Long to_user_id) {
        try {
            subscribeRepository.Subscribe(from_user_id, to_user_id);
        } catch (Exception e) {
            throw new CustomApiException("구독하기 오류: 이미 구독한 상태입니다.");
        }
    }

    @Transactional
    public void unSubs(Long from_user_id, Long to_user_id) {
        subscribeRepository.unSubscribe(from_user_id, to_user_id);
    }
}
