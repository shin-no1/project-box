package io.github.haeun.batch.service.WriterTest;

import io.github.haeun.batch.domain.bookRating.BookRating;
import io.github.haeun.batch.mapper.RatingMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Transactional
@Service
public class InsertTester implements CommandLineRunner {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @PersistenceContext
    EntityManager em;
    @Autowired
    RatingMapper ratingMapper;

    @Override
    public void run(String... args) {
        List<BookRating> dummyList = IntStream.range(0, 100_000)
                .mapToObj(i -> new BookRating(null, "user" + i, "book" + i, i % 5 + 1, null))
                .collect(Collectors.toList());

        insertWithJdbc(dummyList);
        insertWithJpa(dummyList);
        insertWithMyBatis(dummyList);
    }

    public void insertWithJdbc(List<BookRating> list) {
        long start = System.currentTimeMillis();

        String sql = "INSERT INTO book_rating (user_id, book_id, rating, created_at) VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                BookRating r = list.get(i);
                ps.setString(1, r.getUserId());
                ps.setString(2, r.getBookId());
                ps.setInt(3, r.getRating());
                ps.setTimestamp(4, new Timestamp(new Date().getTime()));
            }

            public int getBatchSize() {
                return list.size();
            }
        });

        log.info("[ JDBC ] size: {}, {}ms", list.size(), System.currentTimeMillis() - start);
    }

    public void insertWithJpa(List<BookRating> list) {
        long start = System.currentTimeMillis();
        int batchSize = 1000;
        for (int i = 0; i < list.size(); i++) {
            em.persist(list.get(i));
            if (i % batchSize == 0 && i > 0) {
                em.flush();
                em.clear();
            }
        }
        em.flush();
        em.clear();
        log.info("[ JPA ] size: {}, {}ms", list.size(), System.currentTimeMillis() - start);
    }

    public void insertWithMyBatis(List<BookRating> list) {
        long start = System.currentTimeMillis();
        ratingMapper.insertBookRatings(list);
        log.info("[ MyBatis ] size: {}, {}ms", list.size(), System.currentTimeMillis() - start);
    }
}
