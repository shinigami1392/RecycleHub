package edu.sunhacks.recyclehub.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String id) {
        this.username = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
