package com.academia.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class User implements UserDetails {

    private String username;

    private String password;

    private Boolean enabled;

    private List<String> roles;

    public User(String username, String password, Boolean enabled, List<String> authorities) {
        super();
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roles = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.roles.stream().map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }
}
