package edu.project3.models;

import java.util.List;

public record LogReport(String metricHeader,
                        List<String> tableHeaders,
                        List<List<String>> tableValues) {
}
