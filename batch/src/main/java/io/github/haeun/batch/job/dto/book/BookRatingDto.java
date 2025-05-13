package io.github.haeun.batch.job.dto.book;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookRatingDto {
    private String bookId;
    private String title;
    private Double price;
    private String userId;
    private String profileName;
    private String helpfulness;
    private Double score;
    private LocalDateTime time;
    private String summary;
    private String text;
}
