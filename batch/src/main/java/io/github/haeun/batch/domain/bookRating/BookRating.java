package io.github.haeun.batch.domain.bookRating;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class BookRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String bookId;
    private int rating;

    @CreatedDate
    private Date createdAt;
}