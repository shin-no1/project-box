package io.github.haeun.batch.job.task.book.step;

import io.github.haeun.batch.job.constant.StepConstant;
import io.github.haeun.batch.job.dto.book.BookRatingDto;
import io.github.haeun.batch.job.task.book.reader.CsvReaderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Configuration
public class BookRatingStep {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;

    private final List<String> bookRatingColumns = Arrays.asList("book_id", "title", "price", "user_id", "profile_name", "helpfulness", "score", "time", "summary", "text");
    private final Integer poolSize = 10;

    public BookRatingStep(JobRepository jobRepository,
                          PlatformTransactionManager transactionManager, DataSource dataSource) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.dataSource = dataSource;
    }

    @Bean(name = "stepBookRating")
    public Step bookRatingStep() {
        return new StepBuilder("stepBookRating", jobRepository)
                .<Map<String, Object>, BookRatingDto>chunk(1000, transactionManager)
                .reader(bookRatingReader())
                .processor(bookRatingProcessor())
                .writer(bookRatingWriter())
                .taskExecutor(taskExecutor())
                .throttleLimit(poolSize)
                .build();
    }

    @Bean
    public SynchronizedItemStreamReader<Map<String, Object>> bookRatingReader() {
        FlatFileItemReader<Map<String, Object>> delegate = CsvReaderFactory.createReader(
                new FileSystemResource("C:/Users/Haeun/main/study/coding/project_batch_book/Amazon Books Reviews/Books_rating.csv"),
                "bookReader",
                new String[]{"Id", "Title", "Price", "User_id", "profileName", "review/helpfulness", "review/score", "review/time", "review/summary", "review/text"}
        );
        SynchronizedItemStreamReader<Map<String, Object>> reader = new SynchronizedItemStreamReader<>();
        reader.setDelegate(delegate);
        return reader;
    }

    @Bean
    public ItemProcessor<Map<String, Object>, BookRatingDto> bookRatingProcessor() {
        return item -> {
            if (ObjectUtils.isEmpty(item)) {
                return null;
            }
            try {
                BookRatingDto bookRatingDto = new BookRatingDto();
                bookRatingDto.setBookId(item.getOrDefault("Id", "").toString());
                bookRatingDto.setTitle(item.getOrDefault("Title", "").toString());
                bookRatingDto.setPrice(getDoubleValue(item, "Price"));
                bookRatingDto.setUserId(item.getOrDefault("User_id", "").toString());
                bookRatingDto.setProfileName(item.getOrDefault("profileName", "").toString());
                bookRatingDto.setHelpfulness(item.getOrDefault("review/helpfulness", "").toString());
                bookRatingDto.setScore(Double.parseDouble(item.getOrDefault("review/score", "0").toString()));
                bookRatingDto.setTime(Instant.ofEpochSecond(Long.parseLong(item.getOrDefault("review/time", "0").toString()))
                        .atZone(ZoneId.of("UTC"))
                        .toLocalDateTime());
                bookRatingDto.setSummary(item.getOrDefault("review/summary", "").toString());
                bookRatingDto.setText(item.getOrDefault("review/text", "").toString());
                return bookRatingDto;
            } catch (Exception e) {
                log.error("Error in BookRatingProcessor", e);
            }
            return null;
        };
    }

    private Double getDoubleValue(Map<String, Object> item, String key) {
        String priceStr = Optional.ofNullable(item.get(key))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .orElse("0.0");
        return Double.parseDouble(priceStr);
    }

    @Bean
    public ItemWriter<BookRatingDto> bookRatingWriter() {
        String tableName = "book_rating";
        JdbcBatchItemWriter<BookRatingDto> delegate = new JdbcBatchItemWriterBuilder<BookRatingDto>()
                .dataSource(dataSource)
                .sql(StepConstant.getSqlQuery(tableName, bookRatingColumns, false))
                .beanMapped()
                .build();
        delegate.afterPropertiesSet();
        return new ItemWriter<>() {
            @Override
            public void write(Chunk<? extends BookRatingDto> chunk) throws Exception {
                long start = System.currentTimeMillis();
                delegate.write(chunk);
                long end = System.currentTimeMillis();
                log.info("📦 INSERTED {} rows in {} ms", chunk.getItems().size(), end - start);
            }
        };
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(poolSize);
        executor.setThreadNamePrefix("batch-thread-");
        executor.initialize();
        return executor;
    }
}
