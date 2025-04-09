package com.cc.authapi.dtos;

public class UserWithKeyDTO {
    private Long id;
    private String username;
    private String email;
    private KeyDTO key;

    public UserWithKeyDTO() {}

    public UserWithKeyDTO(Long id, String username, String email, KeyDTO key) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.key = key;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public KeyDTO getKey() {
        return key;
    }

    public void setKey(KeyDTO key) {
        this.key = key;
    }
}
