package io.github.haeun.coupon.web.worker;

import io.github.haeun.coupon.common.constant.CouponConstants;
import io.github.haeun.coupon.domain.couponIssues.CouponIssues;
import io.github.haeun.coupon.domain.couponIssues.CouponIssuesRepository;
import io.github.haeun.coupon.domain.coupons.Coupons;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class CouponWorker {
    private final StringRedisTemplate redisTemplate;
    private final CouponIssuesRepository couponIssuesRepository;

    @Scheduled(fixedDelay = 5000) // 5초마다 실행
    public void consumeCouponIssuedStream() {
        try {
            List<MapRecord<String, Object, Object>> messages = redisTemplate.opsForStream()
                    .read(
                            Consumer.from(CouponConstants.GROUP_NAME, CouponConstants.CONSUMER_NAME),
                            StreamReadOptions.empty().count(10).block(Duration.ofSeconds(1)),
                            StreamOffset.create(CouponConstants.STREAM_KEY, ReadOffset.lastConsumed())
                    );

            if (ObjectUtils.isEmpty(messages)) {
                return;
            }

            for (MapRecord<String, Object, Object> message : messages) {
                try {
                    Map<Object, Object> value = message.getValue();
                    String couponIdStr = String.valueOf(value.get("couponId"));
                    String userId = String.valueOf(value.get("userId"));

                    log.info("Processing coupon issue event: couponId={}, userId={}", couponIdStr, userId);

                    CouponIssues issued = CouponIssues.builder()
                            .coupons(Coupons.builder().id(Long.parseLong(couponIdStr)).build())
                            .userId(userId)
                            .build();
                    couponIssuesRepository.save(issued);

                    // TODO: Coupons 에는 남은 쿠폰 어떻게 관리할지 (Coupons.quantity - Issues.count ?) -> Coupon 수량 조회에 필요

                    // 처리 완료된 메시지 ack
                    redisTemplate.opsForStream()
                            .acknowledge(CouponConstants.STREAM_KEY, CouponConstants.GROUP_NAME, message.getId());
                } catch (Exception e) {
                    log.error("ERROR!", e);
                }
            }
        } catch (Exception e) {
            log.error("Error while consuming coupon issue stream", e);
        }
    }
}
