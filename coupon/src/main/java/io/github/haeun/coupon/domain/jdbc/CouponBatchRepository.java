package io.github.haeun.coupon.domain.jdbc;

import io.github.haeun.coupon.domain.couponIssues.CouponIssues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
public class CouponBatchRepository {
    private final JdbcTemplate jdbcTemplate;

    public CouponBatchRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsertCouponIssues(List<CouponIssues> issues) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO coupon_issues (coupon_id, user_id, created_at) VALUES (?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        CouponIssues issue = issues.get(i);
                        ps.setLong(1, issue.getCoupons().getId());
                        ps.setString(2, issue.getUserId());
                        ps.setTimestamp(3, issue.getCreatedAt());
                    }

                    public int getBatchSize() {
                        return issues.size();
                    }
                }
        );
        log.info("CouponIssues Inserted: {}", issues.size());
    }
}
