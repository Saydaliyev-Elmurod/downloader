package org.example.common.exception.exp;

public class UserBlockedException extends ForbiddenException {

  public UserBlockedException() {}

  public UserBlockedException(final String message) {
    super(message);
  }

  public UserBlockedException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
