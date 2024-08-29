package org.example.common.exception.exp;

public class TokenExpiredException extends ForbiddenException {

  public TokenExpiredException() {}

  public TokenExpiredException(final String message) {
    super(message);
  }

  public TokenExpiredException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public TokenExpiredException(final Throwable cause) {
    super(cause);
  }
}
