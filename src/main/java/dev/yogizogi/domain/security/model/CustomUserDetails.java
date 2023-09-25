package dev.yogizogi.domain.security.model;

import dev.yogizogi.domain.user.model.entity.User;
import java.util.Collection;
import lombok.AccessLevel;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder(access = AccessLevel.PRIVATE)
public class CustomUserDetails implements UserDetails {

    private final User user;

    public static CustomUserDetails of(User user) {
        return CustomUserDetails.builder()
                .user(user)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

    @Override
    public String getPassword() {

        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getAccountName();
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
        return true;
    }

}