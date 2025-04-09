package com.cc.authapi.dtos;

import java.util.Date;
import java.util.UUID;

public class KeyDTO {
    private UUID id;
    private boolean use;
    private Date expires;

    public KeyDTO() {}

    public KeyDTO(UUID id, boolean use, Date expires) {
        this.id = id;
        this.use = use;
        this.expires = expires;
    }

    // Getters e Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isUse() {
        return use;
    }

    public void setUse(boolean use) {
        this.use = use;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }
}