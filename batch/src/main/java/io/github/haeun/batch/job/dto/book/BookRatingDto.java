package io.github.haeun.batch.job.dto.book;

import io.github.haeun.batch.domain.book.Book;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class BookRatingDto {
    private int id;
    private Book book;
    private Double price;
    private String userId;
    private String profileName;
    private String helpfulness;
    private BigDecimal score;
    private Timestamp time;
    private String summary;
    private String text;
}
