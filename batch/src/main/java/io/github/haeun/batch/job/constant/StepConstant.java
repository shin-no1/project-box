package io.github.haeun.batch.job.constant;

import java.util.List;
import java.util.stream.Collectors;

public class StepConstant {
    public static String getSqlQuery(String table, List<String> columns, boolean isIgnore) {
        return String.format(
                "INSERT %s INTO %s (%s) VALUES (%s)",
                isIgnore ? "IGNORE " : "",
                table,
                String.join(", ", columns),
                columns.stream()
                        .map(col -> ":" + snakeToCamel(col))
                        .collect(Collectors.joining(", "))
        );
    }

    private static String snakeToCamel(String snake) {
        StringBuilder result = new StringBuilder();
        boolean upper = false;
        for (char c : snake.toCharArray()) {
            if (c == '_') {
                upper = true;
            } else {
                result.append(upper ? Character.toUpperCase(c) : c);
                upper = false;
            }
        }
        return result.toString();
    }
}
