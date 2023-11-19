package edu.project3.models;

import java.time.OffsetDateTime;

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
