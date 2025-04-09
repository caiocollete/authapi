package com.cc.authapi.application;

import com.cc.authapi.domain.Key;
import com.cc.authapi.domain.User;
import com.cc.authapi.dtos.KeyDTO;
import com.cc.authapi.dtos.UserDTO;
import com.cc.authapi.dtos.UserWithKeyDTO;

public abstract class DtosMappers {

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
    }

    public static KeyDTO toKeyDTO(Key key) {
        return new KeyDTO(key.getId(), key.isUse(), key.getExpires());
    }

    public static UserWithKeyDTO toUserWithKeyDTO(User user) {
        return new UserWithKeyDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                toKeyDTO(user.getKey())
        );
    }


}
