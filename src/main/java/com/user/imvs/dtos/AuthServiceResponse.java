package com.user.imvs.dtos;

public class AuthServiceResponse {
        private String token;
        private String username;

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
}
