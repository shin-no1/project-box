package io.github.haeun.batch.job.task.book.step;

import io.github.haeun.batch.job.constant.StepConstant;
import io.github.haeun.batch.job.dto.book.BookDto;
import io.github.haeun.batch.job.task.book.reader.CsvReaderFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class BookStep {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;

    private final List<String> bookColumns = Arrays.asList("title", "description", "authors", "image", "preview_link", "publisher", "published_date", "info_link", "categories", "ratings_count");

    public BookStep(JobRepository jobRepository,
                    PlatformTransactionManager transactionManager, DataSource dataSource) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.dataSource = dataSource;
    }

    @Bean(name = "stepBook")
    public Step bookStep() {
        return new StepBuilder("stepBook", jobRepository)
                .<Map<String, Object>, BookDto>chunk(1000, transactionManager)
                .reader(bookReader())
                .processor(bookProcessor())
                .writer(bookWriter())
                .build();
    }

    @Bean
    public FlatFileItemReader<Map<String, Object>> bookReader() {
        return CsvReaderFactory.createReader(
                new FileSystemResource("C:/Users/Haeun/main/study/coding/project_batch_book/Amazon Books Reviews/books_data.csv"),
                "bookReader",
                new String[]{"Title", "description", "authors", "image", "previewLink", "publisher", "publishedDate", "infoLink", "categories", "ratingsCount"}
        );
    }

    @Bean
    public ItemProcessor<Map<String, Object>, BookDto> bookProcessor() {
        return item -> {
            if (ObjectUtils.isEmpty(item)) {
                return null;
            }

            BookDto bookDto = new BookDto();
            bookDto.setTitle(item.getOrDefault("Title", "").toString());
            bookDto.setDescription(item.getOrDefault("description", "").toString());
            bookDto.setAuthors(parseListToString(item, "authors"));
            bookDto.setImage(item.getOrDefault("image", "").toString());
            bookDto.setPreviewLink(item.getOrDefault("previewLink", "").toString());
            bookDto.setPublisher(item.getOrDefault("publisher", "").toString());
            bookDto.setPublishedDate(item.getOrDefault("publishedDate", "").toString());
            bookDto.setInfoLink(item.getOrDefault("infoLink", "").toString());
            bookDto.setCategories(parseListToString(item, "categories"));
            String ratingsCount = item.getOrDefault("ratingsCount", "").toString();
            bookDto.setRatingsCount(ObjectUtils.isEmpty(ratingsCount) ? 0 : (long) Double.parseDouble(ratingsCount));
            return bookDto;
        };
    }

    private String parseListToString(Map<String, Object> map, String column) {
        String value = map.getOrDefault(column, "").toString();
        if (ObjectUtils.isEmpty(value)) {
            return "";
        }
        return Arrays.stream(
                        value.replace("[", "")
                                .replace("]", "")
                                .replace("'", "")
                                .split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(", "));
    }

    @Bean
    public JdbcBatchItemWriter<BookDto> bookWriter() {
        String tableName = "book";
        return new JdbcBatchItemWriterBuilder<BookDto>()
                .dataSource(dataSource)
                .sql(StepConstant.getSqlQuery(tableName, bookColumns, false))
                .beanMapped()
                .build();
    }
}
