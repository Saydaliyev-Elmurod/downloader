package org.example.common.exception;



import java.io.Serializable;

public enum Error implements Serializable {

    WRONG_CREDENTIALS(1000, "Wrong credential!"),
    UN_AUTHORIZE(1001, "User is un-authorize to access this resource"),
    USER_ALREADY_EXIST(1002, "User Already exist"),
    USER_NOT_FOUND(1003, "User not found");

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    private String message;

    Error(int code, String message) {
        this.code = code;
        this.message = message;
    }
}