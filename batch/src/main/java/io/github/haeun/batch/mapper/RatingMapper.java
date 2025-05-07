package io.github.haeun.batch.mapper;

import io.github.haeun.batch.domain.bookRating.BookRating;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface RatingMapper {
    void insertBookRatings(@Param("bookRatings") List<BookRating> bookRatings);
}