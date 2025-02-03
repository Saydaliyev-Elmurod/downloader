package org.example.common.exception.exp;

public class InvalidArgumentException extends RuntimeException {

  public InvalidArgumentException() {}

  public InvalidArgumentException(final String message) {
    super(message);
  }

  public InvalidArgumentException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public InvalidArgumentException(final Throwable cause) {
    super(cause);
  }
}
