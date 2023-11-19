package edu.project3.models;

public record RequestRecord(String typeHttpRequest,
                            String resource,
                            String httpVersion) {
}
