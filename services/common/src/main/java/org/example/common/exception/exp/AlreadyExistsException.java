package org.example.common.exception.exp;

public class AlreadyExistsException extends RuntimeException {
    private final Integer code;

    public AlreadyExistsException(final Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
