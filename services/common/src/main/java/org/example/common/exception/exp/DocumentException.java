package org.example.common.exception.exp;

public class DocumentException extends RuntimeException {

  public DocumentException() {}

  public DocumentException(final String message) {
    super(message);
  }

  public DocumentException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
