package com.user.imvs.dtos;

public class AuthServiceResponse {
        private String username;
        private String role;
        private String token;

        public String getToken() {
            return this.token;
        }
        public void setToken(String token) {
            this.token = token;
        }
        public String getUsername() {
            return this.username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getRole() {
            return this.role;
        }
        public void setRole(String role) {
            this.role = role;
        }
}
