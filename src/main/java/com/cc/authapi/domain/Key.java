package com.cc.authapi.domain;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "keys")
public class Key {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expires;

    @Column(nullable = false)
    private boolean use;

    public Key() {}

    // to create
    public Key(Date expires) {
        this.expires = expires;
        this.use = false;
    }

    // get do banco
    public Key(UUID id, Date expires, boolean use) {
        this.id = id;
        this.expires = expires;
        this.use = use;
    }

    public Key(UUID id) {
        this.id = id;
    }

    // Getters e setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID key) {
        this.id = key;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public boolean isUse() {
        return use;
    }

    public void setUse(boolean use) {
        this.use = use;
    }

    public boolean isExpired() {
        return expires.before(new Date());
    }
}