package org.example.common.exception.exp;

public class ServiceUnavailableException extends RuntimeException {

  public ServiceUnavailableException() {}

  public ServiceUnavailableException(final String message) {
    super(message);
  }

  public ServiceUnavailableException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ServiceUnavailableException(final Throwable cause) {
    super(cause);
  }
}
