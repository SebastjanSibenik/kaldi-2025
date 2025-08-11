package com.kaldi.app.common.dto;

import com.kaldi.app.common.enums.Role;

public class UserDto {

    private Role role;
    private String username;

    public Role getRole() {
        return role;
    }

    public UserDto setRole(Role role) {
        this.role = role;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDto setUsername(String username) {
        this.username = username;
        return this;
    }
}
