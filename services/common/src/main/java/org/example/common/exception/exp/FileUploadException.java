package org.example.common.exception.exp;

public class FileUploadException extends RuntimeException {

  public FileUploadException() {}

  public FileUploadException(final String message) {
    super(message);
  }

  public FileUploadException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
