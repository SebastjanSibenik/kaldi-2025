package com.kaldi.app.model.user;

import com.kaldi.app.common.enums.Role;
import com.kaldi.app.model.BaseEntity;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
@UserDefinition
public class User extends BaseEntity {

    @Username
    @Column(unique = true, nullable = false)
    private String username;

    @Password
    @Column(nullable = false)
    private String password;

    @Roles
    @Column(nullable = false)
    private String role;

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Role getRoleEnum() {
        return Role.valueOf(role);
    }

    public User setRole(String role) {
        this.role = role;
        return this;
    }

    public boolean isOperator() {
        try {
            return Role.valueOf(role) == Role.OPERATOR;
        } catch (Exception e) {
            return false;
        }
    }
}
