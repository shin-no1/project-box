package io.github.haeun.coupon.common.stream;

import io.github.haeun.coupon.common.constant.CouponConstants;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StreamInitializer {

    private final StringRedisTemplate redisTemplate;

    /**
     * 프로젝트가 실행될 때 자동으로 Stream + Consumer Group이 준비
     * Redis는 Stream이 존재해야만 Consumer Group을 만들 수 있기 때문에
     * dummy 데이터 하나라도 넣고 나서 그룹을 만듦
     */
    @PostConstruct
    public void initStreamAndGroup() {
        try {
            // 1. Stream 키가 존재하는지 확인
            boolean streamExists = redisTemplate.hasKey(CouponConstants.STREAM_KEY);

            if (!streamExists) {
                log.info("Stream '{}' does not exist. Creating dummy record...", CouponConstants.STREAM_KEY);
                // Stream이 없으면 dummy 데이터 하나 넣어서 스트림 생성
                redisTemplate.opsForStream().add(
                        StreamRecords.newRecord()
                                .ofObject("init")
                                .withStreamKey(CouponConstants.STREAM_KEY)
                );
            }

            // 2. Consumer Group 존재하는지 확인하고 없으면 생성
            try {
                redisTemplate.opsForStream().createGroup(CouponConstants.STREAM_KEY, CouponConstants.GROUP_NAME);
                log.info("Consumer Group '{}' created for Stream '{}'.", CouponConstants.GROUP_NAME, CouponConstants.STREAM_KEY);
            } catch (DataAccessException e) {
                // 이미 존재해서 발생하는 에러는 무시
                log.warn("Consumer Group '{}' already exists for Stream '{}'.", CouponConstants.GROUP_NAME, CouponConstants.STREAM_KEY);
            }
        } catch (Exception e) {
            log.error("Failed to initialize Stream or Consumer Group", e);
        }
    }
}
