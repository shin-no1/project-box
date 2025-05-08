package io.github.haeun.batch.domain.book;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@NoArgsConstructor
@Getter
@Entity
public class Book {
    @Comment("The Id of Book")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Comment("Book Title")
    @Column(length = 1000)
    private String Title;

    @Comment("description of book")
    @Column(columnDefinition = "TEXT")
    private String description;

    @Comment("Name of book authors")
    @Column(length = 2500)
    private String authors;

    @Comment("url for book cover")
    @Column(length = 2000)
    private String image;

    @Comment("link to access this book on google Books")
    @Column(length = 1000)
    private String previewLink;

    @Comment("Name of the publisheer")
    private String publisher;

    @Comment("the date of publish")
    @Column(length = 25)
    private String publishedDate;

    @Comment("link to get more information about the book on google books")
    @Column(length = 1000)
    private String infoLink;

    @Comment("genres of books")
    @Column(length = 1000)
    private String categories;

    @Comment("averaging rating for book")
    private Long ratingsCount;

}
