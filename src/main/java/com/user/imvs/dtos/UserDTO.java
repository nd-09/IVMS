package com.user.imvs.dtos;

import com.user.imvs.model.Role;
import lombok.Data;

@Data
public class UserDTO {
  private Long id;
  private String username;
  private String email;
  private String password;
  private Role role;

  public Long getId() {
          return this.id;
  }
  public void setId(Long id) {
          this.id = id;
  }
  public String getUsername() {
          return this.username;
  }
  public void setUsername(String username) {
          this.username = username;
  }

  public String getEmail() {
          return this.email;
  }
  public void setEmail(String email) {
          this.email = email;
  }
  public Role getRole() {
          return this.role;
  }
  public void setRole(Role role) {
          this.role = role;
  }
  public String getPassword() {
      return this.password;
  }
  public void setPassword(String password) {
      this.password = password;
  }
}
