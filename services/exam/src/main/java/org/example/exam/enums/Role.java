package org.example.exam.enums;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role implements GrantedAuthority {
  USER("USER"),
  ADMIN("ADMIN");

  private final String value;

  Role(String value) {
    this.value = value;
  }

  @Override
  public String getAuthority() {
    return name();
  }
}
