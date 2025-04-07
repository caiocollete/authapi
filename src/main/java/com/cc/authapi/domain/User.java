package com.cc.authapi.domain;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "key_id", referencedColumnName = "id")
    private Key key;

    public User() {
    }


    // register
    public User(Long id, String username, String email, String password, Date requestDate, UUID key) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.requestDate = requestDate;
        this.key = new Key(key);
    }

    public User(String user, String email, String password, Date requestDate) {
        this.username = user;
        this.email = email;
        this.password = password;
        this.requestDate = requestDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // login
    public User(Date requestDate, String password, String user) {
        this.requestDate = requestDate;
        this.password = password;
        this.username = user;
    }

    public boolean isLogin() {
        return email == null;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }
}