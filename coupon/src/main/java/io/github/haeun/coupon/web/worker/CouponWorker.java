package io.github.haeun.coupon.web.worker;

import io.github.haeun.coupon.common.constant.CouponConstants;
import io.github.haeun.coupon.domain.couponIssues.CouponIssues;
import io.github.haeun.coupon.domain.couponIssues.CouponIssuesRepository;
import io.github.haeun.coupon.domain.coupons.Coupons;
import io.github.haeun.coupon.domain.coupons.CouponsRepository;
import io.github.haeun.coupon.service.CouponIssueTransactionalService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class CouponWorker {
    private final StringRedisTemplate redisTemplate;
    private final CouponIssueTransactionalService couponIssueTransactionalService;

    @Scheduled(fixedDelay = 5000) // 5초마다 실행
    public void consumeCouponIssuedStream() {
        try {
            List<MapRecord<String, Object, Object>> messages = redisTemplate.opsForStream()
                    .read(
                            Consumer.from(CouponConstants.GROUP_NAME, CouponConstants.CONSUMER_NAME),
                            StreamReadOptions.empty().count(1000).block(Duration.ofSeconds(1)),
                            StreamOffset.create(CouponConstants.STREAM_KEY, ReadOffset.lastConsumed())
                    );

            if (ObjectUtils.isEmpty(messages)) {
                return;
            }

            saveCouponIssues(messages);

        } catch (Exception e) {
            log.error("Error while consuming coupon issue stream", e);
        }
    }

    private void saveCouponIssues(List<MapRecord<String, Object, Object>> messages) {
        Map<Long, List<CouponIssues>> issuesGroupedByCouponId = new HashMap<>();
        Map<Long, List<RecordId>> recordIdGroupedByCouponId = new HashMap<>();

        for (MapRecord<String, Object, Object> message : messages) {
            Map<Object, Object> value = message.getValue();

            Long couponId = Long.valueOf(String.valueOf(value.get("couponId")));
            String userId = String.valueOf(value.get("userId"));
            Timestamp createdAt = Timestamp.valueOf(String.valueOf(value.get("createdAt")));

            issuesGroupedByCouponId
                    .computeIfAbsent(couponId, k -> new ArrayList<>())
                    .add(CouponIssues.builder()
                            .coupons(Coupons.builder().id(couponId).build())
                            .userId(userId)
                            .createdAt(createdAt)
                            .build());

            recordIdGroupedByCouponId
                    .computeIfAbsent(couponId, k -> new ArrayList<>())
                    .add(message.getId());
        }

        for (Map.Entry<Long, List<CouponIssues>> entry : issuesGroupedByCouponId.entrySet()) {
            try {
                couponIssueTransactionalService.saveIssueAndIncrementCount(entry.getKey(), entry.getValue());
                redisTemplate.opsForStream()
                        .acknowledge(CouponConstants.STREAM_KEY, CouponConstants.GROUP_NAME, recordIdGroupedByCouponId.get(entry.getKey())
                                .toArray(new RecordId[0]));
            } catch (Exception e) {
                log.error("ERROR!", e);
            }
        }
    }


}
