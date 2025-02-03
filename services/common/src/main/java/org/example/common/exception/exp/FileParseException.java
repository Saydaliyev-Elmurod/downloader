package org.example.common.exception.exp;

public class FileParseException extends RuntimeException {

  public FileParseException() {}

  public FileParseException(final String message) {
    super(message);
  }

  public FileParseException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
