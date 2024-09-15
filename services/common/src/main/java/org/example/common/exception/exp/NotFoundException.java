package org.example.common.exception.exp;

import org.apache.logging.log4j.core.lookup.StrMatcher;
import org.example.common.exception.ErrorCodeEnum;

import javax.management.monitor.StringMonitor;

public class NotFoundException extends RuntimeException {
    private final Integer code;

    public NotFoundException(String msg) {
        super(msg);
        this.code = ErrorCodeEnum.NOT_FOUND.code;
    }

    public NotFoundException(Integer code) {
        super();
        this.code = code;
    }

    public NotFoundException(Integer code, final String message) {
        super(message);
        this.code = code;
    }
}
