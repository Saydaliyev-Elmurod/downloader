package org.example.user.domain;

import org.example.user.enums.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
public record User(
    @Id @Column("id") Integer id,
    @Column("first_name") String firstname,
    @Column("last_name") String lastname,
    @Column("username") String username,
    @Column("email") String email,
    @Column("password") String password,
    @Column("is_account_expired") Boolean isAccountExpired,
    @Column("is_account_locked") Boolean isAccountLocked,
    @Column("is_credentials_expired") Boolean isCredentialsExpired,
    @Column("is_enabled") boolean isEnabled,
    @Column("role") Role role,
    @Column("is_confirmed") Boolean isConfirmed,
    @Column("created_at") LocalDateTime createdAt)
    implements UserDetails {

  public User {
    if (isAccountExpired == null) {
      isAccountExpired = false;
    }
    if (isAccountLocked == null) {
      isAccountLocked = false;
    }
    if (isCredentialsExpired == null) {
      isCredentialsExpired = false;
    }
    if (isConfirmed == null) {
      isConfirmed = false;
    }
    isEnabled = true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(role);
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return !isAccountExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !isAccountLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !isCredentialsExpired;
  }

  @Override
  public boolean isEnabled() {
    return isEnabled;
  }

  public User withPassword(String password) {
    return new User(
        id,
        firstname,
        lastname,
        username,
        email,
        password,
        isAccountExpired,
        isAccountLocked,
        isCredentialsExpired,
        isEnabled,
        role,
        isConfirmed,
        createdAt);
  }

  public User withRole(Role role) {
    return new User(
        id,
        firstname,
        lastname,
        username,
        email,
        password,
        isAccountExpired,
        isAccountLocked,
        isCredentialsExpired,
        isEnabled,
        role,
        isConfirmed,
        createdAt);
  }

  public User withIsConfirmed(boolean isConfirmed) {
    return new User(
        id,
        firstname,
        lastname,
        username,
        email,
        password,
        isAccountExpired,
        isAccountLocked,
        isCredentialsExpired,
        isEnabled,
        role,
        isConfirmed,
        createdAt);
  }
}
