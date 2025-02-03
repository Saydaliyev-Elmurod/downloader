package org.example.common.exception.exp;

public class InvalidOperationException extends RuntimeException {

  public InvalidOperationException() {}

  public InvalidOperationException(final String message) {
    super(message);
  }

  public InvalidOperationException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public InvalidOperationException(final Throwable cause) {
    super(cause);
  }
}
