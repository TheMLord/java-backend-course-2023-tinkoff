package edu.project3.models;

/**
 * Модель информации об HTTP запросе в логах.
 */
public record RequestRecord(String typeHttpRequest,
                            String resource,
                            String httpVersion) {
}
