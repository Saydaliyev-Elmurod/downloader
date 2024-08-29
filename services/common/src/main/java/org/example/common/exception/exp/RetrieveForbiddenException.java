package org.example.common.exception.exp;

public class RetrieveForbiddenException extends ForbiddenException {

  public RetrieveForbiddenException() {}

  public RetrieveForbiddenException(final String message) {
    super(message);
  }

  public RetrieveForbiddenException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public RetrieveForbiddenException(final Throwable cause) {
    super(cause);
  }
}
