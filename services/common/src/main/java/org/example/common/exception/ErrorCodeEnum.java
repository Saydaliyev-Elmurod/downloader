package org.example.common.exception;

public enum ErrorCodeEnum {
    ALREADY_EXIST(10),
    USER_ALREADY_EXIST(11),
    NOT_FOUND(1),
    USER_NOT_FOUND(2);

    ErrorCodeEnum(int code) {
        this.code = code;
    }

    public final int code;

}
