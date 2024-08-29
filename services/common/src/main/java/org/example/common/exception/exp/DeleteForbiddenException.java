package org.example.common.exception.exp;

public class DeleteForbiddenException extends ForbiddenException {

  public DeleteForbiddenException() {}

  public DeleteForbiddenException(final String message) {
    super(message);
  }

  public DeleteForbiddenException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public DeleteForbiddenException(final Throwable cause) {
    super(cause);
  }
}
