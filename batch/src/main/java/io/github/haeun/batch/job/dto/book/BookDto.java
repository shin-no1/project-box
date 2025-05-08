package io.github.haeun.batch.job.dto.book;

import lombok.Data;

@Data
public class BookDto {
    private String id;
    private String title;
    private String description;
    private String authors;
    private String image;
    private String previewLink;
    private String publisher;
    private String publishedDate;
    private String infoLink;
    private String categories;
    private Long ratingsCount;
}
