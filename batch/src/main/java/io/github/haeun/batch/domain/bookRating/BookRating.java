package io.github.haeun.batch.domain.bookRating;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Entity
public class BookRating {
    @Comment("The Id of BookRating")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Comment("The Id of Book")
    private String bookId;

    @Comment("Book Title")
    @Column(length = 1000)
    private String title;

    @Comment("The price of Book")
    @ColumnDefault("0")
    private Double price;

    @Comment("Id of the user who rates the book")
    @Column(length = 30)
    private String userId;

    @Comment("Name of the user who rates the book")
    private String profileName;

    @Comment("helpfulness rating of the review, e.g. 2/3")
    @Column(length = 50)
    private String helpfulness;

    @Comment("rating from 0 to 5 for the book")
    @ColumnDefault("0")
    @Column(precision = 2, scale = 1)
    private BigDecimal score;

    @Comment("time of given the review")
    private Timestamp time;

    @Comment("the summary of a text review")
    private String summary;

    @Comment("the full text of a review")
    @Column(columnDefinition = "TEXT")
    private String text;

}
