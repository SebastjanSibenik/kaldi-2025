package com.kaldi.app.common.dto;

import com.kaldi.app.common.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDto {

    @NotBlank(message = "username cannot be empty")
    private String username;

    @NotNull(message = "role cannot be null")
    private Role role;

    public String getUsername() {
        return username;
    }

    public UserDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public UserDto setRole(Role role) {
        this.role = role;
        return this;
    }

    @Override
    public String toString() {
        return String.format(
                "UserDto{username='%s', role=%s}",
                username,
                role.toString()
        );
    }
}
