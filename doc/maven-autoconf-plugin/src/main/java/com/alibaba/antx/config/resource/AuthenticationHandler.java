package com.alibaba.antx.config.resource;

import java.net.URI;

public interface AuthenticationHandler {
    UsernamePassword authenticate(String message, URI uri, String username, boolean visited);

    public static class UsernamePassword {
        private String username;
        private String password;

        public UsernamePassword(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String toString() {
            return getUsername() + ":" + getPassword();
        }
    }
}
