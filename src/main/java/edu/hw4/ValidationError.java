package edu.hw4;

public class ValidationError extends RuntimeException {
    private final String invalidField;
    private final ErrorType errorType;

    public ValidationError(ErrorType typeError, InvalidField field) {
        super(typeError.errorDescription);
        this.errorType = typeError;
        this.invalidField = field.nameField;
    }

    public String getInvalidField() {
        return invalidField;
    }

    enum ErrorType {
        INVALID_AGE("age must be a positive integer"),
        INVALID_HEIGHT("height must be a positive integer"),
        INVALID_WEIGHT("weight must be a positive integer");

        private final String errorDescription;

        ErrorType(String errorDescription) {
            this.errorDescription = errorDescription;
        }
    }

    enum InvalidField {
        AGE_FIELD("age"),
        HEIGHT_FIELD("height"),
        WEIGHT_FIELD("weight");
        private final String nameField;

        InvalidField(String nameField) {
            this.nameField = nameField;
        }
    }
}
