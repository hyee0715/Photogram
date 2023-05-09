package com.hy.photogram.service;

import com.hy.photogram.domain.subscribe.SubscribeRepository;
import com.hy.photogram.handler.ex.CustomApiException;
import com.hy.photogram.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager em;

    @Transactional
    public void subs(Long fromUserId, Long toUserId) {
        try {
            subscribeRepository.subscribe(fromUserId, toUserId);
        } catch (Exception e) {
            throw new CustomApiException("구독하기 오류: 이미 구독한 상태입니다.");
        }
    }

    @Transactional
    public void unSubs(Long fromUserId, Long toUserId) {
        subscribeRepository.unSubscribe(fromUserId, toUserId);
    }

    @Transactional(readOnly = true)
    public List<SubscribeDto> subscribeList(Long principalId, Long pageUserId) {

        //쿼리 준비
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.username, u.profile_image_url, ");
        sb.append("if ((SELECT 1 FROM subscribe WHERE from_user_id = ? AND to_user_id = u.id), 1, 0) subscribe_state, ");
        sb.append("if ((? = u.id), 1, 0) equal_user_state ");
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.to_user_id ");
        sb.append("WHERE s.from_user_id = ?");

        //쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);

        //쿼리 실행
        JpaResultMapper resultMapper = new JpaResultMapper();
        List<SubscribeDto> list = resultMapper.list(query, SubscribeDto.class);

        return list;
    }
}
