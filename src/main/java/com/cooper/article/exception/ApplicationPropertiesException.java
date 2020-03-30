package com.cooper.article.exception;

public class ApplicationPropertiesException extends RuntimeException {
    private static final String message = "There was an error loading the properties for this application.";
    public ApplicationPropertiesException() {
        super(message);
    }
}
