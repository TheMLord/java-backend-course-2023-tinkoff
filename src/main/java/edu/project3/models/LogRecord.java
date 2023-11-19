package edu.project3.models;

import java.time.OffsetDateTime;

/**
 * Модель лога NGINX.
 */
public record LogRecord(String remoteAddress,
                        String userId,
                        String authId,
                        OffsetDateTime time,
                        RequestRecord request,
                        ResponseStatus status,
                        long bodyBytesSentSize,
                        String httpRefer,
                        String userAgent) {
}
