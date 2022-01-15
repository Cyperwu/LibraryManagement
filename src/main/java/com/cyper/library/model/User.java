package com.cyper.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Schema(description = "用户")
@Table(name = "user", schema = "library")
public class User extends BaseModel implements UserDetails {
    private String username;

    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    private String phone;

    private String gender;

    private String name;

    @ManyToOne
    @JoinColumn(name = "fk_role_id")
    private Role role;

    @JsonIgnore
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> set = new HashSet<GrantedAuthority>();
        set.add(new SimpleGrantedAuthority(role.getCode()));
        return set;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}