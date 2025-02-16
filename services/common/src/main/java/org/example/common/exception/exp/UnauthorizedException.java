package org.example.common.exception.exp;

public class UnauthorizedException extends RuntimeException {

  public UnauthorizedException() {}

  public UnauthorizedException(final String message) {
    super(message);
  }

  public UnauthorizedException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public UnauthorizedException(final Throwable cause) {
    super(cause);
  }
}
