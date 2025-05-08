package io.github.haeun.batch.job.task.book.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.Resource;

import java.util.HashMap;
import java.util.Map;

public class CsvReaderFactory {
    public static FlatFileItemReader<Map<String, Object>> createReader(Resource resource, String name, String[] fieldNames) {
        return new FlatFileItemReaderBuilder<Map<String, Object>>()
                .name(name)
                .resource(resource)
                .linesToSkip(1)
                .lineMapper(new DefaultLineMapper<>() {{
                    setLineTokenizer(new DelimitedLineTokenizer() {{
                        setNames(fieldNames);
                    }});
                    setFieldSetMapper((FieldSetMapper<Map<String, Object>>) fieldSet -> {
                        Map<String, Object> map = new HashMap<>();
                        for (String fieldName : fieldNames) {
                            map.put(fieldName, fieldSet.readString(fieldName));
                        }
                        return map;
                    });
                }})
                .build();
    }
}
