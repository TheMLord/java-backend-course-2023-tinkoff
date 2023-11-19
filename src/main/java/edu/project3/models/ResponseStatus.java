package edu.project3.models;

public enum ResponseStatus {
    CONTINUE(100),
    Switching_Protocols(101),

    OK(200),
    CREATED(201),
    NO_CONTENT(204),
    PARTIAL_CONTENT(206),

    MOVED_PERMANENTLY(301),
    FOUND(302),
    NOT_MODIFIED(304),

    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    CONFLICT(409),
    RANGE_NOT_SATISFIABLE(416),

    INTERNAL_SERVER_ERROR(500),

    NOT_IMPLEMENTED(501),

    SERVICE_UNAVAILABLE(503),

    GATEWAY_TIMEOUT(504);

    private final int responseCode;

    ResponseStatus(int i) {
        this.responseCode = i;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public static ResponseStatus responseCodeOf(int code) {
        for (ResponseStatus status : values()) {
            if (status.responseCode == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown response code " + code);
    }
}
