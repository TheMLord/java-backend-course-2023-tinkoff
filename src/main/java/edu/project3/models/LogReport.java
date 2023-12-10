package edu.project3.models;

import java.util.List;

/**
 * Модель метрики лога.
 */
public record LogReport(String metricHeader,
                        List<String> tableHeaders,
                        List<List<String>> tableValues) {
}
