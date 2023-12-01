package com.go2geda.Go2GedaApp.security.models;

import com.go2geda.Go2GedaApp.data.models.Role;
import com.go2geda.Go2GedaApp.data.models.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@AllArgsConstructor
public class SecureUser implements UserDetails {
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = user.getRole();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority userAuthority = new SimpleGrantedAuthority(role.name());
        authorities.add(userAuthority);
        return authorities;
    }

    @Override
    public String getPassword() {
        String password = user.getBasicInformation().getPassword();
        return password;
    }

    @Override
    public String getUsername() {
        String username = user.getBasicInformation().getEmail();
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }
}
